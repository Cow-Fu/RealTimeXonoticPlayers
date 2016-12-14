package rocks.cow.TableRenderer;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.*;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;

public abstract class TableRenderer {
    protected V2_AsciiTable at = new V2_AsciiTable();
    protected V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();

    protected void initTable() {
        rend.setTheme(V2_E_TableThemes.PLAIN_7BIT_STRONG.get());
        rend.setWidth(new WidthLongestLine());
    }

    public abstract RenderedTable render();

}
