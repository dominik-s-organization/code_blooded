package game;

/**
 * Absztrakt ősosztály a felhasználókat (játékosokat) reprezentáló entitások számára.
 * Közös felületet biztosít a játékosok döntéshozatalához a körök során.
 */
public abstract class Player {
    /**
     * A játékos azonosítója vagy neve.
     */
    private String name;

    /**
     * A játékos ezen a metóduson keresztül választja ki a következő célállomást
     * (csomópontot) az általa irányított jármű számára.
     *
     * @return a kiválasztott célállomás (Point), ahova a jármű lépni fog
     */
    public Point selectDestination() {
        // Implementáció majd itt
        return null;
    }
}