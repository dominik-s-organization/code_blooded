package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A tesztelési és normál kimeneteket kezelő központi naplózó osztály.
 */
public class Logger {
    /** 
     * Tesztelői mód kapcsolója. Ha true, kiírja a konzolra is az üzeneteket.
     * Alapértelmezetten kikapcsolva tartjuk.
     */
    public static boolean testerMode = false;
    
    /** A kimenetek memóriában tárolt listája a fájlba mentéshez. */
    public static List<String> outputHistory = new ArrayList<>();

    /**
     * Központi kiíró metódus.
     * Mindig eltárolja az üzenetet a listában, de csak tesztelői módban írja ki a konzolra.
     * @param message a kiírandó üzenet
     */
    public static void log(String message) {
        outputHistory.add(message);
        if (!testerMode) {
            System.out.println(message);
        }
    }
    
    /**
     * Törli az eddigi naplózott kimeneteket.
     */
    public static void clear() {
        outputHistory.clear();
    }

    /**
     * Kimenti a naplózott kimeneteket a megadott fájlba.
     * @param filename a létrehozandó txt fájl neve
     */
    public static void save(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            for (String logLine : outputHistory) {
                writer.println(logLine);
            }
            System.out.println("> Output successfully saved to " + filename);
        } catch (java.io.IOException e) {
            System.out.println("> ERROR: Could not save to file: " + e.getMessage());
        }
    }
}