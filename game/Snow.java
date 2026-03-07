package game;

public class Snow {
    private int level; //0-14-ig átkelhető, 15-30-ig már nem, 30 a max
    private int vehiclesPassed;
    private boolean ice;
    private boolean brokenIce;

    public void raise() {
        if (level < 30) {
            level++;
        }
    }

    public void raiseBy(int amount) {
        level = Math.min(level + amount, 30);
    }

      public void passVehicle() {}
}
