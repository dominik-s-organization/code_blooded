package model;

/**
 * A Takarító (SnowCleaner) játékos által irányított hókotró munkagépet reprezentáló osztály.
 * Felelőssége a felszerelt takarítófej működtetése az adott sávon történő mozgás során.
 */
public class SnowPlower extends Vehicle {
    /**
     * A hókotróra jelenleg felszerelt és aktív takarítófej.
     */
    private Head currentHead;
    /**
     * A hókotrót irányító és birtokló játékos (takarító).
     */
    private SnowCleaner owner;
    /**
     * Konstruktor a jármű egyedi azonosítójának beállításával.
     * @param id A hókotró azonosítója.
     */
    public SnowPlower() {
        super();
        currentHead = new SweepingHead(); 
        owner = null;
    }

    public SnowPlower(String id) {
        super(id);
        currentHead = new SweepingHead();
        owner = null;
    }

    public Lane getNextLane() {
        return nextLane;
    }

    public Head getCurrentHead() {
        return currentHead;
    }

    public void setCurrentHead(Head currentHead) {
        this.currentHead = currentHead;
    }

    public SnowCleaner getOwner() {
        return owner;
    }
     public void setOwner(SnowCleaner owner) {
        this.owner = owner;
    }
    /**
     * A hókotró elakadását (pl. baleset) kezelő metódus.
     * Karambol esetén a gép 1 körből kimarad.
     */
    @Override
    public void jam() {
        super.setJammedTime(1);
        Logger.log("> ACTION: " + this.getId() + " jammed_at " + this.getCurrentPoint().getId());
    }
    /**
     * A jármű interakcióba lép a sávval. Ha van felszerelt feje, akkor végrehajtja 
     * a takarítási folyamatot az adott útszakaszon.
     * @param lane A sáv, amellyel a jármű kapcsolatba lép.
     */
    @Override
    public void interactWithLane(Lane lane) {
        if(lane == null) return;

        if (this.currentHead != null) {
            this.currentHead.clean(lane, this);
        } else{
            Snow snow = lane.getSnow();
            if(snow != null){
                snow.passVehicle();
            }
        }
    }

    /**
     * Fizikailag lépteti a hókotrót a paraméterként kapott célállomás (Point) felé.
     * Levonja az előző sávból és rögzíti a következőn, miközben meghívja az interakciót (takarítást).
     * @param point A cél csomópont, amely felé a hókotró halad.
     */
    @Override
    public void move(Point point) {
        if (super.getJammedTime() > 0) {
            return; 
        }
        if (point.isReachable(this)) {
            super.getCurrentPoint().removeVehicle(this);
            super.setCurrentPoint(point);
            super.getCurrentPoint().addVehicle(this);
            super.setLastLane(nextLane);
            this.interactWithLane(super.getLastLane());
            super.setNextLane(null);
            Logger.log("> ACTION: " + this.getId() + " moved_to " + point.getId());
        } 
    }
    /**
     * Lecseréli a hókotróra jelenleg felszerelt takarítófejet egy újra az Inventory-ból.
     * @param head Az új takarítófej (Head), amelyet a gépre szerelnek.
     */
    public void changeHead(Head head) {
        this.currentHead = head;
    }

    @Override
    public void stat() {
        Logger.log("SnowPlower ID: " + getId());
        Logger.log("Type: SnowPlower");
        Logger.log("Current Point: " + (getCurrentPoint() != null ? getCurrentPoint().getId() : "null"));
        Logger.log("Last Lane: " + (getLastLane() != null ? getLastLane().getId() : "null"));
        Logger.log("Next Lane: " + (getNextLane() != null ? getNextLane().getId() : "null"));
        Logger.log("Jammed Time: " + getJammedTime());
        Logger.log("Can Slip: " + canSlip);
        Logger.log("Current Head: " + (getCurrentHead() != null ? getCurrentHead().getClass().getSimpleName() : "null"));
        Logger.log("Owner: " + (getOwner() != null ? getOwner().getName() : "null"));
        Logger.log("");
    }
}
