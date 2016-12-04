package rocks.cow.Query.SingleServerQuery;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import rocks.cow.MasterServer.MasterServer;
import rocks.cow.Query.Query;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServerQuery implements Query {
    public String targetServerIP = "";
    private Logger logger = Logger.getGlobal();

    public SingleServerQuery() {}

    public SingleServerQuery(String ip) {
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
}
