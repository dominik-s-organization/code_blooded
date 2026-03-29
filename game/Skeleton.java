package game;

import java.util.Scanner;

public class Skeleton {
    
    /**
    * Megvalósítja a tesztesetek menüjét, ahol a felhasználó kiválaszthatja, melyik tesztesetet szeretné futtatni.
    */
    public void menu() {
        System.out.println("Skeleton menu\n");
        System.out.println(
            "1. Havazás egy sávon\n" +
            "2. Autó megcsúszása és balesete jeges úton\n" +
            "3. Autó sikeres mozgása tiszta úton\n" +
            "4. Hókotró havat seper a szomszédos sávba (SweepingHead)\n" +
            "5. Hókotró jeget tör (IceBreakerHead)\n" +
            "6. Hókotró sót szór, amiből kifogy (SalterHead)\n" +
            "7. Játékos vásárol a boltban (Store)\n" +
            "8. Busz haladása a menetrend szerint\n" +
            "9. Sózó fej sikeres használata (SalterHead)\n" +
            "10. Sárkányfej (DragonHead) használata\n" +
            "11. Hó letaposása autó áthaladásakor\n" +
            "12. Hóhányó fej (ThrowerHead) használata\n" +
            "0. Kilépés a programból\n");

            int choice = -1;
            Scanner scanner = new Scanner(System.in);

            do {
                System.out.print("\nVálassz egy tesztesetet (1-12): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            test1();
                            break;
                        case 2:
                            test2();
                            break;
                        case 3:
                            test3();
                            break;
                        case 4:
                            test4();
                            break;
                        case 5:
                            test5();
                            break;
                        case 6:
                            test6();
                            break;
                        case 7:
                            test7();
                            break;
                        case 8:
                            test8();
                            break;
                        case 9:
                            test9();
                            break;
                        case 10:
                            test10();
                            break;
                        case 11:
                            test11();
                            break;
                        case 12:
                            test12();
                            break;
                        case 0:
                            System.out.println("Kilépés a programból...");
                            break;
                        default:
                            System.out.println("Ez nem egy érvényes egész szám!");
                            break;
                        }
                    } else {
                         System.out.println("Ez nem egy érvényes egész szám!");
                    }
            } while (choice != 0);
    }

    private void test1() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        g.setCity(cm);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test2() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Junction j3 = new Junction();
        Lane l12 = new Lane();
        Lane l23 = new Lane();
        Car c1 = new Car();
        Car c2 = new Car();
        cm.addLane(l12);
        cm.addLane(l23);
        cm.addPoint(j1);
        cm.addPoint(j2);
        cm.addPoint(j3);
        l12.setStartPoint(j1);
        l12.setEndPoint(j2);
        l23.setStartPoint(j2);
        l23.setEndPoint(j3);
        j1.addOutgoingLane(l12);
        j2.addIncomingLane(l12);
        j2.addOutgoingLane(l23);
        j3.addIncomingLane(l23);
        c1.setCurrentPoint(j1);
        c2.setCurrentPoint(j2);
        c1.setNextPoint(j2);
        c2.setNextPoint(j3);
        j1.addVehicle(c1);
        j2.addVehicle(c2);
        l12.getSnow().setIce(true);
        g.setCity(cm);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test3() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        Car c = new Car();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        c.setCurrentPoint(j1);
        c.setNextPoint(j2);
        j1.addVehicle(c);
        g.setCity(cm);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test4() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        Lane rightLane = new Lane();
        cm.addLane(lane);
        cm.addLane(rightLane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        rightLane.setStartPoint(j1);
        rightLane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        j1.addOutgoingLane(rightLane);
        j2.addIncomingLane(rightLane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test5() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        IceBreakerHead ibh = new IceBreakerHead();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);
        sc.addHead(ibh);
        sc.getSnowPlowers().get(0).setCurrentHead(ibh);
        lane.getSnow().setIce(true);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test6() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        SalterHead salth = new SalterHead();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);
        sc.addHead(salth);
        sc.getSnowPlowers().get(0).setCurrentHead(salth);
        sc.setSaltStock(0);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test7() {
        System.out.println("\nInicializálás:\n");
        SnowCleaner sc = new SnowCleaner();
        Store store = new Store();

        System.out.println("\nLefutás:\n");
        store.buy("DragonHead",1,sc);
    }

    private void test8() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        BusDriver bd = new BusDriver();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        bd.getBus().setCurrentPoint(j1);
        bd.getBus().setNextPoint(j2);
        j1.addVehicle(bd.getBus());
        g.setCity(cm);
        g.addPlayer(bd);
        bd.getBus().setBeginningPoint(j1);
        bd.getBus().setEndPoint(j2);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test9() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        SalterHead salth = new SalterHead();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);
        sc.addHead(salth);
        sc.getSnowPlowers().get(0).setCurrentHead(salth);
        sc.setSaltStock(100);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test10() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        DragonHead dh = new DragonHead();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);
        sc.addHead(dh);
        sc.getSnowPlowers().get(0).setCurrentHead(dh);
        sc.setBioKeroseneStock(100);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test11() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        Car c = new Car();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        c.setCurrentPoint(j1);
        c.setNextPoint(j2);
        j1.addVehicle(c);
        g.setCity(cm);
        lane.getSnow().setLevel(6);
        lane.getSnow().setVehiclesPassed(4);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }

    private void test12() {
        System.out.println("\nInicializálás:\n");
        Game g = new Game();
        CityMap cm = new CityMap();
        SnowCleaner sc = new SnowCleaner();
        ThrowerHead th = new ThrowerHead();
        Junction j1 = new Junction();
        Junction j2 = new Junction();
        Lane lane = new Lane();
        cm.addLane(lane);
        cm.addPoint(j1);
        cm.addPoint(j2);
        lane.setStartPoint(j1);
        lane.setEndPoint(j2);
        j1.addOutgoingLane(lane);
        j2.addIncomingLane(lane);
        sc.getSnowPlowers().get(0).setCurrentPoint(j1);
        sc.getSnowPlowers().get(0).setNextPoint(j2);
        j1.addVehicle(sc.getSnowPlowers().get(0));
        g.setCity(cm);
        g.addPlayer(sc);
        sc.addHead(th);
        sc.getSnowPlowers().get(0).setCurrentHead(th);

        System.out.println("\nLefutás:\n");
        g.simulateStep();
    }
}