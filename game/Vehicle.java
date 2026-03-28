package game;

/**
 * Absztrakt �soszt�ly a mozg� entit�sok (aut�k, buszok, h�kotr�k) sz�m�ra.
 * Felel�ss�ge az aktu�lis poz�ci� �s az elakad�si id� nyilv�ntart�sa, 
 * valamint a mozg�s �s elakad�s alapvet� m�veleteinek defini�l�sa.
 */
public abstract class Vehicle { 
    /**
     * A j�rm� aktu�lis helyzete (csom�pontja) az �th�l�zatban.
     */
    private Point currentPoint;
    /**
     * A legut�bbi s�v, amelyen a j�rm� tart�zkodott, miel�tt a jelenlegi pontra l�pett.
     */
    private Lane lastLane;
    /**
     * A v�rakoz�si vagy elakad�si id� (k�r�kben m�rve). 
     * Am�g ez az �rt�k nagyobb null�n�l, a j�rm� nem tud mozogni.
     */
    private int jammedTime;

    public Vehicle() {
        currentPoint = null;
        lastLane = null;
        jammedTime = 0;
    }

    public Point getCurrentPoint() {
        System.out.println("-> vehicle.getCurrentPoint()");
        System.out.println("<- currentPoint");
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        System.out.println("-> vehicle.setCurrentPoint(currentPoint)");
        this.currentPoint = currentPoint;
    }

    public Lane getLastLane() {
        System.out.println("-> vehicle.getLastLane()");
        System.out.println("<- lastLane");
        return lastLane;
    }

    public void setLastLane(Lane lastLane) {
        System.out.println("-> vehicle.setLastLane(lastLane)");
        this.lastLane = lastLane;
    }

    public int getJammedTime() {
        System.out.println("-> vehicle.getJammedTime()");
        System.out.println("<- jammedTime");
        return jammedTime;
    }

    public void setJammedTime(int jammedTime) {
        System.out.println("-> vehicle.setJammedTime(jammedTime)");
        this.jammedTime = jammedTime;
    }
    /**
     * A j�rm� elakad�s�t, baleset�t vagy b�ntet�si idej�t kezel� absztrakt met�dus.
     * A lesz�rmazott oszt�lyok a saj�t logik�juk szerint val�s�tj�k meg.
     */
    public abstract void jam();

    /**
     * A j�rm�vet a megadott c�l�llom�s (pont) fel� mozgatja.
     *
     * @param point a c�l�llom�s (Point), ahova a j�rm� l�pni pr�b�l
     */
    public abstract void move(Point point);
}
