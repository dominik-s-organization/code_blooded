package model;

import view.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. A fő játékmodell létrehozása
        Game game = new Game();
        CityMap map = game.getCityMap();

        // 2. A PÁLYA VÉLETLENSZERŰ GENERÁLÁSA (3 sor, 4 oszlop = 12 csomópont)
        // A MapGenerator elhelyezi a pontokat és 75% eséllyel húz utakat köztük!
        // 2. A PÁLYA VÉLETLENSZERŰ GENERÁLÁSA (5 sor, 6 oszlop)
        MapGenerator.generateRandomMap(game, 5, 6);

        // --- JÁRMŰVEK ELHELYEZÉSE ---
        
        // Létrehozzuk a játékost (SnowCleaner)
        SnowCleaner cleaner = new SnowCleaner("Jatekos1"); 
        model.SnowPlower plower = cleaner.getSnowPlowers().get(0);

        // Rárakjuk a hókotrót a legelső generált pontra (bal felső sarok -> J1)
        Point startJunction = map.getPoints().get(0);
        plower.setCurrentPoint(startJunction); 
        
        startJunction.addVehicle(plower);
        map.addVehicle(plower);
        game.addPlayer(cleaner);

        // 3. A grafikus felhasználói felület (GUI) elindítása
        MainFrame frame = new MainFrame(game);
        frame.setVisible(true);
    }
}