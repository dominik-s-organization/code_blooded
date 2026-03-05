package game;

abstract class Player {
    private String name;

    protected Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Junction selectDestination();
}