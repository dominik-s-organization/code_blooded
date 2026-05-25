package model;

import java.util.List;
import java.util.Random;

/**
 * A TrafficGenerator felelős a járművek (autók, buszok) véletlenszerű 
 * elhelyezéséért és célállomásaik (otthon, munkahely) kiosztásáért.
 */
public class TrafficGenerator {

    public static void spawnVehicles(Game game, int carCount, int busCount) {
        CityMap map = game.getCityMap();
        List<Point> allPoints = map.getPoints();
        Random rand = new Random();

        if (allPoints.isEmpty()) {
            return; // Biztonsági ellenőrzés: ha nincs térkép, ne csináljon semmit
        }

        // --- AUTÓK LÉTREHOZÁSA ---
        for (int i = 1; i <= carCount; i++) {
            Point homePoint = allPoints.get(rand.nextInt(allPoints.size()));
            Point workPoint;
            do {
                workPoint = allPoints.get(rand.nextInt(allPoints.size()));
            } while (workPoint.equals(homePoint));

            Car car = new Car("car_" + i);
            car.setHome(homePoint);
            car.setWork(workPoint);
            
            // Autó rárakása a kezdőpontra
            car.setCurrentPoint(homePoint);
            homePoint.addVehicle(car);
            map.addVehicle(car);
        }

        // --- BUSZOK ÉS SOFŐRÖK LÉTREHOZÁSA ---
        for (int i = 1; i <= busCount; i++) {
            Point stationA = allPoints.get(rand.nextInt(allPoints.size()));
            Point stationB;
            do {
                stationB = allPoints.get(rand.nextInt(allPoints.size()));
            } while (stationB.equals(stationA));

            // Létrehozzuk a Játékost (Buszsofőrt)
            BusDriver busDriver = new BusDriver("Busdriver_" + i);

            // Lekérjük a sofőrhöz tartozó buszt (vagy adunk neki egyet)
            Bus bus = busDriver.getBus();
            if (bus == null) { // Biztonsági ellenőrzés, ha a BusDriver nem hozná létre automatikusan
                bus = new Bus("bus_" + i);
                busDriver.setBus(bus); // Asszociáljuk a buszt a sofőrrel
            } else {
                bus.setId("bus_" + i);
            }

            // Beállítjuk a busz útvonalát
            bus.setBeginningPoint(stationA);
            bus.setEndPoint(stationB);
            
            // Busz rárakása a kezdőpontra (A állomásra)
            bus.setCurrentPoint(stationA);
            stationA.addVehicle(bus);
            map.addVehicle(bus);

            game.addPlayer(busDriver);
        }
    }
}
