package game;

public abstract class Head {
    public boolean currentlyUsed;

    public abstract boolean used(Lane lane);
}
