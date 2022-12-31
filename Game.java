package battleship;

import java.util.Scanner;

public class Game {
    static final String ENTER_COORDINATES_MSG = "Enter the coordinates of the %s (%d cells)";
    static final String TRY_AGAIN_MSG = "Try again";
    static final String START_GAME_MSG = "The game starts!";
    static final String TAKE_SHOT_MSG = "Take a shot!";
    static final String PLACE_SHIPS_MSG = "place your ships on the game field";
    static final String MOVE_TO_ANOTHER_PLAYER_MSG = "Press Enter and pass the move to another player";
    static final String TURN_MSG = "it's your turn";

    static String[] query;
    static String row;
    static String col;
    static TypeShip typeShip;

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player notActivePlayer;
    private Scanner input;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.input = new Scanner(System.in);
    }

    public void start() {
        placeShips(player1, player2);
        startShot();
    }

    private void placeShips(Player... players) {
        for (Player player : players) {
            System.out.printf("%s, %s\n\n", player.getName(), PLACE_SHIPS_MSG);
            player.getPlayground().display();

            for (int i = 0, len = TypeShip.values().length; i < len; ) {
                typeShip = TypeShip.getByOrdinal(i);

                System.out.printf(ENTER_COORDINATES_MSG, typeShip.getName(), typeShip.getLength());
                System.out.println();

                query = input.nextLine().split(" ");
                row = query[0];
                col = query[1];

                System.out.println();

                while (!isValidShipLocation(row, col)) {
                    System.out.printf("%s %s:\n", ErrorMsg.ERROR_WRONG_LOCATION, TRY_AGAIN_MSG);
                    query = input.nextLine().split(" ");
                    row = query[0];
                    col = query[1];
                }

                while (typeShip.getLength() != getPotentialLength(row, col)) {
                    System.out.printf("%s %s:\n", ErrorMsg.ERROR_WRONG_LENGTH, TRY_AGAIN_MSG);
                    query = input.nextLine().split(" ");
                    row = query[0];
                    col = query[1];
                }

                Ship ship = new Ship(row, col);

                while (!player.getPlayground().addShip(ship)) {
                    System.out.printf("%s %s:\n", ErrorMsg.ERROR_TOO_CLOSE, TRY_AGAIN_MSG);
                    query = input.nextLine().split(" ");
                    row = query[0];
                    col = query[1];
                    ship = new Ship(row, col);
                }

                player.getPlayground().display();

                i++;
            }

            System.out.println(MOVE_TO_ANOTHER_PLAYER_MSG);
            System.out.print("...");
            input.nextLine();
        }
    }

    private void startShot() {
        String shot;
        activePlayer = player2;
        notActivePlayer = player1;
        Playground activePlayground = notActivePlayer.getPlayground();

        while (!activePlayground.isWon()) {
            if (activePlayer.equals(player2)) {
                activePlayer = player1;
                notActivePlayer = player2;
            } else {
                activePlayer = player2;
                notActivePlayer = player1;
            }
            activePlayground = notActivePlayer.getPlayground();
            activePlayground.setFog(true);
            activePlayer.getPlayground().setFog(false);

            activePlayground.display();
            System.out.println("-".repeat(21));
            activePlayer.getPlayground().display();

            System.out.printf("%s, %s!\n", activePlayer.getName(), TURN_MSG);
            shot = input.nextLine();
            while (!isValidCoord(shot)) {
                System.out.printf("%s %s:\n", ErrorMsg.ERROR_WRONG_COORDINATES, TRY_AGAIN_MSG);
                shot = input.nextLine();
            }

            activePlayground.shoot(shot);
            if (!activePlayground.isWon()) {
                System.out.println(activePlayground.getMsg());
                System.out.println(MOVE_TO_ANOTHER_PLAYER_MSG);
                System.out.print("...");
                input.nextLine();
            }
        }
        System.out.println(activePlayground.getMsg());
    }

    private static boolean isValidShipLocation(String potentialRow, String potentialCol) {
        int x = Integer.parseInt(potentialRow.substring(1));
        int y = Integer.parseInt(potentialCol.substring(1));
        return potentialRow.charAt(0) == potentialCol.charAt(0) || x == y;
    }

    private static int getPotentialLength(String potentialRow, String potentialCol) {
        if (potentialRow.charAt(0) == potentialCol.charAt(0)) {
            return Math.abs(Integer.parseInt(potentialRow.substring(1)) - Integer.parseInt(potentialCol.substring(1))) + 1;
        }

        return Math.abs(potentialRow.charAt(0) - potentialCol.charAt(0)) + 1;
    }

    private static boolean isValidCoord(String coord) {
        int x = coord.charAt(0) - 65 + 1;
        int y = Integer.parseInt(coord.substring(1));

        return (x >= 1 && x <= 10) && (y >= 1 && y <= 10);
    }
}
