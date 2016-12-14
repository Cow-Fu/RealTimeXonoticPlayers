package rocks.cow.TableRenderer.TableRendererImpl;

import de.vandermeer.asciitable.v2.RenderedTable;
import rocks.cow.Player.PlayerList.PlayerList;
import rocks.cow.Server.Server;
import rocks.cow.Server.ServerList.ServerList;
import rocks.cow.TableRenderer.TableRenderer;

public class SingleServerTable extends TableRenderer {
    private Server server;

    public SingleServerTable() {
        initTable();
    }

    public SingleServerTable(Server server) {
        initTable();
        setServer(server);
    }

    public SingleServerTable setServer(Server server) {
        this.server = server;
        return this;
    }


    public RenderedTable render() {
        if (server.equals(null)) {
            return null;
        }
        at.addRule();
        at.addRow(String.format("Name: %s", server.getName()), String.format("Map: %s", server.getMap()), "Moo");
        at.addRule();
        if (server.getPlayers().isPresent()) {
            // System.out.println(server.getPlayers().get().size());
            // server.getPlayers().get().forEach(player -> System.out.println(player.getName()));
            at.addRow("Player Name", "Score", "Ping");
            at.addRule();
            server.getPlayers().get().getSortedListBy(PlayerList.SCORE).forEach(
                    player ->
                        at.addRow(player.getName(),
                                player.getScore(),
                                player.getPing()
                        )
            );
        } else {
            at.addRow(null, null, "N/A");
        }
        at.addRule();
        return rend.render(at);
    }
}
