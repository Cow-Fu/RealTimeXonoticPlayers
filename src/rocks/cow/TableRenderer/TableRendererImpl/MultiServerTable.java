package rocks.cow.TableRenderer.TableRendererImpl;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import rocks.cow.Server.ServerList.ServerList;
import rocks.cow.TableRenderer.TableRenderer;


public class MultiServerTable extends TableRenderer {
    private ServerList servers = null;

    public MultiServerTable() {
        initTable();
    }

    public MultiServerTable(ServerList servers) {
        initTable();
        setServers(servers);
    }

    public MultiServerTable setServers(ServerList servers) {
        this.servers = servers;
        return this;
    }

    public RenderedTable render() {
        if (servers.equals(null)) {
            return null;
        }
        at.addRule();
        at.addRow("Address", "Server Name", "Players", "Map");
        at.addRule();
        servers.forEach(server -> {
                    at.addRow(
                            server.getAddress(),
                            server.getName(),
                            String.format("%s/%s", server.getNumplayers(), server.getMaxplayers()),
                            server.getMap()
                    );
                });
        at.addRule();

        return rend.render(at);
    }
}
