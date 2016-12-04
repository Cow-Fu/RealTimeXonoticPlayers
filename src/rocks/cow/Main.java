package rocks.cow;

import org.xml.sax.SAXException;
import rocks.cow.Query.MasterServerQuery.MasterServerQuery;
import rocks.cow.Server.ServerList.ServerList;
import rocks.cow.TableRenderer.TableRendererImpl.MultiServerTable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.WARNING);
        logger.log(Level.INFO, "Huston, we have a logger.");

        Optional<ServerList> optServers = Optional.empty();
        ServerList servers;

        try {
            optServers = Optional.of(new MasterServerQuery().getServerList());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!optServers.isPresent()) {
            return;
        }

        servers = optServers.get();
        System.out.print(new MultiServerTable().Render(servers.getSortedListBy(ServerList.PLAYERS)));
    }
}
