package rocks.cow.Server;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rocks.cow.Player.PlayerBuilder;
import rocks.cow.Player.PlayerList.PlayerList;

import javax.xml.xpath.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerBuilder {
    private XPath xpath;
    private XPathExpression expr;
    private XPathFactory xPathfactory;
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
    private static final String text = "/text()";  // for xpath's text property


    public ServerBuilder() {
        xPathfactory = XPathFactory.newInstance();
        xpath = xPathfactory.newXPath();
    }

    public ServerBuilder setNode(Node node) {
        this.node = node;
        return this;
    }

    public ServerBuilder setHostName() {
        setHostname(getXPathAsString("hostname", node));
        return this;
    }


    public ServerBuilder setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ServerBuilder setName() {
        setName(getXPathAsString("name", node));
        return this;
    }

    public ServerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ServerBuilder setGameType () {
        setGameType(getXPathAsString("gametype", node));
        return this;
    }

    public ServerBuilder setGameType (String gameType) {
        if (gameType.equals("")) {
            this.gameType = Optional.empty();
        } else {
            this.gameType = Optional.of(gameType);
        }
        return this;
    }

    public ServerBuilder setMap() {
        setMap(getXPathAsString("map", node));
        return this;
    }

    public ServerBuilder setMap(String map) {
        this.map = map;
        return this;
    }

    public ServerBuilder setNumplayers() {
        setNumplayers(getXPathAsInt("numplayers", node));
        return this;
    }

    public ServerBuilder setNumplayers(int numplayers) {
        this.numplayers = numplayers;
        return this;
    }

    public ServerBuilder setMaxplayers() {
        setMaxplayers(getXPathAsInt("maxplayers", node));
        return this;
    }

    public ServerBuilder setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
        return this;
    }

    public ServerBuilder setPing() {
        setPing(getXPathAsInt("ping", node));
        return this;
    }

    public ServerBuilder setPing(int ping) {
        this.ping = ping;
        return this;
    }

    public ServerBuilder setRetries() {
        setRetries(getXPathAsInt("retries", node));
        return this;
    }

    public ServerBuilder setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public ServerBuilder setRules() {
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xpath.evaluate("rule", node, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        if (nodeList != null) {
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
        Node playerNode = null;
        try {
            playerNode = (Node) xpath.evaluate("players", node, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        if (playerNode != null) {
            if (playerNode.hasChildNodes()) {
                PlayerList temp = new PlayerList();
                NodeList playerNodes = playerNode.getChildNodes();
                for (int i = 0; i < playerNodes.getLength(); ++i) {
                    Node player = playerNodes.item(i);
                    playerBuilder
                            .setName(getXPathAsString("name", player))
                            .setScore(getXPathAsInt("score", player))
                            .setPing(getXPathAsInt("ping", player));
                    temp.add(playerBuilder.createPlayer());
                }
                setPlayers(Optional.of(temp));
            } else {
                players = Optional.empty();
            }
        }
        return this;
    }

    public ServerBuilder setPlayers(Optional<PlayerList> players) {
        this.players = players;
        return this;
    }

    private String getXPathAsString(String path, final Node node) {
        String temp = "";

        if (!path.endsWith(text)) {
            path += text;
        }

        try{
            temp = (String) xpath.evaluate(path, node, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private int getXPathAsInt(String path, final Node node) {
        OptionalInt temp = OptionalInt.empty();
        if (!path.endsWith(text)) {
            path += text;
        }

        try{
            temp = OptionalInt.of(((Number) xpath.evaluate(path, node, XPathConstants.NUMBER)).intValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return temp.getAsInt();
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
