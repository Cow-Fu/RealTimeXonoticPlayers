package rocks.cow.Server;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rocks.cow.Player.PlayerBuilder;
import rocks.cow.Player.PlayerList.PlayerList;
import rocks.cow.Util.ColorStripper;
import rocks.cow.Util.XPathUtils;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

public class ServerBuilder {
    private XPathUtils xpath = new XPathUtils();
    private Logger logger = Logger.getGlobal();
    private PlayerBuilder playerBuilder = new PlayerBuilder();

    private Node node;

    private String hostname;
    private String name;
    private Optional<String> gameType;
    private String map;
    private int numplayers;
    private int maxplayers;
    private int ping;
    private int retries;
    private HashMap<String, String> rules;
    private Optional<PlayerList> players = Optional.empty();


    public ServerBuilder setNode(Node node) {
        this.node = node;
        return this;
    }

    public ServerBuilder setHostName() {
        xpath.getXPathAsString("hostname", node).ifPresent(this::setHostName);
        return this;
    }


    public ServerBuilder setHostName(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ServerBuilder setName() {
        xpath.getXPathAsString("name", node).ifPresent(this::setName);
        return this;
    }

    public ServerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ServerBuilder setGameType () {
        xpath.getXPathAsString("gametype", node).ifPresent(this::setGameType);
        return this;
    }

    public ServerBuilder setGameType (String gameType) {
        if (gameType.equals("")) { // this will need to be changed for issue #1
            this.gameType = Optional.empty();
        } else {
            this.gameType = Optional.of(gameType);
        }
        return this;
    }

    public ServerBuilder setMap() {
        xpath.getXPathAsString("map", node).ifPresent(this::setMap);
        return this;
    }

    public ServerBuilder setMap(String map) {
        this.map = map;
        return this;
    }

    public ServerBuilder setNumplayers() {
        xpath.getXPathAsInt("numplayers", node).ifPresent(this::setNumplayers);
        return this;
    }

    public ServerBuilder setNumplayers(int numplayers) {
        this.numplayers = numplayers;
        return this;
    }

    public ServerBuilder setMaxplayers() {
        xpath.getXPathAsInt("maxplayers", node).ifPresent(this::setMaxplayers);
        return this;
    }

    public ServerBuilder setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
        return this;
    }

    public ServerBuilder setPing() {
        xpath.getXPathAsInt("ping", node).ifPresent(this::setPing);
        return this;
    }

    public ServerBuilder setPing(int ping) {
        this.ping = ping;
        return this;
    }

    public ServerBuilder setRetries() {
        xpath.getXPathAsInt("retries", node).ifPresent(this::setRetries);
        return this;
    }

    public ServerBuilder setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public ServerBuilder setRules() {
        Optional<NodeList> optNodeList = xpath.getXPathAsNodeList("rule", node);
        if (optNodeList.isPresent()) {
            NodeList nodeList = optNodeList.get();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node nnode = nodeList.item(i);
                rules.put(((Element) nnode).getAttribute("name"), nnode.getTextContent());
            }
        }
        return this;
    }

    public ServerBuilder setRules(HashMap<String, String> rules) {
        this.rules = rules;
        return this;
    }

    public ServerBuilder setPlayers() {
        Optional<Node> optPlayerNode = xpath.getXPathAsNode("players", node);
        if (optPlayerNode.isPresent()) {
            Optional<NodeList> optPlayerNodes = xpath.getXPathAsNodeList("player", optPlayerNode.get());
            if (optPlayerNodes.isPresent()) {
                PlayerList players = new PlayerList();
                NodeList playerNodes = optPlayerNodes.get();
                for (int i = 0; i < playerNodes.getLength(); ++i) {
                    Node player = playerNodes.item(i);
                    xpath.getXPathAsString("name", player).ifPresent(name ->
                                playerBuilder.setName(ColorStripper.stripColorTags(name)));
                    xpath.getXPathAsInt("score", player).ifPresent(playerBuilder::setScore);
                    xpath.getXPathAsInt("ping", player).ifPresent(playerBuilder::setPing);
                    players.add(playerBuilder.createPlayer());
                }
                setPlayers(Optional.of(players));
            }
        }
        return this;
    }

    public ServerBuilder setPlayers(Optional<PlayerList> players) {
        this.players = players;
        return this;
    }

    public Server build(Node node) {
        return setNode(node)
                .setHostName()
                .setName()
                .setGameType()
                .setMap()
                .setNumplayers()
                .setMaxplayers()
                .setPing()
                .setRetries()
                .setRules()
                .setPlayers()
                .createServer();

    }

    public Server createServer() {
        return new Server(hostname, name, gameType, map, numplayers, maxplayers, ping, retries, rules, players);
    }

    // example

    /*
    <hostname>176.9.22.146:26000</hostname>
	<name>Xonotic 0.8.1 Server</name>
	<gametype />
    <map>oilrig</map>
    <numplayers>0</numplayers>
    <maxplayers>16</maxplayers>
    <ping>116</ping>
    <retries>0</retries>
    <rules>
        <rule name="gamename">Xonotic</rule>
        <rule name="modname">data</rule>
        <rule name="gameversion">801</rule>
        <rule name="bots">0</rule>
        <rule name="protocol">3</rule>
        <rule name="qcstatus">dm:0.8.1:P0:S16:F5:MXonotic::score!!</rule>
        <rule name="d0_blind_id">0 CRgIl/t7L3r+KgdZBf+YsOSFCkQOKh2+jP2ZVa/juYc=@Xon//KssdlzGkFKdnnN4sgg8H+koTbBn5JTi37BAW1Q=</rule>
        <rule name="hostname">Xonotic 0.8.1 Server</rule>
    </rules>
    <players>
    </players>
	</server>
     */
}
