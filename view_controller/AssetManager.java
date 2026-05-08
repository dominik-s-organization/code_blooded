package view_controller;

import java.util.Map;
import java.awt.Image;
import java.awt.Color;

// Az AssertManager osztály felelős a játékban használt ikonok és színek kezeléséért.
public class AssertManager {
    private Map<String, Image> vehicleIcons;
    private Map<String, Color> snowColors;
    private Map<String, Image> itemIcons;
    private Map<String, Image> playerIcons;
    private Map<String, Image> obstacleIcons;

    private void loadAssets() {
        // Itt töltsük be az ikonokat és színeket a fájlokból vagy erőforrásokból
    }

    public Image getIcon(String name) {
        return vehicleIcons.get(name);
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
}
