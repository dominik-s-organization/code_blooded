package model;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Junction p1 = new Junction("P1");
        Junction p2 = new Junction("P2");
        game.getCityMap().addPoint(p1);
        game.getCityMap().addPoint(p2);
        Lane lane = new Lane("L1");
        game.getCityMap().addLane(lane);
        Vehicle vehicle = new Car("V1");
        MainFrame frame = new MainFrame(game);
        frame.setVisible(true);
    }
}