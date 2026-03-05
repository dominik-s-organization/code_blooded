package game;

public class SnowPlower extends Vehicle {
    private Head currentHead;
    private snowCleaner owner;

    @Override
    public void jam() {}

    @Override
    public void move() {}
    
    public void changeHead(Head head) {
        this.currentHead = head;
    }
}
