package game;

public class Lane {
    private Junction startpoint;
    private Junction endpoint;
    private Lane leftLane;
    private Lane rightLane;
    private int snowLevel;
    private boolean isJammed;
    private int iceLevel; //max =( frozen ) , how many cars crossed it
    private int saltLevel;

    public void change(){}
}
