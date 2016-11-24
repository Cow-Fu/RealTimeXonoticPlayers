package rocks.cow.Player;

public class PlayerBuilder {
    private String name;
    private int score;
    private int ping;

    public PlayerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder setScore(int score) {
        this.score = score;
        return this;
    }

    public PlayerBuilder setPing(int ping) {
        this.ping = ping;
        return this;
    }

    public Player createPlayer() {
        return new Player(name, score, ping);
    }
}
