package view_controller;

import java.util.Map;
import java.awt.Image;
import java.awt.Color;
import java.util.HashMap;
import model.Game;

public class AssertManager {
    private Map<String, Image> vehicleIcons;
    private Map<String, Color> actualAsserts;
    private Map<String, Object> expectedAsserts;

    public void addAssert(String name, Object value) {
        asserts.put(name, value);
    }

    public void getIcon(String name) {
        // Return the icon associated with the given name
    }
}
