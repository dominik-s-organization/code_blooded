package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

// A Console osztály felelős a parancssori interakciók kezeléséért, a parancsok értelmezéséért és végrehajtásáért.
class Console {
    public Game game = new Game();

    public ArrayList<String> commandHistory = new ArrayList<>();

    public void readConsoleParams() {
        // A parancsok beolvasása a szabványos bemenetről (stdin) történik[cite: 105].
        // Try-with-resources használata a BufferedReader automatikus lezárásához.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            boolean isGoing = true;
            while (isGoing) {
                //printHelp();
                String line;
                try {
                    System.out.print("> ");
                    line = reader.readLine();
                } catch (IOException e) {
                    // Amennyiben a beolvasás során hiba történik, hibaüzenetet küldünk[cite: 189].
                    Logger.log("> ERROR: Reading from standard input failed.");
                    break;
                }

                if (line != null && !line.trim().isEmpty()) {
                    // A bemeneti nyelv soronként értelmezett, szóközökkel elválasztva[cite: 115, 116].
                    isGoing = processCommand(line);
                }
            }
        } catch (IOException e) {
            // Kritikus hiba a standard input elérésekor.
            Logger.log("> ERROR: Critical system error while accessing console.");
        }
    }

    public boolean processCommand(String line) {
        String[] args = line.trim().split("\\s+");
        String command = args[0];

        if(game == null){
            System.out.println("> ERROR: Game instance is not initialized.");
            return false;
        }

        switch (command) {
            case "add_player": {
                if (args.length < 3) {
                    Logger.log("> ERROR: Missing arguments for add_player command. Usage: add_player <name> <role>");
                    break;
                }
                commandHistory.add(line);
                if(game.getPlayers().size() < 4) {
                    if(args[2].equals("snow_cleaner")){
                        game.addPlayer(new SnowCleaner(args[1]));
                    }
                    else if(args[2].equals("bus_driver")){
                        game.addPlayer(new BusDriver(args[1]));
                    }
                    else{
                        Logger.log("> ERROR: Invalid player type: " + args[2]);
                    }
                    break;
                }
                Logger.log("> ERROR: Maximum player limit reached.");
                break;
            }
                            
            case "load": {
                // Konfiguráció betöltése fájlból: <fájlnév.txt>[cite: 126, 128].
                if (args.length < 2) {
                    Logger.log("> ERROR: Missing filename.");
                } else {
                    loadGame(args[1]);
                    // A load-ot magát NEM mentjük a history-ba
                }
                break;
            }
                            
            case "step": {
                // Léptetés végrehajtása (Game.simulateStep()): [n][cite: 129, 131, 136].
                if (args.length > 2) {
                    Logger.log("> ERROR: Too many arguments for step command. Usage: step [n]");
                    break;
                }
                commandHistory.add(line);
                if(args.length > 1){
                    int steps;
                    try {
                        steps = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        Logger.log("> ERROR: Invalid number of steps: " + args[1]);
                        break;
                    }
                    for(int i = 0; i < steps; i++){
                        game.simulateStep();
                    }
                }
                else{
                    game.simulateStep();
                }
                break;
            }
            case "stat": {
                // Objektum állapotának lekérdezése: <objektum_id>[cite: 144, 146].
                if (args.length < 2) {
                    Logger.log("> ERROR: Missing object ID for stat command.");
                    break;
                }
                commandHistory.add(line);

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
                Logger.log("> ERROR: Object with id " + id + " not found.");
                break;
            }         

            case "test": {
                /**
                 * Automatikus tesztfuttató parancs.
                 * Szintaxis: test <teszt_száma>
                 * Példa: test 01 -> beolvassa a test_01.txt fájlt, és a kimenetet a 01_test.txt-be menti.
                 */
                if (args.length < 2) {
                    Logger.log("> ERROR: Missing test number. Usage: test <number>");
                    break;
                }
                commandHistory.add(line);
                
                String testNum = args[1];
                // A bemeneti és kimeneti fájlnevek dinamikus generálása
                String inputFile = "test_" + testNum + ".txt";
                String outputFile = testNum + "_test.txt";
                
                //Tesztelői mód bekapcsolása a futás idejére
                Logger.testerMode = true;
                
                //Napló törlése, hogy előző tesztek kimenete ne szemetelje tele az újat
                Logger.clear();

                //Teszt parancsok beolvasása és futtatása
                loadGame(inputFile);
                
                System.out.println("> Test " + testNum + " completed. Saving output to " + outputFile);
                //Eredmények automatikus kimentése
                Logger.save(outputFile);
                
                //Visszaállítjuk a tesztelői módot hamisra
                Logger.testerMode = false;
                break;
            }

            case "move": {
                // Jármű menetirányának kiválasztása: <vehicle_id> <lane_id>[cite: 147, 149].
                if(args.length < 3){
                    Logger.log("> ERROR: Missing arguments for move command. Usage: move <vehicle_id> <lane_id>");
                    break;
                }
                Vehicle v = game.getVehicleById(args[1]);
                Lane l = game.getLaneById(args[2]);
                if (v != null && l != null) {
                    if(args[1].contains("car_")){
                        Logger.log("> ERROR: Cars cannot be directly commanded to move. They determine their own path based on the city map and traffic conditions.");
                        break;
                    }
                    v.setNextLane(l);
                }
                break;
            }

            case "buy": {
                // Vásárlás a Bolttal: <player_id> <item_name> [quantity]
                if(args.length < 3){
                    Logger.log("> ERROR: Missing arguments for buy command. Usage: buy <player_id> <item_name> [quantity]");
                    break;
                }
                commandHistory.add(line);

                Player p = game.getPlayerByName(args[1]);
                if (p == null) {
                    Logger.log("> ERROR: Player with name " + args[1] + " not found.");
                    break;
                }
                
                String itemName = args[2];
                int quantity = 1;
                if (args.length > 3) {
                    try {
                        quantity = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        Logger.log("> ERROR: Invalid quantity. Please specify a valid number.");
                        break;
                    }
                }
                
                // Itt használjuk a polimorf getType() metódust az instanceof helyett!
                if ("snow_cleaner".equals(p.getType())) {         
                    SnowCleaner sc = (SnowCleaner) p;
                    game.getStore().buy(itemName, quantity, sc);
                } else {
                    Logger.log("> ERROR: Only SnowCleaner players can buy items from the shop.");
                }
                break;
            }           

            case "equip": {
                /**
                 * Hókotró fejének lecserélése a kívánt típusra, amennyiben az a játékos eszköztárában van.
                 * Szintaxis: equip <plower_id> <head_type>
                 */
                if (args.length < 3) {
                    Logger.log("> ERROR: Missing arguments for equip command. Usage: equip <plower_id> <head_type>");
                    break;
                }
                commandHistory.add(line);
                
                Vehicle v = game.getVehicleById(args[1]);
                if (v == null) {
                    Logger.log("> ERROR: Vehicle not found: " + args[1]);
                    break;
                }
                
                try {
                    // Ha a jármű egy Car vagy Bus, akkor kivételt dob, amit lent elkapunk.
                    SnowPlower sp = (SnowPlower) v;
                    
                    if (sp.getOwner() == null) {
                        Logger.log("> ERROR: SnowPlower has no owner, cannot access inventory.");
                        break;
                    }

                    // A parancsban kapott szót lecsupaszítjuk (kisbetűsítjük és kivesszük az esetleges aláhúzásokat)
                    String targetType = args[2].toLowerCase();
                    Head headToEquip = null;

                    // Végignézzük a tulajdonos (SnowCleaner) eszköztárát (inventory)
                    for (Head h : sp.getOwner().getInventory()) {
                        // Lekérjük az inventory-ban lévő fej pontos osztálynevét (pl. "ThrowerHead" -> "throwerhead")
                        String currentHeadName = h.getClass().getSimpleName().toLowerCase();
                        
                        // Ha a név tartalmazza a keresett típust (pl. a "throwerhead" tartalmazza a "thrower" szót)
                        if (currentHeadName.contains(targetType)) {
                            headToEquip = h; // Megtaláltuk a megfelelő fejet a raktárban!
                            break; 
                        }
                    }

                    if (headToEquip != null) {
                        sp.changeHead(headToEquip); // Felszereljük a meglévő fejet
                        Logger.log("> SUCCESS: " + headToEquip.getClass().getSimpleName() + " successfully equipped on " + args[1]);
                    } else {
                        // Ha a ciklus végigért, és nem talált ilyen fejet a listában:
                        Logger.log("> ERROR: The owner does not have a '" + args[2] + "' in their inventory. Buy it first from the Store!");
                    }
                } catch (ClassCastException e) {
                    // Ha a kasztolás elszállt, akkor az objektum biztosan nem hókotró volt.
                    Logger.log("> ERROR: Vehicle is not a SnowPlower: " + args[1]);
                }
                break;
            }
                            
            case "save": {
                // Aktuális állapot kimentése: <fájlnév.txt>[cite: 157, 162].
                if (args.length < 2) {
                    Logger.log("> ERROR: Missing filename.");
                } else {
                    saveGame(args[1]);
                    // A save-et sem mentjük, mert betöltéskor nem akarunk újra menteni
                }
                break;
            }
                            
            case "create_point": {
                // Új csomópont létrehozása: <type>[cite: 166, 168].
                if (args.length < 2) {
                    System.out.println("> ERROR: Missing arguments for create_point command. Usage: create_point <point_type>");
                    break;
                }
                commandHistory.add(line);

                String type = args[1];
                if (!type.equals("junction") && !type.equals("crossroads") && !type.equals("tunnel")) {
                    Logger.log("> ERROR: Invalid point type: " + type + ". Valid types are: junction, crossroads, tunnel.");
                    break;
                }
                switch (type) {
                    case "junction":
                        Junction junction = new Junction();
                        junction.setId(game.generateId("junction"));
                        game.getCityMap().addPoint(junction);
                        break;
                    case "crossroads":
                        CrossRoads crossroads = new CrossRoads();
                        crossroads.setId(game.generateId("crossroads"));
                        game.getCityMap().addPoint(crossroads);
                        break;
                    case "tunnel":
                        Tunnel tunnel = new Tunnel();
                        tunnel.setId(game.generateId("tunnel"));
                        game.getCityMap().addPoint(tunnel);
                        break;
                    default:
                        Logger.log("> ERROR: Invalid point type: " + type);
                        break;
                }
                break;
            }
                            
            case "create_lane": {
                // Új sáv létrehozása és bekötése: <start_junction_id> <end_junction_id>[cite: 169, 171].
                if (args.length < 3) {
                    Logger.log("> ERROR: Missing arguments for create_lane command. Usage: create_lane <start_junction_id> <end_junction_id>");
                    break;
                }
                commandHistory.add(line);
                
                Point startPoint = game.getPointById(args[1]);
                Point endPoint = game.getPointById(args[2]);
                Lane lane = new Lane();
                lane.setId(game.generateId("lane"));
                lane.setStartPoint(startPoint);
                lane.setEndPoint(endPoint);
                
                // Beállítani a legutolsó ugyanilyen sáv jobb oldali szomszádjának az új sávot
                List<Lane> lanes = game.getCityMap().getLanes();
                for (int i = lanes.size() - 1; i >= 0; i--) {
                    if (lanes.get(i).getStartPoint().equals(startPoint) && lanes.get(i).getEndPoint().equals(endPoint)) {
                        Lane leftLane = lanes.get(i);
                        lane.setLeftLane(leftLane);
                        leftLane.setRightLane(lane);
                        break;
                    }
                }

                game.getCityMap().addLane(lane);
                break;
            }
                            
            case "set_lane": {
                /**
                 * Sáv paramétereinek manuális beállítása tesztelési célból.
                 * Szintaxis: set_lane <lane_id> <parameter> <value>
                 */
                if (args.length < 4) {
                    Logger.log("> ERROR: Missing arguments for set_lane command. Usage: set_lane <lane_id> <parameter> <value>");
                    break;
                }
                commandHistory.add(line);

                Lane laneToSet = game.getLaneById(args[1]);
                if (laneToSet == null) {
                    Logger.log("> ERROR: Lane not found: " + args[1]);
                    break;
                }

                String param = args[2].toLowerCase();
                String valueStr = args[3].toLowerCase();

                try {
                    switch (param) {
                        case "is_jammed":
                            laneToSet.setJammed(Boolean.parseBoolean(valueStr));
                            break;
                        case "is_underground":
                            laneToSet.setUnderground(Boolean.parseBoolean(valueStr));
                            break;
                        case "snow_level":
                            laneToSet.getSnow().setLevel(Integer.parseInt(valueStr));
                            break;
                        case "ice":
                            laneToSet.getSnow().setIce(Boolean.parseBoolean(valueStr));
                            break;
                        case "broken_ice":
                            laneToSet.getSnow().setBrokenIce(Boolean.parseBoolean(valueStr));
                            break;
                        case "salt_level":
                            laneToSet.getSnow().setSaltLevel(Integer.parseInt(valueStr));
                            break;
                        case "crushed_stone_level":
                            laneToSet.getSnow().setCrushedStoneLevel(Integer.parseInt(valueStr));
                            break;
                        default:
                            Logger.log("> ERROR: Unknown parameter: " + param);
                    }
                } catch (Exception e) {
                    Logger.log("> ERROR: Invalid value format for " + param + ". Check if it should be a number or true/false.");
                }
                break;
            }
                            

            case "place_vehicle": {
                /**
                 * Jármű lehelyezése a megadott kezdőpontra a város hálózatában.
                 * Szintaxis: place_vehicle <position_id> [player_name]
                 */
                if (args.length < 2) {
                    System.out.println("> ERROR: Missing arguments for place_vehicle command. Usage: place_vehicle <position_id> [player_name]");
                    break;
                }
                commandHistory.add(line);

                String pId = args[1];
                String playerName = args.length > 2 ? args[2] : null;
                Player player = game.getPlayerById(playerName);

                Point startingPoint = game.getPointById(pId);
                if (startingPoint == null) {
                    Logger.log("> ERROR: Starting point not found: " + pId);
                    break;
                }

                Vehicle newVehicle = null;
                if (playerName == null) {
                    newVehicle = new Car();
                    newVehicle.setId(game.generateId("car"));
                } else if (player.getType().equals("bus_driver")) {
                    BusDriver busDriver = (BusDriver) player;
                    newVehicle = busDriver.getBus();
                    newVehicle.setId(game.generateId("bus"));
                } else if (player.getType().equals("snow_cleaner")) {
                    SnowCleaner snowCleaner = (SnowCleaner) player;
                    newVehicle = snowCleaner.getSnowPlowers().get(snowCleaner.getSnowPlowers().size() - 1); // utolsó hókotró lehelyezése
                    newVehicle.setId(game.generateId("snow_plower"));
                } else {
                    Logger.log("> ERROR: Unknown player");
                    break;
                }

                // Jármű logikai elhelyezése a csomóponton és a várostérképen
                if (newVehicle != null) {
                    newVehicle.setCurrentPoint(startingPoint);
                    newVehicle.setLastLane(newVehicle.getCurrentPoint().getIncomingLanes().get(0));
                    startingPoint.addVehicle(newVehicle);
                    game.getCityMap().addVehicle(newVehicle);
                }
                break;
            }
                            
            case "exit": {
                // A szimulációs program biztonságos leállítása[cite: 216].
                return false;
            }
                            
            case "help": {
                // A szimulációs program segítségének megjelenítése[cite: 216].
                printHelp();
                break;
            }
                        
            default: {
                // Szabálytalan parancs esetén hibaüzenet[cite: 184, 189].
                Logger.log("> ERROR: Unknown command: " + command);
                break;
            }
        }
        return true;
    }

    public void saveGame(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String cmd : commandHistory) {
                writer.println(cmd);
            }
            System.out.println("> Game saved to " + filename);
        } catch (IOException e) {
            System.out.println("> ERROR: Could not save to file: " + e.getMessage());
        }
    }
    
    public void loadGame(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            Logger.log("> ERROR: File not found: " + filename);
            return;
        }

        // Betöltés előtt érdemes lehet alaphelyzetbe állítani a játékot:
        game = new Game(); 
        commandHistory.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // A fájlból olvasott parancsokat végrehajtjuk, így rekonstruálva a játék állapotát.
                processCommand(line);
            }
            Logger.log("> Game loaded from " + filename);
        } catch (IOException e) {
            Logger.log("> ERROR: Error reading file: " + e.getMessage());
        }
    }

    /**
     * Segédfüggvény a rendelkezésre álló parancsok és argumentumaik listázására.
     */
    public void printHelp() {
        Logger.log("--- Elérhető parancsok és használatuk ---");
        
        // Rendszervezérlő és lekérdező parancsok
        Logger.log("add_player <name> <role>");
        Logger.log("  role: snowcleaner | busdriver");
        Logger.log("  Leírás: Új játékos regisztrálása a szimulációs rendszerbe. [cite: 124, 125]");
        
        Logger.log("load <fájlnév.txt>");
        Logger.log("  Leírás: A programot egy előre megadott konfigurációból indítja el. [cite: 127, 128]");
        
        Logger.log("step [n]");
        Logger.log("  Leírás: A léptető parancs. Kiváltja a cselekvések tényleges végrehajtását. Opcionálisan megadható a lépések száma. [cite: 130, 136]");
        
        Logger.log("stat <objektum_id>");
        Logger.log("  Leírás: Állapotlekérdező parancs. A konzolra írja egy entitás összes aktuális, belső paraméterét. [cite: 145, 146]");
        
        Logger.log("move <vehicle_id> <lane_id>");
        Logger.log("  Leírás: Utasítja a megadott járművet, hogy a következő step alkalmával a megadott sávon át próbáljon haladni. [cite: 148, 149]");
        
        Logger.log("buy <player_id> <item_name> [quantity]");
        Logger.log("  Leírás: A Takarító (SnowCleaner) játékos tárgyat/nyersanyagot vásárol a Boltból. [cite: 151, 153]");
        
        Logger.log("equip <plower_id> <head_type>");
        Logger.log("  Leírás: Lecseréli a hókotrón lévő aktuális fejet egy másik, már birtokolt fejre. [cite: 155, 156]");
        
        Logger.log("save <fájlnév.txt>");
        Logger.log("  Leírás: A teljes aktuális szimulációs állapotot kimenti a megadott szöveges fájlba. [cite: 158, 162]");
        
        // Konfigurációs és pályaépítő parancsok
        Logger.log("\n--- Konfigurációs és pályaépítő parancsok ---");
        
        Logger.log("create_point <type>");
        Logger.log("  type: junction | crossroads | tunnel");
        Logger.log("  Leírás: Létrehoz egy új pontot. [cite: 167, 168]");

        Logger.log("create_lane <start_junction_id> <end_junction_id>");
        Logger.log("  Leírás: Létrehoz egy sávot, és logikailag összeköti a csomópontokat. [cite: 170, 171]");
        
        Logger.log("set_lane <lane_id> <is_jammed | is_underground | snow_level | ice | broken_ice | salt_level | crushed_Stone_level> <érték>");
        Logger.log("  Leírás: Tesztelési célból egy sáv paramétereit manuálisan, azonnal beállítja. [cite: 173, 174]");
        
        Logger.log("place_vehicle <position_id> [player_name]");
        Logger.log("  Leírás: Elhelyez egy járművet a megadott kezdőpozíción.");
        Logger.log("  Ha adunk nem adunk meg játékosnevet, akkor egy Car típusú jármű kerül lehelyezésre,");
        Logger.log("  ha pedig megadunk, akkor a játékostól függően egy Bus vagy SnowPlower lesz lehelyezve.");

        Logger.log("help");
        Logger.log("  Leírás: Megjeleníti a rendelkezésre álló parancsokat és azok használatát. [cite: 178, 179]");
        
        Logger.log("exit");
        Logger.log("  Leírás: A szimulációs program biztonságos leállítása és kilépés a parancssorból. ");
        Logger.log("-------------------------------------------");
    }
}