package rocks.cow.TableRenderer.TableRendererImpl;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import rocks.cow.Server.ServerList.ServerList;
import rocks.cow.TableRenderer.TableRenderer;


public class MultiServerTable implements TableRenderer {
    private V2_AsciiTable at = new V2_AsciiTable();
    private V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
    private ServerList servers;

    public MultiServerTable(ServerList servers) {
        rend.setTheme(V2_E_TableThemes.PLAIN_7BIT_STRONG.get());
        rend.setWidth(new WidthLongestLine());
        this.servers = servers;
    }

    public RenderedTable render() {
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

    public MultiServerTable setServers(ServerList servers) {
        this.servers = servers;
        return this;
    }
}
