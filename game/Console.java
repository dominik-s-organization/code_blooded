package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Console {

    public Game game;

    public void ReadConsoleParams() {
        // A parancsok beolvasása a szabványos bemenetről (stdin) történik[cite: 105].
        // Try-with-resources használata a BufferedReader automatikus lezárásához.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            while (true) {
                printHelp();
                String line;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    // Amennyiben a beolvasás során hiba történik, hibaüzenetet küldünk[cite: 189].
                    System.out.println("> ERROR: Reading from standard input failed.");
                    break;
                }

                if (line != null && !line.trim().isEmpty()) {
                    // A bemeneti nyelv soronként értelmezett, szóközökkel elválasztva[cite: 115, 116].
                    String[] args = line.trim().split("\\s+");
                    String command = args[0];

                    switch (command) {
                        case "add_player":
                            if(game.getPlayerCount() < 4){
                                if(args[2].equals("snow_cleaner")){
                                    game.addPlayer(new SnowCleaner(args[1]));
                                }
                                else if(args[2].equals("bus_driver")){
                                    game.addPlayer(new BusDriver(args[1]));
                                }
                                else{
                                    System.out.println("> ERROR: Invalid player type: " + args[2]);
                                }
                                break;
                            }
                            System.out.println("> ERROR: Maximum player limit reached.");
                            break;
                            
                        case "load":
                            // Konfiguráció betöltése fájlból: <fájlnév.txt>[cite: 126, 128].
                            game.loadGame(args[1]);
                            break;
                            
                        case "step":
                            // Léptetés végrehajtása (Game.simulateStep()): [n][cite: 129, 131, 136].
                            if(args.length > 1){
                                int steps;
                                try {
                                    steps = Integer.parseInt(args[1]);
                                } catch (NumberFormatException e) {
                                    System.out.println("> ERROR: Invalid number of steps: " + args[1]);
                                    break;
                                }
                                for(int i = 0; i < steps; i++){
                                    game.simulateStep();
                                }
                            }
                            else{
                                game.simulateStep();
                            }
                            game.simulateStep();
                            break;
                            
                        case "random":
                            // RNG állapot vezérlése: <on | off>[cite: 138, 140].
                            break;
                            
                        case "force_slip":
                            // Autó kényszerített megcsúsztatása: <car_id> <true | false>[cite: 141, 143].
                            break;
                            
                        case "stat":
                            // Objektum állapotának lekérdezése: <objektum_id>[cite: 144, 146].
                            if (args.length < 2) {
                                System.out.println("> ERROR: Missing object ID for stat command.");
                                break;
                            }
                            String id = args[1];
                            Vehicle v = game.getVehicleById(id);
                            if (v != null) {
                                v.stat();
                                break;
                            }
                            Lane l = game.getLaneById(id);
                            if (l != null) {
                                l.stat();
                                break;
                            }
                            Point p = game.getPointById(id);
                            if (p != null) {
                                p.stat();
                                break;
                            }
                            System.out.println("> ERROR: Object with id " + id + " not found.");
                            break;
                            
                        case "move":
                            // Jármű menetirányának kiválasztása: <vehicle_id> <junction_id>[cite: 147, 149].
                            break;
                            
                        case "buy":
                            // Vásárlás a Bolttal: <player_id> <item_name> [quantity][cite: 150, 153].
                            break;
                            
                        case "equip":
                            // Hókotró fejének lecserélése: <plower_id> <head_type>[cite: 154, 156].
                            break;
                            
                        case "save":
                            // Aktuális állapot kimentése: <fájlnév.txt>[cite: 157, 162].
                            game.saveGame(args[1]);
                            break;
                            
                        case "create_junction":
                            // Új csomópont létrehozása: <junction_id>[cite: 166, 168].
                            break;
                            
                        case "create_lane":
                            // Új sáv létrehozása és bekötése: <lane_id> <start_junction_id> <end_junction_id>[cite: 169, 171].
                            break;
                            
                        case "set_lane":
                            // Sáv paramétereinek manuális beállítása[cite: 172, 174].
                            break;
                            
                        case "place_vehicle":
                            // Jármű lehelyezése a megadott pozícióra[cite: 175, 176].
                            break;
                            
                        case "exit":
                            // A szimulációs program biztonságos leállítása[cite: 216].
                            return; 
                            
                        case "help":
                            // A szimulációs program segítségének megjelenítése[cite: 216].
                            printHelp();
                            return; 
                        
                        default:
                            // Szabálytalan parancs esetén hibaüzenet[cite: 184, 189].
                            System.out.println("> ERROR: Unknown command: " + command);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            // Kritikus hiba a standard input elérésekor.
            System.out.println("> ERROR: Critical system error while accessing console.");
        }
    }

    /**
     * Segédfüggvény a rendelkezésre álló parancsok és argumentumaik listázására.
     */
    public void printHelp() {
        System.out.println("--- Elérhető parancsok és használatuk ---");
        
        // Rendszervezérlő és lekérdező parancsok
        System.out.println("add_player <name> <role>");
        System.out.println("  Leírás: Új játékos regisztrálása a szimulációs rendszerbe. [cite: 124, 125]");
        
        System.out.println("load <fájlnév.txt>");
        System.out.println("  Leírás: A programot egy előre megadott konfigurációból indítja el. [cite: 127, 128]");
        
        System.out.println("step [n]");
        System.out.println("  Leírás: A léptető parancs. Kiváltja a cselekvések tényleges végrehajtását. Opcionálisan megadható a lépések száma. [cite: 130, 136]");
        
        System.out.println("random <on | off>");
        System.out.println("  Leírás: A játék véletlenszám-generátorának (RNG) állapotát vezérli. [cite: 139, 140]");
        
        System.out.println("force_slip <car_id> <true | false>");
        System.out.println("  Leírás: Arra kényszeríti a rendszert determinisztikus módban, hogy egy civil autó garantáltan megcsússzon. [cite: 142, 143]");
        
        System.out.println("stat <objektum_id>");
        System.out.println("  Leírás: Állapotlekérdező parancs. A konzolra írja egy entitás összes aktuális, belső paraméterét. [cite: 145, 146]");
        
        System.out.println("move <vehicle_id> <junction_id>");
        System.out.println("  Leírás: Utasítja a megadott járművet, hogy a következő step alkalmával a megadott csomópont felé próbáljon haladni. [cite: 148, 149]");
        
        System.out.println("buy <player_id> <item_name> [quantity]");
        System.out.println("  Leírás: A Takarító (Cleaner) játékos tárgyat/nyersanyagot vásárol a Boltból. [cite: 151, 153]");
        
        System.out.println("equip <plower_id> <head_type>");
        System.out.println("  Leírás: Lecseréli a hókotrón lévő aktuális fejet egy másik, már birtokolt fejre. [cite: 155, 156]");
        
        System.out.println("save <fájlnév.txt>");
        System.out.println("  Leírás: A teljes aktuális szimulációs állapotot kimenti a megadott szöveges fájlba. [cite: 158, 162]");
        
        // Konfigurációs és pályaépítő parancsok
        System.out.println("\n--- Konfigurációs és pályaépítő parancsok ---");
        
        System.out.println("create_junction <junction_id>");
        System.out.println("  Leírás: Létrehoz egy új csomópontot (Junction vagy CrossRoads). [cite: 167, 168]");
        
        System.out.println("create_lane <lane_id> <start_junction_id> <end_junction_id>");
        System.out.println("  Leírás: Létrehoz egy sávot, és logikailag összeköti a csomópontokat. [cite: 170, 171]");
        
        System.out.println("set_lane <lane_id> <is_jammed | is_underground | snow_level | ice | broken_ice | salt_level | crushed_Stone_level> <érték>");
        System.out.println("  Leírás: Tesztelési célból egy sáv paramétereit manuálisan, azonnal beállítja. [cite: 173, 174]");
        
        System.out.println("place_vehicle <type> <entity_id> <position_id>");
        System.out.println("  Leírás: Elhelyez egy járművet (Busz, Civil autó, Hókotró) a megadott kezdőpozíción. [cite: 176]");

        System.out.println("help");
        System.out.println("  Leírás: Megjeleníti a rendelkezésre álló parancsokat és azok használatát. [cite: 178, 179]");
        
        System.out.println("exit");
        System.out.println("  Leírás: A szimulációs program biztonságos leállítása és kilépés a parancssorból. ");
        System.out.println("-------------------------------------------");
    }
}