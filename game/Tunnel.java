package game;

/**
 * Speci�lis csom�pontot (alagutat) reprezent�l� oszt�ly az �th�l�zatban.
 * Mivel fedett, a k�rnyezeti hat�sok (havaz�s) jellemz�en nem �rintik, 
 * de biztos�tja az �thalad�st a j�rm�vek sz�m�ra.
 */
public class Tunnel extends Point {
    
    public Tunnel() {
        System.out.println("-> tunnel.Tunnel()");
        System.out.println("<- void");
    }
    /**
     * Meghat�rozza, hogy az adott j�rm� r�l�phet-e (behajthat-e) az alag�tba.
     *
     * @param vehicle a vizsg�lt j�rm�, amely be szeretne hajtani az alag�tba
     * @return true, ha a j�rm� r�l�phet az alag�tra, ellenkez� esetben false
     */    
     public boolean isReachable(Vehicle vehicle) {
        System.out.println("-> tunnel.isReachable(vehicle)");
        System.out.println("<- true");
        return true;
     }
}
