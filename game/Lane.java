package game;

public class Lane {
    private Point startpoint;
    private Point endpoint;
    private Lane leftLane;
    private Lane rightLane;
    private boolean isJammed;
    private boolean isUnderground;

    private int saltLevel;  //min = ( nem sózott ) , minden lépés után csökken
    private Snow snow;

    public void change(Vehicle vehicle){}   // vehicle lehet majd null, akkor csak a hó változik
}
