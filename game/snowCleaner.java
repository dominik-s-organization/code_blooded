package game;
import java.util.List;

public class SnowCleaner extends Player {
    private List<SnowPlower> snowPlowers;
    private List<Head> inventory;
    private int money;
    private int saltStock;
    private int bioKeroseneStock;

    public void getPaid(int amount) {}

    public void consumeMaterial(String type) {}

    public void buyHead(Head head, int price) {}

    public void buyMaterial(String material, int price) {}
}

