package model;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {  
        // 1. A fő játékmodell létrehozása
        Game game = new Game();
        CityMap map = game.getCityMap();

        // 2. Csomópontok (Junctions) létrehozása és képernyő-koordinátáik beállítása
        // A koordinátákat úgy lőttük be, hogy egy szép, arányos elrendezést adjanak az ablakban
        Junction j1 = new Junction("J1");
        j1.setX(150); j1.setY(150);

        Junction j2 = new Junction("J2");
        j2.setX(450); j2.setY(150);

        Junction j3 = new Junction("J3");
        j3.setX(650); j3.setY(300);

        Junction j4 = new Junction("J4");
        j4.setX(150); j4.setY(450);

        Junction j5 = new Junction("J5");
        j5.setX(450); j5.setY(450);

        // Csomópontok hozzáadása a várostérképhez
        map.addPoint(j1);
        map.addPoint(j2);
        map.addPoint(j3);
        map.addPoint(j4);
        map.addPoint(j5);

        // 3. Sávok (Lanes) létrehozása és összekötése a Python terv alapján
        // Minden útszakaszhoz létrehozunk egy sávot, és beállítjuk a topológiai kapcsolatokat
        
        // Lane 1: J1 -> J2
        Lane l1 = new Lane("lane_1");
        l1.setStartPoint(j1);
        l1.setEndPoint(j2);
        j1.addOutgoingLane(l1);
        j2.addIncomingLane(l1);
        map.addLane(l1);

        // Lane 2: J2 -> J3
        Lane l2 = new Lane("lane_2");
        l2.setStartPoint(j2);
        l2.setEndPoint(j3);
        j2.addOutgoingLane(l2);
        j3.addIncomingLane(l2);
        map.addLane(l2);

        // Lane 3: J1 -> J4
        Lane l3 = new Lane("lane_3");
        l3.setStartPoint(j1);
        l3.setEndPoint(j4);
        j1.addOutgoingLane(l3);
        j4.addIncomingLane(l3);
        map.addLane(l3);

        // Lane 4: J4 -> J5
        Lane l4 = new Lane("lane_4");
        l4.setStartPoint(j4);
        l4.setEndPoint(j5);
        j4.addOutgoingLane(l4);
        j5.addIncomingLane(l4);
        map.addLane(l4);

        // Lane 5: J2 -> J5
        Lane l5 = new Lane("lane_5");
        l5.setStartPoint(j2);
        l5.setEndPoint(j5);
        j2.addOutgoingLane(l5);
        j5.addIncomingLane(l5);
        map.addLane(l5);

        // 4. Egy kis kezdeti hómennyiség beállítása a teszteléshez,
        // hogy lássuk a sávok átszíneződését a grafikus felületen!
        l1.getSnow().setSnowLevel(0);  // Tiszta út (Sötétszürke)
        l2.getSnow().setSnowLevel(4);  // Enyhe hó (Világosszürke)
        l3.getSnow().setSnowLevel(8);  // Közepes hó (Közepesen fehér)
        l4.getSnow().setSnowLevel(12); // Nagy hó (Hófehér)
        l5.getSnow().setSnowLevel(2);  // Minimális hó

        // --- JÁRMŰVEK ELHELYEZÉSE ---
        
        // Létrehozzuk a játékost (SnowCleaner), ami egy nevet vár paraméterként
        SnowCleaner cleaner = new SnowCleaner("Jatekos1"); 
        
        // A SnowCleaner konstruktora automatikusan létrehoz egy hókotrót (SnowPlower).
        // Lekérjük ezt a járművet, mert EZT kell rátenni az utakra!
        model.SnowPlower plower = cleaner.getSnowPlowers().get(0);

        // Beállítjuk a hókotró kezdőpozícióját a J1 csomópontra
        plower.setCurrentPoint(j1); 
        
        // Hozzáadjuk a csomóponthoz is, hogy tudja, ott áll rajta egy jármű
        j1.addVehicle(plower);
        
        // Hozzáadjuk a város térképéhez, hogy a grafikus felület megtalálja
        map.addVehicle(plower);
        
        // Opcionális: Hozzáadjuk a játékost a játékhoz
        game.addPlayer(cleaner);;

        // 5. A grafikus felhasználói felület (GUI) elindítása
        // A MainFrame CardLayout-ja miatt először a Főmenü fog megjelenni
        MainFrame frame = new MainFrame(game);
        frame.setVisible(true);
    }
}