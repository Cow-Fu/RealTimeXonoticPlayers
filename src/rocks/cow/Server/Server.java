package rocks.cow.Server;

import rocks.cow.Player.PlayerList.PlayerList;

import java.util.HashMap;
import java.util.Optional;

public class Server {
    private String hostname;
    private String name;
    private Optional<String> gameType;
    private String map;
    private int numplayers;
    private int maxplayers;
    private int ping;
    private int retries;
    private HashMap<String, String> rules;
    private Optional<PlayerList> players;

    public Server(String hostname, String name, Optional<String> gameType, String map, int numplayers, int maxplayers, int ping, int retries, HashMap<String, String> rules, Optional<PlayerList> players) {
        this.hostname = hostname;
        this.name = name;
        this.gameType = gameType;
        this.map = map;
        this.numplayers = numplayers;
        this.maxplayers = maxplayers;
        this.ping = ping;
        this.retries = retries;
        this.rules = rules;
        this.players = players;
    }


    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getGameType() {
        return gameType;
    }

    public String getMap() {
        return map;
    }

    public int getNumplayers() {
        return numplayers;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public int getPing() {
        return ping;
    }

    public int getRetries() {
        return retries;
    }

    public HashMap<String, String> getRules() {
        return rules;
    }

    public String getRule(String rule) {
        return rules.get(rule);
    }

    public Optional<PlayerList> getPlayers() {
        return players;
    }
}
