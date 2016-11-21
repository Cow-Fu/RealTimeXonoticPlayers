package rocks.cow.TableRenderer.TableRendererImpl;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthAbsoluteEven;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import rocks.cow.Server.Server;
import rocks.cow.TableRenderer.TableRenderer;

import java.util.ArrayList;

public class MultiServerTable implements TableRenderer {
    private ArrayList<Server> servers = null;
    private V2_AsciiTable at;
    private V2_AsciiTableRenderer rend;


    public MultiServerTable() {
        at = new V2_AsciiTable()
        rend = new V2_AsciiTableRenderer();
        rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
        rend.setWidth(new WidthAbsoluteEven(76));
    }

    public MultiServerTable(ArrayList<Server> servers) {
        this();
        setServers(servers);
    }

    public MultiServerTable setServers(ArrayList<Server> servers) {
        this.servers = servers;
        return this;
    }

    public RenderedTable Render() {
        at.addRule();
        at.addRow()
        return null;
    }

}
