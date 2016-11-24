package rocks.cow.Server;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rocks.cow.Player.PlayerList.PlayerList;

import javax.xml.xpath.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerBuilder {
    private XPathFactory xPathfactory;
    private XPath xpath;
    private XPathExpression expr;
    private Logger logger = Logger.getGlobal();

    private String hostname;
    private String name;
    private Optional<String> gameType;
    private String map;
    private int numplayers;
    private int maxplayers;
    private int ping;
    private int retries;
    private HashMap<String, String> rules;
    private PlayerList players;
    private static final String text = "/text()";


    public ServerBuilder() {
        xPathfactory = XPathFactory.newInstance();
        xpath = xPathfactory.newXPath();
    }


    public ServerBuilder setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ServerBuilder setName(String name) {
        this.name = name;
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

    public ServerBuilder setMap(String map) {
        this.map = map;
        return this;
    }

    public ServerBuilder setNumplayers(int numplayers) {
        this.numplayers = numplayers;
        return this;
    }

    public ServerBuilder setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
        return this;
    }

    public ServerBuilder setPing(int ping) {
        this.ping = ping;
        return this;
    }

    public ServerBuilder setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public ServerBuilder setRules(HashMap<String, String> rules) {
        this.rules = rules;
        return this;
    }

    private String getXPathAsString(final String path, final Node node) {
        String temp = "";
        try{
            if (path.endsWith(text)) {
                temp = (String) xpath.evaluate(path, node, XPathConstants.STRING);
            } else {
                temp = (String) xpath.evaluate(path + text, node, XPathConstants.STRING);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private int getXPathAsInt(String path, final Node node) {
        return Integer.parseInt(getXPathAsString(path, node));
    }

    public Server build(Node node) {
        try {

            setHostname(getXPathAsString("hostname", node));

            logger.log(Level.INFO, String.format("Reading information from ip: %s", hostname));

            setName(getXPathAsString("name", node));

            setGameType(getXPathAsString("gametype", node));

            setMap(getXPathAsString("map", node));


            setNumplayers(getXPathAsInt("numplayers", node));

            setMaxplayers(getXPathAsInt("maxplayers", node));

            setPing(getXPathAsInt("ping", node));

            setRetries(getXPathAsInt("retries", node));

            NodeList nodeList = (NodeList) xpath.evaluate("rule", node, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node nnode = nodeList.item(i);
                rules.put(((Element) nnode).getAttribute("name"), nnode.getTextContent());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return createServer();

    }

    public Server createServer() {
        return new Server(hostname, name, gameType, map, numplayers, maxplayers, ping, retries, rules);
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
