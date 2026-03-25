package game;
import java.util.List;

/**
 * A h�eltakar�t�st v�gz� felhaszn�l�t (j�t�kost) reprezent�l� oszt�ly.
 * Nyilv�ntartja a j�t�kos vagyon�t, nyersanyagk�szleteit (s�, biokerozin), 
 * valamint az �ltala ir�ny�tott h�kotr�kat �s a rakt�ron l�v� takar�t�fejeket.
 */
public class SnowCleaner extends Player {
    /**
     * A j�t�kos �ltal birtokolt �s ir�ny�tott h�kotr�k (munkag�pek) list�ja.
     */
    private List<SnowPlower> snowPlowers;
    /**
     * A j�t�kos �ltal megv�s�rolt, de aktu�lisan egy g�pre sem felszerelt takar�t�fejek list�ja.
     */
    private List<Head> inventory;
    /**
     * A j�t�kos vagyona, amellyel a boltban (Store) gazd�lkodhat.
     */
    private int money;
    /**
     * A rendelkez�sre �ll� s�k�szlet a s�z� fej (SalterHead) m�k�dtet�s�hez.
     */
    private int saltStock;
    /**
     * A rendelkez�sre �ll� biokerozin k�szlet a s�rk�nyfej (DragonHead) m�k�dtet�s�hez.
     */
    private int bioKeroseneStock;

    /**
     * P�nz�sszeget �r j�v� a j�t�kos egyenleg�n (pl. sikeres takar�t�s ut�n).
     *
     * @param amount a j�v��rand� p�nz�sszeg
     */
    public void getPaid(int amount) {}

    /**
     * Cs�kkenti a megadott t�pus� nyersanyag k�szlet�t a fogyaszt�s sor�n.
     *
     * @param type a felhaszn�lt nyersanyag t�pusa (pl. "salt" vagy "biokerosene")
     */
    public void consumeMaterial(String type) {}
}

