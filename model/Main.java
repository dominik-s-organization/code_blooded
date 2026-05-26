package model;

import view.MainFrame;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // ==========================================================
        // 1. A FŐ JÁTÉKMODELL LÉTREHOZÁSA
        // ==========================================================
        Game game = new Game();
        CityMap map = game.getCityMap();

        // ==========================================================
        // 2. PÁLYA GENERÁLÁSA
        // ==========================================================
        // A MapGenerator elhelyezi a pontokat és utakat húz köztük
        MapGenerator.generateRandomMap(game, 5, 6);

        // ==========================================================
        // 3. SHOWCASE: VÉLETLENSZERŰ SÁV-ÁLLAPOTOK (Textúra teszt)
        // ==========================================================
        Random rand = new Random();
        for (Lane lane : map.getLanes()) {
            if (lane.getSnow() != null) {
                // 1. Véletlenszerű hóvastagság (0-tól 10-ig)
                lane.getSnow().setSnowLevel(rand.nextInt(11));

                // 2. Legyen 30% esély, hogy az út jégpáncélos
                if (rand.nextInt(100) < 30) {
                    lane.getSnow().setIce(true);
                }

                // 3. Legyen 20% esély, hogy szórtak rá zúzottkövet (csak ha jeges)
                if (lane.getSnow().isIce() && rand.nextInt(100) < 20) {
                    lane.getSnow().setCrushedStoneLevel(3); // 3-as szintű zúzottkő
                }
            }
        }

        // ==========================================================
        // 4. JÁRMŰVEK ELHELYEZÉSE
        // ==========================================================
        // Autók és Buszok automatikus elhelyezése a TrafficGenerator-ral, a busz opcionális
        TrafficGenerator.spawnVehicles(game, 3, 0); // 3 autó, 0 busz

        // Játékos (Hókotró) elhelyezése
        SnowCleaner cleaner = new SnowCleaner("Jatekos1"); 
        model.SnowPlower plower = cleaner.getSnowPlowers().get(0);

        // Rárakjuk a hókotrót a legelső generált pontra
        Point startJunction = map.getPoints().get(0);
        plower.setCurrentPoint(startJunction); 
        
        startJunction.addVehicle(plower);
        map.addVehicle(plower);
        game.addPlayer(cleaner);

        // ==========================================================
        // 5. GUI INDÍTÁSA
        // ==========================================================
        MainFrame frame = new MainFrame(game);
        frame.setVisible(true);
    }
}