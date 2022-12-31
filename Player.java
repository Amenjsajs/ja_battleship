package battleship;

public class Player {
    private String name;
    private final Playground playground;

    public Player(String name) {
        this.name = name;
        this.playground = new Playground();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Playground getPlayground() {
        return playground;
    }
}
