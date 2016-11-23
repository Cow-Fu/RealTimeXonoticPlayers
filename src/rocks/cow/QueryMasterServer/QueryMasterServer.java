package rocks.cow.QueryMasterServer;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import rocks.cow.MasterServer.MasterServer;
import rocks.cow.Server.ServerBuilder;
import rocks.cow.Server.ServerList.ServerList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.stream.IntStream;

public class QueryMasterServer {
    private XPath xpath;

    public QueryMasterServer() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    public Document getRawResult() throws IOException, SAXException, ParserConfigurationException {
        return MasterServer.masterServerQuery();
    }

    public ServerList getServerList() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        ServerBuilder serverBuilder = new ServerBuilder();
        ServerList servers = new ServerList();
        Document doc = getRawResult();

        NodeList nodelist = (NodeList) xpath.compile("/qstat/server")
                .evaluate(doc, XPathConstants.NODESET);

        IntStream
                .range(1, nodelist.getLength())
                .forEach(i -> servers.add(serverBuilder.build(nodelist.item(i))));

        return servers;
    }
}
