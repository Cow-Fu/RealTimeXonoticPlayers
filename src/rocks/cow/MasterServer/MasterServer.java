package rocks.cow.MasterServer;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class MasterServer {
    private static String masterServerUrl = "http://dpmaster.deathmask.net/?game=xonotic&hide=empty&nocolors=1&xml=1&utf8=1";

    public static Document masterServerQuery() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(new URL(masterServerUrl).openConnection().getInputStream());
    }

    public static Document singleServerQuery(String ip) throws ParserConfigurationException, IOException, SAXException {
        String url = masterServerUrl + "&server=" + ip;
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(new URL(url).openConnection().getInputStream());
    }
}
