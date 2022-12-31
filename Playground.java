package battleship;

public class Playground {
    public static final String WIN_MSG = "You sank the last ship. You won. Congratulations!";
    public static final String SANK_SHIP = "You sank a ship! Specify a new target";
    public static final String HIT_SHIP_MSG = "You hit a ship! Try again";
    public static final String MISSED_MSG = "You missed! Try again";
    private int row = 10;
    private int col = 10;
    private final char[][] fields;
    private final Ship[] ships = new Ship[5];
    private int shipCount = 0;
    private boolean fog;
    private String msg;

    public Playground() {
        fields = new char[row][col];
        init();
    }

    public void init() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                fields[i][j] = '~';
            }
        }
    }

    public void display() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char c;

        for (int i = 0; i < row; i++) {
            System.out.print((char) (i + 65));
            for (int j = 0; j < col; j++) {
                c = fields[i][j];
                if (fog) {
                    if (c == 'O') {
                        System.out.print(" ~");
                    } else {
                        System.out.printf(" %c", c);
                    }
                } else {
                    System.out.printf(" %c", c);
                }
            }
            System.out.println();
        }
    }

    public Ship[] getShips() {
        return ships;
    }

    public boolean addShip(Ship ship) {
        if (!isNear(ship)) {
            for (String point : ship.getCoords()) {
                int x = point.charAt(0) - 65;
                int y = Integer.parseInt(point.substring(1)) - 1;
                fields[x][y] = 'O';
            }
            ships[shipCount++] = ship;
            return true;
        }
        return false;
    }

    private boolean isNear(Ship ship) {
        String[] coords = ship.getCoords();
        for (Ship ship1 : ships) {
            if (ship1 != null) {
                for (String point : coords) {
                    if (ship1.getRadar().contains(point)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean shoot(String coord) {
        int x = coord.charAt(0) - 65;
        int y = Integer.parseInt(coord.substring(1)) - 1;

        boolean isTouched = false;

        msg = "";
        for (Ship ship : ships) {
            if (ship.isTouched(coord)) {
                isTouched = ship.isTouched(coord);
                if (ship.isDestroyed()) {
                    msg = SANK_SHIP;
                }
                break;
            }
        }

        if (isTouched) {
            fields[x][y] = 'X';
            msg = msg.isEmpty() ? HIT_SHIP_MSG : msg;
        } else {
            fields[x][y] = 'M';
            msg = msg.isEmpty() ? MISSED_MSG : msg;
        }

        return isTouched;
    }

    public boolean isWon() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        msg = WIN_MSG;
        return true;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isFog() {
        return fog;
    }

    public void setFog(boolean fog) {
        this.fog = fog;
    }
}