package view;

import java.util.Map;
import java.awt.Image;
import java.awt.Color;

// Az AssertManager osztály felelős a játékban használt ikonok és színek kezeléséért.
public class AssetManager {
    private static Map<String, Image> vehicleIcons;
    private static Map<String, Color> snowColors;
    private static Map<String, Image> itemIcons;
    private static Map<String, Image> playerIcons;
    private static Map<String, Image> obstacleIcons;

    private void loadAssets() {
        // Itt töltsük be az ikonokat és színeket a fájlokból vagy erőforrásokból
    }

    public Image getIcon(String name) {
        return vehicleIcons.get(name);
    }

    public Image setIcon(String name, Image img) {
        return vehicleIcons.put(name, img);
    }

    public Color getColor(String name) {
        return snowColors.get(name);
    }

    public Image getItemIcon(String name) {
        return itemIcons.get(name);
    }

    public Image getPlayerIcon(String name) {
        return playerIcons.get(name);
    }

    public Image getObstacleIcon(String name) {
        return obstacleIcons.get(name);
    }

    public static Color getSnowColor(int snowLevel) {
        if (snowLevel == 0) {
            return new Color(200, 200, 200); // világosszürke
        } else if (snowLevel <= 5) {
            return new Color(255, 255, 255); // fehér
        } else if (snowLevel <= 10) {
            return new Color(220, 220, 220); // világosabb szürke
        } else if (snowLevel <= 15) {
            return new Color(180, 180, 180); // sötétebb szürke
        } else {
            return new Color(150, 150, 150); // nagyon sötét szürke
        }
    }
}
