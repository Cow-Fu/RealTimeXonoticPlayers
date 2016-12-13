package rocks.cow;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import org.xml.sax.SAXException;
import rocks.cow.Query.MasterServerQuery.MasterServerQuery;
import rocks.cow.Query.SingleServerQuery.SingleServerQuery;
import rocks.cow.Server.Server;
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
        Optional<String> optId;
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.WARNING);
        logger.log(Level.INFO, "Huston, we have a logger.");


        ArgumentParser parser = ArgumentParsers.newArgumentParser("Server")
                .defaultHelp(true)
                .description("An identifier for the server.");

        parser.addArgument("id").nargs("?").help("Server Id");
        optId = Optional.ofNullable(
                parser.parseArgsOrFail(args)
                        .getString("id")
        );

        if (optId.isPresent()) {
            System.out.println(optId.get());
            Server server;
            try {
                server = new SingleServerQuery(optId.get()).getServer();
                // System.out.println(server.getPlayers().);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

            server
            return;
        }
        Optional<ServerList> optServers = Optional.empty();

        try {
            optServers = Optional.of(new MasterServerQuery().getServerList());
        } catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e) {
            e.printStackTrace();
        }

        if (!optServers.isPresent()) return;

        System.out.print(new MultiServerTable().Render(optServers.get().getSortedListBy(ServerList.PLAYERS)));

    }
}
