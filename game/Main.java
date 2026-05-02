package game;

public class Main {
    public static void main(String[] args) {
        Game myGame = new Game();
        Console console = new Console(myGame);
        console.ReadConsoleParams();
    }
}