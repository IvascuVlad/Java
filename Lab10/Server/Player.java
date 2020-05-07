public class Player {
    private int playerPort;
    String name;

    public Player() {
        this.playerPort = -1;
    }

    public Player(int playerPort, String name) {
        this.name = name;
        this.playerPort = playerPort;
    }

    public int getPlayerPort() {
        return playerPort;
    }
}
