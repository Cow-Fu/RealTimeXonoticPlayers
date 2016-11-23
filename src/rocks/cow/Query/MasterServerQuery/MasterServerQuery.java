package rocks.cow.Query.MasterServerQuery;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rocks.cow.MasterServer.MasterServer;
import rocks.cow.Query.Query;
import rocks.cow.Server.ServerBuilder;
import rocks.cow.Server.ServerList.ServerList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class MasterServerQuery implements Query {
    private XPath xpath;
    private Logger logger = Logger.getGlobal();

    public MasterServerQuery() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    @Override
    public Document query() {
        Document doc = null;
        try {
             doc = MasterServer.masterServerQuery();
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

    public ServerList getServerList() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        ServerBuilder serverBuilder = new ServerBuilder();
        ServerList servers = new ServerList();
        Document doc = query();

        NodeList nodelist = (NodeList) xpath.compile("/qstat/server")
                .evaluate(doc, XPathConstants.NODESET);

        IntStream
                .range(1, nodelist.getLength())
                .forEach(i -> servers.add(serverBuilder.build(nodelist.item(i))));

        return servers;
    }
}
