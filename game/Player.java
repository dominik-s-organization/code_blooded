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

    //Getter, setter
    public String getName() {
         return name;
     }
 
     public void setName(String name) {
         this.name = name;
     }
    /**
     * A játékos ezen a metóduson keresztül választja ki a következő célállomást
     * (csomópontot) az általa irányított jármű számára.
     *
     * @param vehicle a jármű, amelynek a célállomása kiválasztandó
     * @param nextLane a kiválasztott célállomás (Lane), amin a jármű át akar haladni
     */
    public void selectDestination(Vehicle vehicle, Lane nextLane) {
        vehicle.setNextLane(nextLane);
    }
}
