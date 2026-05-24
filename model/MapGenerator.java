package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A MapGenerator osztály egy véletlenszerű várostérkép generálásáért felelős.
 * KÖTELEZŐEN ÖSSZEFÜGGŐ gráfot generál, ahol az utak egyirányúak.
 */
public class MapGenerator {

    private static int laneIdCounter = 1;

    public static void generateRandomMap(Game game, int rows, int cols) {
        CityMap map = game.getCityMap();
        Random rand = new Random();

        // 1. JAVÍTÁS: A rács immár az ősosztályt (Point) használja!
        Point[][] grid = new Point[rows][cols];
        List<int[]> existingNodes = new ArrayList<>();

        int startX = 60;
        int startY = 60;
        int gapX = 170; 
        int gapY = 130; 

        int targetNodes = (int) (rows * cols * 0.8); 
        if (targetNodes == 0) targetNodes = 1;
        int junctionIdCounter = 1;

        // 2. Kezdőpont generálása (Ez is Point változóba megy)
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
                
                // === POLIMORF SORSOLÁS ===
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
                
                // Utak bekötése (Itt a grid[r][c] is már Point)
                createOneWayLane(map, grid[r][c], newNode, rand);
                createOneWayLane(map, newNode, grid[r][c], rand);
            }
        }

        // 3. Extra utak (hurkok) hozzáadása
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // JAVÍTÁS: Itt is Point a típus, nem Junction!
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
    }

    // JAVÍTÁS: Point paramétereket vár Junction helyett!
    private static void createOneWayLane(CityMap map, Point from, Point to, Random rand) {
        Lane lane = new Lane("lane_" + laneIdCounter++);
        lane.setStartPoint(from);
        lane.setEndPoint(to);
        from.addOutgoingLane(lane);
        to.addIncomingLane(lane);
        
        // Opcionális: Véletlenszerű hó a frissen generált sávokon
        if (lane.getSnow() != null) {
            lane.getSnow().setSnowLevel(rand.nextInt(15));
        }
        
        map.addLane(lane);
    }

    // JAVÍTÁS: Point paramétereket vár Junction helyett!
    private static boolean isConnected(Point p1, Point p2) {
        for (Lane lane : p1.getOutgoingLanes()) {
            if (lane.getEndPoint().equals(p2)) return true;
        }
        return false;
    }
}