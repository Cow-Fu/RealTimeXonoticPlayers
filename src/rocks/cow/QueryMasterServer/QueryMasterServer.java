package rocks.cow.QueryMasterServer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rocks.cow.MasterServer.MasterServer;
import rocks.cow.Server.Server;
import rocks.cow.Server.ServerBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;

public class QueryMasterServer {
    private XPath xpath;

    public QueryMasterServer() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    public Document getRawResult() throws IOException, SAXException, ParserConfigurationException {
        return MasterServer.masterServerQuery();
    }

    public ArrayList<Server> getServerList() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        ServerBuilder serverBuilder = new ServerBuilder();
        ArrayList<Server> servers = new ArrayList<Server>();
        Document doc = getRawResult();

        NodeList nodelist = (NodeList) xpath.compile("/qstat/server").evaluate(doc, XPathConstants.NODESET);

        for (int i = 1; i < nodelist.getLength(); ++i) {
            Node node = nodelist.item(i);
            servers.add(serverBuilder.build(node));
        }
        return servers;
    }
}
