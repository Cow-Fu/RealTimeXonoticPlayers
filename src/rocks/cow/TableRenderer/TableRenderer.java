package rocks.cow.TableRenderer;

import de.vandermeer.asciitable.v2.RenderedTable;
import rocks.cow.Server.ServerList.ServerList;

public interface TableRenderer {
    RenderedTable Render(ServerList servers);
}
