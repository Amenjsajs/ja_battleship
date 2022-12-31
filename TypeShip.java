package battleship;

public enum TypeShip {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);


    private final String name;
    private final int length;

    TypeShip(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public static TypeShip getByOrdinal(int i) {
        for (TypeShip typeShip : values()) {
            if (typeShip.ordinal() == i) {
                return typeShip;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
}
