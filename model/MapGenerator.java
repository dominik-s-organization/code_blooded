package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A MapGenerator osztály egy véletlenszerű várostérkép (gráf) generálásáért felelős.
 * A Spanning Tree (feszítőfa) algoritmusra építve garantálja, hogy a generált térkép 
 * KÖTELEZŐEN ÖSSZEFÜGGŐ legyen, így a járművek el tudnak jutni bármelyik pontból bármelyikbe.
 */
public class MapGenerator {

    /** Számláló a sávok egyedi azonosítójának (ID) generálásához. */
    private static int laneIdCounter = 1;

    /**
     * Létrehoz egy véletlenszerű, garantáltan összefüggő térképet a megadott rácsméretek alapján.
     * Minden útszakaszhoz automatikusan oda- és visszautat is generál a párhuzamos közlekedéshez.
     * @param game A Game objektum, amelynek a CityMap-jét fel kell tölteni.
     * @param rows A rács sorainak száma.
     * @param cols A rács oszlopainak száma.
     */
    public static void generateRandomMap(Game game, int rows, int cols) {
        CityMap map = game.getCityMap();
        Random rand = new Random();

        // Rács létrehozása a csomópontok tárolására
        Junction[][] grid = new Junction[rows][cols];
        
        // Egy lista, amelyben a már lerakott, hálózathoz csatlakozó pontok koordinátáit tároljuk
        List<int[]> existingNodes = new ArrayList<>();

        // Képernyő koordináták beállítása (Nagy távolságok a szép megjelenítéshez)
        int startX = 60;
        int startY = 60;
        int gapX = 170; 
        int gapY = 130; 

        // Cél: A rács 80%-át beépítjük, hogy maradjanak izgalmas "lyukak" a városban
        int targetNodes = (int) (rows * cols * 0.8); 
        if (targetNodes == 0) targetNodes = 1;
        int junctionIdCounter = 1;

        // --- 1. LÉPÉS: A legelső pont elhelyezése (0,0) ---
        Junction startNode = new Junction("J" + junctionIdCounter++);
        startNode.setX(startX);
        startNode.setY(startY);
        grid[0][0] = startNode;
        map.addPoint(startNode);
        existingNodes.add(new int[]{0, 0}); // Hozzáadjuk a létező pontokhoz

        // --- 2. LÉPÉS: A város "növesztése" (Spanning Tree) ---
        // Ez a ciklus garantálja, hogy minden új pont egy már meglévőhöz kapcsolódik!
        while (existingNodes.size() < targetNodes) {
            // Kiválasztunk egy már létező pontot, ahonnan terjeszkedhetünk
            int[] coords = existingNodes.get(rand.nextInt(existingNodes.size()));
            int r = coords[0];
            int c = coords[1];

            // Véletlen irányba próbálunk lépni (0: fel, 1: jobb, 2: le, 3: bal)
            int dir = rand.nextInt(4);
            int nr = r, nc = c;
            if (dir == 0) nr--;
            else if (dir == 1) nc++;
            else if (dir == 2) nr++;
            else if (dir == 3) nc--;

            // Ha a rácson belül vagyunk, ÉS az adott hely még üres (nem épült oda semmi)
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == null) {
                
                // 1. Létrehozzuk az új csomópontot
                Junction newNode = new Junction("J" + junctionIdCounter++);
                newNode.setX(startX + nc * gapX);
                newNode.setY(startY + nr * gapY);
                grid[nr][nc] = newNode;
                map.addPoint(newNode);
                existingNodes.add(new int[]{nr, nc}); // Bejegyezzük, hogy létezik

                // 2. AZONNAL összekötjük az őt "létrehozó" szülőponttal (oda-vissza)
                connectJunctions(map, grid[r][c], grid[nr][nc], rand);
            }
        }

        // --- 3. LÉPÉS: Extra utak (hurkok) hozzáadása ---
        // Hogy ne egy fa szerkezetet kapjunk, ahol sok a zsákutca, 
        // 40% eséllyel összekötjük az egymás mellett lévő, de még nem csatlakozó szomszédokat is.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Junction current = grid[r][c];
                if (current == null) continue;

                // Jobb oldali szomszéd vizsgálata
                if (c < cols - 1 && grid[r][c + 1] != null) {
                    Junction right = grid[r][c + 1];
                    if (!isConnected(current, right) && rand.nextDouble() < 0.4) { 
                        connectJunctions(map, current, right, rand);
                    }
                }

                // Alsó szomszéd vizsgálata
                if (r < rows - 1 && grid[r + 1][c] != null) {
                    Junction bottom = grid[r + 1][c];
                    if (!isConnected(current, bottom) && rand.nextDouble() < 0.4) {
                        connectJunctions(map, current, bottom, rand);
                    }
                }
            }
        }
    }

    /**
     * Segédmetódus két csomópont kétirányú (oda-vissza) összekötésére a térképen.
     * @param map A város térképe.
     * @param j1 Az egyik csomópont.
     * @param j2 A másik csomópont.
     * @param rand A véletlenszám-generátor a hó szintjének beállításához.
     */
    private static void connectJunctions(CityMap map, Junction j1, Junction j2, Random rand) {
        // Oda út (j1 -> j2)
        Lane toLane = new Lane("lane_" + laneIdCounter++);
        toLane.setStartPoint(j1);
        toLane.setEndPoint(j2);
        j1.addOutgoingLane(toLane);
        j2.addIncomingLane(toLane);
        toLane.getSnow().setSnowLevel(rand.nextInt(15));
        map.addLane(toLane);

        // Vissza út (j2 -> j1)
        Lane fromLane = new Lane("lane_" + laneIdCounter++);
        fromLane.setStartPoint(j2);
        fromLane.setEndPoint(j1);
        j2.addOutgoingLane(fromLane);
        j1.addIncomingLane(fromLane);
        fromLane.getSnow().setSnowLevel(rand.nextInt(15));
        map.addLane(fromLane);

        // Sávok összekapcsolása a párhuzamos logikához
        toLane.setRightLane(fromLane);
        fromLane.setLeftLane(toLane);
    }

    /**
     * Segédmetódus annak ellenőrzésére, hogy két pont között létezik-e már közvetlen kapcsolat.
     * @param j1 A kiinduló pont.
     * @param j2 A célpont.
     * @return Igaz, ha már van kiépített út j1-ből j2-be.
     */
    private static boolean isConnected(Junction j1, Junction j2) {
        for (Lane lane : j1.getOutgoingLanes()) {
            if (lane.getEndPoint().equals(j2)) return true;
        }
        return false;
    }
}