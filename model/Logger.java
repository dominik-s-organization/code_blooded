package game;

import java.util.ArrayList;
import java.util.List;

/**
 * A tesztel�si �s norm�l kimeneteket kezel� k�zponti napl�z� oszt�ly.
 */
public class Logger {
    /** 
     * Tesztel�i m�d kapcsol�ja. Ha true, ki�rja a konzolra is az �zeneteket.
     * Alap�rtelmezetten kikapcsolva tartjuk.
     */
    public static boolean testerMode = false;
    
    /** A kimenetek mem�ri�ban t�rolt list�ja a f�jlba ment�shez. */
    public static List<String> outputHistory = new ArrayList<>();

    /**
     * K�zponti ki�r� met�dus.
     * Mindig elt�rolja az �zenetet a list�ban, de csak tesztel�i m�dban �rja ki a konzolra.
     * @param message a ki�rand� �zenet
     */
    public static void log(String message) {
        outputHistory.add(message);
        if (!testerMode) {
            System.out.println(message);
        }
    }
    
    /**
     * T�rli az eddigi napl�zott kimeneteket (hasznos lehet a 'load' parancsn�l).
     */
    public static void clear() {
        outputHistory.clear();
    }

    /**
     * Kimenti a napl�zott kimeneteket a megadott f�jlba (pl. tesztel�shez).
     * @param filename a l�trehozand� txt f�jl neve
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