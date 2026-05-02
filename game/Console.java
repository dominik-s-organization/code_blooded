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
    // game referencia, amelyen keresztül a konzol parancsokat hajt végre a játék állapotán.
    private Game game;

    public Console(Game game) {
        this.game = game;
    }

    public ArrayList<String> commandHistory = new ArrayList<>();

    public void ReadConsoleParams() {
        // A parancsok beolvasása a szabványos bemenetről (stdin) történik[cite: 105].
        // Try-with-resources használata a BufferedReader automatikus lezárásához.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            boolean isGoing = true;
            while (isGoing) {
                printHelp();
                String line;
                try {
                    System.out.print("> ");
                    line = reader.readLine();
                } catch (IOException e) {
                    // Amennyiben a beolvasás során hiba történik, hibaüzenetet küldünk[cite: 189].
                    System.out.println("> ERROR: Reading from standard input failed.");
                    break;
                }

                if (line != null && !line.trim().isEmpty()) {
                    // A bemeneti nyelv soronként értelmezett, szóközökkel elválasztva[cite: 115, 116].
                    isGoing = processCommand(line);
                }
            }
        } catch (IOException e) {
            // Kritikus hiba a standard input elérésekor.
            System.out.println("> ERROR: Critical system error while accessing console.");
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
                    System.out.println("> ERROR: Missing arguments for add_player command. Usage: add_player <name> <role>");
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
                        System.out.println("> ERROR: Invalid player type: " + args[2]);
                    }
                    break;
                }
                System.out.println("> ERROR: Maximum player limit reached.");
                break;
            }
                            
            case "load": {
                // Konfiguráció betöltése fájlból: <fájlnév.txt>[cite: 126, 128].
                if (args.length < 2) {
                    System.out.println("> ERROR: Missing filename.");
                } else {
                    loadGame(args[1]);
                    // A load-ot magát NEM mentjük a history-ba
                }
                break;
            }
                            
            case "step": {
                // Léptetés végrehajtása (Game.simulateStep()): [n][cite: 129, 131, 136].
                if (args.length > 2) {
                    System.out.println("> ERROR: Too many arguments for step command. Usage: step [n]");
                    break;
                }
                commandHistory.add(line);
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
                break;
            }
            case "stat": {
                // Objektum állapotának lekérdezése: <objektum_id>[cite: 144, 146].
                if (args.length < 2) {
                    System.out.println("> ERROR: Missing object ID for stat command.");
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
                System.out.println("> ERROR: Object with id " + id + " not found.");
                break;
            }         
            case "move": {
                // Jármű menetirányának kiválasztása: <vehicle_id> <lane_id>[cite: 147, 149].
                if(args.length < 3){
                    System.out.println("> ERROR: Missing arguments for move command. Usage: move <vehicle_id> <lane_id>");
                    break;
                }
                Vehicle v = game.getVehicleById(args[1]);
                Lane l = game.getLaneById(args[2]);
                if (v != null && l != null) {
                    if(args[1].contains("car_")){
                        System.out.println("> ERROR: Cars cannot be directly commanded to move. They determine their own path based on the city map and traffic conditions.");
                        break;
                    }
                    v.setNextLane(l);
                }
                break;
            }

            case "buy": {
                // Vásárlás a Bolttal: <player_id> <item_name> [quantity]
                if(args.length < 3){
                    System.out.println("> ERROR: Missing arguments for buy command. Usage: buy <player_id> <item_name> [quantity]");
                    break;
                }
                commandHistory.add(line);

                Player p = game.getPlayerByName(args[1]);
                if (p == null) {
                    System.out.println("> ERROR: Player with name " + args[1] + " not found.");
                    break;
                }
                
                String itemName = args[2];
                int quantity = 1;
                if (args.length > 3) {
                    try {
                        quantity = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("> ERROR: Invalid quantity. Please specify a valid number.");
                        break;
                    }
                }
                
                // Itt használjuk a polimorf getType() metódust az instanceof helyett!
                if ("snow_cleaner".equals(p.getType())) {         
                    SnowCleaner sc = (SnowCleaner) p;
                    game.getStore().buy(itemName, quantity, sc);
                } else {
                    System.out.println("> ERROR: Only SnowCleaner players can buy items from the shop.");
                }
                break;
            }           

            case "equip": {
                /**
                 * Hókotró fejének lecserélése a kívánt típusra.
                 * Szintaxis: equip <plower_id> <head_type>
                 */
                if (args.length < 3) {
                    System.out.println("> ERROR: Missing arguments for equip command. Usage: equip <plower_id> <head_type>");
                    break;
                }
                commandHistory.add(line);
                
                Vehicle v = game.getVehicleById(args[1]);
                if (v == null) {
                    System.out.println("> ERROR: Vehicle not found: " + args[1]);
                    break;
                }
                
                try {
                    // Ha a jármű egy Car vagy Bus, akkor kivételt dob, amit lent elkapunk.
                    SnowPlower sp = (SnowPlower) v;
                    
                    String headType = args[2].toLowerCase();
                    Head newHead = null;

                    // Az átadott string alapján eldöntjük, melyik fejet kell felszerelni
                    if (headType.contains("sweeping")) newHead = new SweepingHead();
                    else if (headType.contains("thrower")) newHead = new ThrowerHead();
                    else if (headType.contains("icebreaker")) newHead = new IceBreakerHead();
                    else if (headType.contains("salter")) newHead = new SalterHead();
                    else if (headType.contains("dragon")) newHead = new DragonHead();
                    else if (headType.contains("crushedstone")) newHead = new CrushedStoneHead();

                    if (newHead != null) {
                        sp.changeHead(newHead);
                    } else {
                        System.out.println("> ERROR: Unknown head type: " + headType);
                    }
                } catch (ClassCastException e) {
                    // Ha a kasztolás elszállt, akkor az objektum biztosan nem hókotró volt.
                    System.out.println("> ERROR: Vehicle is not a SnowPlower: " + args[1]);
                }
                break;
            }
                            
            case "save": {
                // Aktuális állapot kimentése: <fájlnév.txt>[cite: 157, 162].
                if (args.length < 2) {
                    System.out.println("> ERROR: Missing filename.");
                } else {
                    saveGame(args[1]);
                    // A save-et sem mentjük, mert betöltéskor nem akarunk újra menteni
                }
                break;
            }
                            
            case "create_point": {
                // Új csomópont létrehozása: <type>[cite: 166, 168].
                if (args.length < 3) {
                    System.out.println("> ERROR: Missing arguments for create_point command. Usage: create_point <point_type>");
                    break;
                }
                commandHistory.add(line);

                String type = args[1];
                if (!type.equals("junction") && !type.equals("crossroads") && !type.equals("tunnel")) {
                    System.out.println("> ERROR: Invalid point type: " + type + ". Valid types are: junction, crossroads, tunnel.");
                    break;
                }
                switch (type) {
                    case "junction":
                        Junction junction = new Junction();
                        junction.setId(game.generateId("point"));
                        game.getCityMap().addPoint(junction);
                        break;
                    case "crossroads":
                        CrossRoads crossroads = new CrossRoads();
                        crossroads.setId(game.generateId("point"));
                        game.getCityMap().addPoint(crossroads);
                        break;
                    case "tunnel":
                        Tunnel tunnel = new Tunnel();
                        tunnel.setId(game.generateId("point"));
                        game.getCityMap().addPoint(tunnel);
                        break;
                    default:
                        System.out.println("> ERROR: Invalid point type: " + type);
                        break;
                }
                break;
            }
                            
            case "create_lane": {
                // Új sáv létrehozása és bekötése: <start_junction_id> <end_junction_id>[cite: 169, 171].
                if (args.length < 3) {
                    System.out.println("> ERROR: Missing arguments for create_lane command. Usage: create_lane <start_junction_id> <end_junction_id>");
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
                    System.out.println("> ERROR: Missing arguments for set_lane command. Usage: set_lane <lane_id> <parameter> <value>");
                    break;
                }
                commandHistory.add(line);

                Lane laneToSet = game.getLaneById(args[1]);
                if (laneToSet == null) {
                    System.out.println("> ERROR: Lane not found: " + args[1]);
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
                            System.out.println("> ERROR: Unknown parameter: " + param);
                    }
                } catch (Exception e) {
                    System.out.println("> ERROR: Invalid value format for " + param + ". Check if it should be a number or true/false.");
                }
                break;
            }
                            

            case "place_vehicle": {
                /**
                 * Jármű lehelyezése a megadott kezdőpontra a város hálózatában.
                 * Szintaxis: place_vehicle <type> <entity_id> <position_id>
                 */
                if (args.length < 4) {
                    System.out.println("> ERROR: Missing arguments for place_vehicle command. Usage: place_vehicle <type> <entity_id> <position_id>");
                    break;
                }
                commandHistory.add(line);

                String type = args[1].toLowerCase();
                String vId = args[2];
                String pId = args[3];

                Point startingPoint = game.getPointById(pId);
                if (startingPoint == null) {
                    System.out.println("> ERROR: Starting point not found: " + pId);
                    break;
                }

                Vehicle newVehicle = null;
                if (type.equals("car")) {
                    newVehicle = new Car(vId);
                } else if (type.equals("bus")) {
                    newVehicle = new Bus(vId);
                } else if (type.equals("snow_plower")) {
                    newVehicle = new SnowPlower(vId);
                } else {
                    System.out.println("> ERROR: Unknown vehicle type: " + type);
                    break;
                }

                // Jármű logikai elhelyezése a csomóponton és a várostérképen
                if (newVehicle != null) {
                    newVehicle.setCurrentPoint(startingPoint);
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
                System.out.println("> ERROR: Unknown command: " + command);
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
            System.out.println("> ERROR: File not found: " + filename);
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
            System.out.println("> Game loaded from " + filename);
        } catch (IOException e) {
            System.out.println("> ERROR: Error reading file: " + e.getMessage());
        }
    }

    /**
     * Segédfüggvény a rendelkezésre álló parancsok és argumentumaik listázására.
     */
    public void printHelp() {
        System.out.println("--- Elérhető parancsok és használatuk ---");
        
        // Rendszervezérlő és lekérdező parancsok
        System.out.println("add_player <name> <role>");
        System.out.println("  role: snowcleaner | busdriver");
        System.out.println("  Leírás: Új játékos regisztrálása a szimulációs rendszerbe. [cite: 124, 125]");
        
        System.out.println("load <fájlnév.txt>");
        System.out.println("  Leírás: A programot egy előre megadott konfigurációból indítja el. [cite: 127, 128]");
        
        System.out.println("step [n]");
        System.out.println("  Leírás: A léptető parancs. Kiváltja a cselekvések tényleges végrehajtását. Opcionálisan megadható a lépések száma. [cite: 130, 136]");
        
        System.out.println("stat <objektum_id>");
        System.out.println("  Leírás: Állapotlekérdező parancs. A konzolra írja egy entitás összes aktuális, belső paraméterét. [cite: 145, 146]");
        
        System.out.println("move <vehicle_id> <lane_id>");
        System.out.println("  Leírás: Utasítja a megadott járművet, hogy a következő step alkalmával a megadott sávon át próbáljon haladni. [cite: 148, 149]");
        
        System.out.println("buy <player_id> <item_name> [quantity]");
        System.out.println("  Leírás: A Takarító (SnowCleaner) játékos tárgyat/nyersanyagot vásárol a Boltból. [cite: 151, 153]");
        
        System.out.println("equip <plower_id> <head_type>");
        System.out.println("  Leírás: Lecseréli a hókotrón lévő aktuális fejet egy másik, már birtokolt fejre. [cite: 155, 156]");
        
        System.out.println("save <fájlnév.txt>");
        System.out.println("  Leírás: A teljes aktuális szimulációs állapotot kimenti a megadott szöveges fájlba. [cite: 158, 162]");
        
        // Konfigurációs és pályaépítő parancsok
        System.out.println("\n--- Konfigurációs és pályaépítő parancsok ---");
        
        System.out.println("create_point <type>");
        System.out.println("  type: junction | crossroads | tunnel");
        System.out.println("  Leírás: Létrehoz egy új pontot. [cite: 167, 168]");

        System.out.println("create_lane <start_junction_id> <end_junction_id>");
        System.out.println("  Leírás: Létrehoz egy sávot, és logikailag összeköti a csomópontokat. [cite: 170, 171]");
        
        System.out.println("set_lane <lane_id> <is_jammed | is_underground | snow_level | ice | broken_ice | salt_level | crushed_Stone_level> <érték>");
        System.out.println("  Leírás: Tesztelési célból egy sáv paramétereit manuálisan, azonnal beállítja. [cite: 173, 174]");
        
        System.out.println("place_vehicle <type> <position_id>");
        System.out.println("  type: bus | car | snow_plower");
        System.out.println("  Leírás: Elhelyez egy járművet a megadott kezdőpozíción. [cite: 176]");

        System.out.println("help");
        System.out.println("  Leírás: Megjeleníti a rendelkezésre álló parancsokat és azok használatát. [cite: 178, 179]");
        
        System.out.println("exit");
        System.out.println("  Leírás: A szimulációs program biztonságos leállítása és kilépés a parancssorból. ");
        System.out.println("-------------------------------------------");
    }
}