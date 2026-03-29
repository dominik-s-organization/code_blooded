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

<<<<<<< HEAD
=======
    public String getName() {
         System.out.println("-> player.getName()");
         System.out.println("<- name");
         return name;
     }
 
     public void setName(String name) {
         System.out.println("-> player.setName(name)");
         this.name = name;
     }
>>>>>>> origin/main
    /**
     * A játékos ezen a metóduson keresztül választja ki a következő célállomást
     * (csomópontot) az általa irányított jármű számára.
     *
     * @return a kiválasztott célállomás (Point), ahova a jármű lépni fog
     */
    public Point selectDestination() {
<<<<<<< HEAD
        // Implementáció majd itt
=======
        System.out.println("-> player.selectDestination()");
        System.out.println("<- nextPoint");     // Skeleton kedvéért, ez majd kicsit máshogy lesz
>>>>>>> origin/main
        return null;
    }
}
