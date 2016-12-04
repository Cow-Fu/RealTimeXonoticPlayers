package rocks.cow.Player.PlayerList;

import rocks.cow.Player.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class PlayerList extends ArrayList<Player> {
    // TODO: Add other options to retrieve servers by
    public static final byte SCORE = 0;
    public static final byte NAME = 1;

    public PlayerList getSortedListBy(byte key) {
        PlayerList temp = new PlayerList();
        switch (key) {
            case 0: // Score
                this.stream()
                        .filter(player -> player.getScore() > 0)
                        .sorted(Comparator.comparingInt(Player::getScore).reversed())
                        .forEach(temp::add);
                break;
            case 1:
                this.stream()
                        .sorted(Comparator.comparing(Player::getName))
                        .forEach(temp::add);
                break;
            default:
                return null;
        }
        return temp;
    }
}
