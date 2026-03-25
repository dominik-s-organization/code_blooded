package game;

/**
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálózatban.
 * Mivel fedett, a környezeti hatások (havazás) jellemzõen nem érintik, 
 * de biztosítja az áthaladást a jármûvek számára.
 */
public class Tunnel extends Point {
    /**
     * Meghatározza, hogy az adott jármû ráléphet-e (behajthat-e) az alagútba.
     *
     * @param vehicle a vizsgált jármû, amely be szeretne hajtani az alagútba
     * @return true, ha a jármû ráléphet az alagútra, ellenkezõ esetben false
     */    
     public boolean isReachable(Vehicle vehicle) {}
}
