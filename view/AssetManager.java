package view;

import java.util.HashMap;
import java.util.Map;
import java.awt.Image;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Az AssetManager osztály felelős a játékban használt grafikus erőforrások 
 * (ikonok, képek, színek) betöltéséért és memóriában tartásáért.
 * Ez biztosítja, hogy a képeket ne kelljen minden képkocka kirajzolásakor újra betölteni.
 */
public class AssetManager {
    /** A járművek ikonjait tároló szótár (Map). */
    private static Map<String, Image> vehicleIcons = new HashMap<>();
    
    /** A hószintekhez tartozó színeket tároló szótár. */
    private static Map<String, Color> snowColors = new HashMap<>();
    
    /** A megvásárolható tárgyak ikonjait tároló szótár. */
    private static Map<String, Image> itemIcons = new HashMap<>();
    
    /** A játékosok profilképeit tároló szótár. */
    private static Map<String, Image> playerIcons = new HashMap<>();
    
    /** Az akadályok ikonjait tároló szótár. */
    private static Map<String, Image> obstacleIcons = new HashMap<>();

    /**
     * Statikus inicializáló blokk, amely az osztály betöltésekor meghívja az erőforrások betöltését.
     */
    static {
        loadAssets();
    }

    private static void loadAssets() {
       try {
            // A kulcsoknak pontosan egyezniük kell az osztálynevekkel!
            vehicleIcons.put("Car", ImageIO.read(new File("car.png")));
            vehicleIcons.put("Bus", ImageIO.read(new File("bus.png")));
            vehicleIcons.put("SnowPlower", ImageIO.read(new File("snowplow.png")));
        } catch (Exception e) {
            System.out.println("Hiba a kepek betoltesekor: " + e.getMessage());
        }
    }

    public static Image getIcon(String name) {
        return vehicleIcons.get(name);
    }

    public static Image setIcon(String name, Image img) {
        return vehicleIcons.put(name, img);
    }

    public static Color getColor(String name) {
        return snowColors.get(name);
    }

    public static Image getItemIcon(String name) {
        return itemIcons.get(name);
    }

    public static Image getPlayerIcon(String name) {
        return playerIcons.get(name);
    }

    public static Image getObstacleIcon(String name) {
        return obstacleIcons.get(name);
    }

    public static Color getSnowColor(int snowLevel) {
        if (snowLevel == 0) {
            return new Color(100, 100, 100); // Sötétszürke tiszta aszfalt
        } else if (snowLevel <= 5) {
            return new Color(200, 200, 200); // Világos havas út
        } else if (snowLevel <= 10) {
            return new Color(235, 235, 235); // Nagyon havas út
        } else {
            return new Color(255, 255, 255); // Teljesen hófehér
        }
    }
}
