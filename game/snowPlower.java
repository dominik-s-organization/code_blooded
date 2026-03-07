package game;

public class SnowPlower extends Vehicle {
    private Head currentHead;
    private SnowCleaner owner;

    @Override
    public void jam() {}

    @Override
    public void move(Point point) {}
    
    public void changeHead(Head head) {
        this.currentHead = head;
    }
}
