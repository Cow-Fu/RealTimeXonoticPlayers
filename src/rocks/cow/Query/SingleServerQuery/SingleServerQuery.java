package rocks.cow.Query.SingleServerQuery;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rocks.cow.MasterServer.MasterServer;
import rocks.cow.Query.Query;
import rocks.cow.Server.Server;
import rocks.cow.Server.ServerBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServerQuery implements Query {
    public String targetServerIP = "";
    private XPath xpath;
    private Logger logger = Logger.getGlobal();

    public SingleServerQuery() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    public SingleServerQuery(String ip) {
        this();
        setTargetServerIP(ip);
    }

    public SingleServerQuery setTargetServerIP(String targetServerIP) {
        this.targetServerIP = targetServerIP;
        return this;
    }

    @Override
    public Document query() {
        Document doc = null;
        try {
            doc = MasterServer.singleServerQuery(targetServerIP);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to configure the parser!");

        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to connect to master server!");

        } catch (SAXException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to read data from the master server!");
        }
        return doc;
    }

    public Server getServer() throws XPathExpressionException {
        ServerBuilder serverBuilder = new ServerBuilder();
        Document doc = query();

        Node node = (Node) xpath.compile("/qstat/server")
                .evaluate(doc, XPathConstants.NODE);

        return serverBuilder.build(node);
    }
}
