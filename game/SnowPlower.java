package game;

/**
 * A Takar�t� (SnowCleaner) j�t�kos �ltal ir�ny�tott h�kotr� munkag�pet reprezent�l� oszt�ly.
 * Felel�ss�ge a felszerelt takar�t�fej m�k�dtet�se az adott s�von t�rt�n� mozg�s sor�n.
 */
public class SnowPlower extends Vehicle {
    /**
     * A h�kotr�ra jelenleg felszerelt �s akt�v takar�t�fej.
     */
    private Head currentHead;
    /**
     * A h�kotr�t ir�ny�t� �s birtokl� j�t�kos (takar�t�).
     */
    private SnowCleaner owner;

    /**
     * A h�kotr� elakad�s�t (pl. �tk�z�s vagy j�rhatatlan �t miatt) kezel� met�dus.
     */
    @Override
    public void jam() {}

    /**
     * Lépteti a hókotrót a paraméterként kapott célállomás (Point) felé.
     *
     * @param point a cél csomópont, amely felé a hókotró haladni próbál
     */
    @Override
    public void move(Point point) {}
    
    /**
     * Lecseréli a hókotróra jelenleg felszerelt takarítófejet egy újra.
     *
     * @param head az új takarítófej (Head), amelyet a gépre szerelnek
     */
    public void changeHead(Head head) {
        this.currentHead = head;
    }
}
