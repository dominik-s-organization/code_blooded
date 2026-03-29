package game;

/**
 * Speciális csomópontot (alagutat) reprezentáló osztály az úthálzatban.
 * Mivel fedett, a környezeti hatások (havazás) jellemzően nem érintik, 
 * de biztosítja az áthaladást a járművek számára.
 */
public class Tunnel extends Point {
    
    public Tunnel() {
         super();
    }
    /**
     * Meghatározza, hogy az adott jármű ráléphet-e (behajthat-e) az alagútba.
     *
     * @param vehicle a vizsgált jármű, amely be szeretne hajtani az alagútba
     * @return true, ha a jármű ráléphet az alagútra, ellenkező esetben false
     */    
     public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> tunnel.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
     }

     public void lookForJams() {
        System.out.println("-> tunnel.lookForJams()");
     }
}
