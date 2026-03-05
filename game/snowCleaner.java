package game;
import java.util.List;

public class snowCleaner extends Player {
    private List<SnowPlower> snowPlowers;
    private List<Head> inventory;
    private int money;
    private int saltStock;
    private int bioKeroseneStock;

    public snowCleaner(String name, List<SnowPlower> hokotrok, List<Head> inventory, int penz, int soKeszlet, int biokerozinKeszlet) {
        super(name);
        this.snowPlowers = hokotrok;
        this.inventory = inventory;
        this.money = penz;
        this.saltStock = soKeszlet;
        this.bioKeroseneStock = biokerozinKeszlet;
    }

    @Override
    public Junction selectDestination() {}

    public void getPaid(int osszeg) {}

    public boolean consumeMaterial(String tipus, int mennyiseg) {return false;}
}

