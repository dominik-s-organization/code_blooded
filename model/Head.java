package model;

import java.io.Serializable;
/**
 * A Head osztály egy absztrakt ősosztály, amely a hókotrókra felszerelhető 
 * különböző takarítófejeket reprezentálja a játékban.
 */
public abstract class Head implements Serializable {
    /**
     * A fej tisztítja, kezeli a megadott sávot a saját típusának megfelelő logikával.
     * @param lane A sáv (út), amelyet a fej kezelni fog.
     * @param snowPlower A hóeltávolító jármű, amelyre a fej szerelve van.
     */
    public abstract void clean(Lane lane, SnowPlower snowPlower);
    /**
     * Visszaadja a fej pontos típusát (osztálynevét) szöveges formában.
     * @return A fej típusa (pl. "DragonHead", "SweepingHead").
     */
    public abstract String getType();
}
