package battleship;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(new Player("Player 1"), new Player("Player 2"));
        game.start();
    }
}
