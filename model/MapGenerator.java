package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A MapGenerator osztály egy véletlenszerű várostérkép generálásáért felelős.
 * KÖTELEZŐEN ÖSSZEFÜGGŐ gráfot generál, ahol az utak egyirányúak.
 */
public class MapGenerator {
    // Sáv egyedi azonosítója
    private static int laneIdCounter = 1;

    /**
     * Generál egy véletlenszerű, összefüggő várostérképet a megadott dimenziók alapján.
     * A metódus polimorf módon hoz létre normál csomópontokat (Junction), 
     * kereszteződéseket (CrossRoads) és alagutakat (Tunnel).
     * @param game A fő játék objektum, amelyhez a térkép tartozik.
     * @param rows A virtuális generálási rács sorainak száma.
     * @param cols A virtuális generálási rács oszlopainak száma.
     */
    public static void generateRandomMap(Game game, int rows, int cols) {
        CityMap map = game.getCityMap();
        Random rand = new Random();

        Point[][] grid = new Point[rows][cols];
        List<int[]> existingNodes = new ArrayList<>();

        int startX = 60;
        int startY = 60;
        int gapX = 170; 
        int gapY = 130; 

        int targetNodes = (int) (rows * cols * 0.8); 
        if (targetNodes == 0) targetNodes = 1;
        int junctionIdCounter = 1;

        Point startNode = new Junction("junction_" + junctionIdCounter++);
        startNode.setX(startX);
        startNode.setY(startY);
        grid[0][0] = startNode;
        map.addPoint(startNode);
        existingNodes.add(new int[]{0, 0});

        while (existingNodes.size() < targetNodes) {
            int[] coords = existingNodes.get(rand.nextInt(existingNodes.size()));
            int r = coords[0];
            int c = coords[1];

            int dir = rand.nextInt(4);
            int nr = r, nc = c;
            if (dir == 0) nr--; else if (dir == 1) nc++; else if (dir == 2) nr++; else if (dir == 3) nc--;

            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == null) {
                
                Point newNode;
                int typeChance = rand.nextInt(100); 
                
                if (typeChance < 15) {
                    newNode = new Tunnel("tunnel_" + junctionIdCounter++);
                } else if (typeChance < 30) {
                    newNode = new CrossRoads("crossroads_" + junctionIdCounter++);
                } else {
                    newNode = new Junction("junction_" + junctionIdCounter++);
                }
                
                newNode.setX(startX + nc * gapX);
                newNode.setY(startY + nr * gapY);
                grid[nr][nc] = newNode;
                map.addPoint(newNode);
                existingNodes.add(new int[]{nr, nc});
                
                createOneWayLane(map, grid[r][c], newNode, rand);
                createOneWayLane(map, newNode, grid[r][c], rand);
            }
        }

        // 3. Extra utak (hurkok) hozzáadása
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Point current = grid[r][c]; 
                if (current == null) continue;

                if (c < cols - 1 && grid[r][c + 1] != null && !isConnected(current, grid[r][c+1]) && rand.nextDouble() < 0.4) {
                    createOneWayLane(map, current, grid[r][c+1], rand);
                    createOneWayLane(map, grid[r][c+1], current, rand);
                }
                if (r < rows - 1 && grid[r + 1][c] != null && !isConnected(current, grid[r+1][c]) && rand.nextDouble() < 0.4) {
                    createOneWayLane(map, current, grid[r+1][c], rand);
                    createOneWayLane(map, grid[r+1][c], current, rand);
                }
            }
        }

        // =========================================================================
        // 4. CSOMÓPONTOK TÍPUSÁNAK JAVÍTÁSA A VALÓDI TOPOLÓGIA ALAPJÁN
        // =========================================================================
        List<Point> finalPoints = new ArrayList<>();
        for (Point p : map.getPoints()) {
            // Az alagutak különlegesek, azokat nem módosítjuk
            if (p instanceof Tunnel) {
                finalPoints.add(p);
                continue; 
            }

            // Megszámoljuk az egyedi szomszédokat
            List<Point> neighbors = new ArrayList<>();
            for (Lane l : p.getOutgoingLanes()) {
                if (!neighbors.contains(l.getEndPoint())) {
                    neighbors.add(l.getEndPoint());
                }
            }
            for (Lane l : p.getIncomingLanes()) {
                if (!neighbors.contains(l.getStartPoint())) {
                    neighbors.add(l.getStartPoint());
                }
            }

            int degree = neighbors.size();
            boolean isStraight = false;

            // Ha pontosan 2 szomszédja van, ellenőrizzük, hogy egy vonalban vannak-e
            if (degree == 2) {
                Point n1 = neighbors.get(0);
                Point n2 = neighbors.get(1);
                
                // Akkor egyenes az út, ha a három pont azonos X vagy azonos Y tengelyen fekszik
                if ((n1.getX() == p.getX() && n2.getX() == p.getX()) ||
                    (n1.getY() == p.getY() && n2.getY() == p.getY())) {
                    isStraight = true;
                }
            }

            // Kereszteződés kell, ha: zsákutca (1), sarok (2 de nem egyenes), vagy valódi kereszteződés (3, 4)
            boolean shouldBeCrossRoads = (degree != 2) || (degree == 2 && !isStraight);

            if (shouldBeCrossRoads && p instanceof Junction) {
                // Csere CrossRoads-ra
                String newId = p.getId().replace("junction", "crossroads");
                CrossRoads cr = new CrossRoads(newId);
                transferNodeData(p, cr);
                finalPoints.add(cr);
            } else if (!shouldBeCrossRoads && p instanceof CrossRoads) {
                // Csere Junction-re
                String newId = p.getId().replace("crossroads", "junction");
                Junction j = new Junction(newId);
                transferNodeData(p, j);
                finalPoints.add(j);
            } else {
                finalPoints.add(p); // Nem kell cserélni, marad ami volt
            }
        }
        
        // Frissítjük a térképet a kijavított pontokkal
        map.setPoints(finalPoints);
    }

    /**
     * Segédfüggvény: Átmásolja a koordinátákat és a sávokat egy régi csomópontról egy újra,
     * és frissíti a rákötött sávok hivatkozásait is.
     */
    private static void transferNodeData(Point oldNode, Point newNode) {
        newNode.setX(oldNode.getX());
        newNode.setY(oldNode.getY());
        
        newNode.getIncomingLanes().addAll(oldNode.getIncomingLanes());
        newNode.getOutgoingLanes().addAll(oldNode.getOutgoingLanes());
        
        // Frissítjük a hivatkozásokat a sávokban, hogy immár az új csomópontra mutassanak!
        for (Lane l : newNode.getIncomingLanes()) {
            l.setEndPoint(newNode);
        }
        for (Lane l : newNode.getOutgoingLanes()) {
            l.setStartPoint(newNode);
        }
    }

    private static void createOneWayLane(CityMap map, Point from, Point to, Random rand) {
        Lane lane = new Lane("lane_" + laneIdCounter++);
        lane.setStartPoint(from);
        lane.setEndPoint(to);
        from.addOutgoingLane(lane);
        to.addIncomingLane(lane);
        
        if (lane.getSnow() != null) {
            lane.getSnow().setSnowLevel(rand.nextInt(15));
        }
        
        map.addLane(lane);
    }

    private static boolean isConnected(Point p1, Point p2) {
        for (Lane lane : p1.getOutgoingLanes()) {
            if (lane.getEndPoint().equals(p2)) return true;
        }
        return false;
    }
}