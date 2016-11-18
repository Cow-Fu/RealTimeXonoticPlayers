package rocks.cow.Player;

public class Player {
    private String name;
    private int score;
    private int ping;

    public Player(String name, int score, int ping) {
        this.name = name;
        this.score = score;
        this.ping = ping;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getPing() {
        return ping;
    }
}
