package rocks.cow.Util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.Optional;
import java.util.OptionalInt;

public class XPathUtils {
    private XPath xpath;

    private static final String text = "/text()";  // for xpath's text property

    public XPathUtils() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    public Optional<String> getXPathAsString(String path, final Node node) {
        Optional<String> temp = Optional.empty();

        if (!path.endsWith(text)) {
            path += text;
        }

        try {
            temp = Optional.of((String) xpath.evaluate(path, node, XPathConstants.STRING));
        } catch (XPathExpressionException ignored) {}
        return temp;
    }

    public OptionalInt getXPathAsInt(String path, final Node node) {
        OptionalInt temp = OptionalInt.empty();
        if (!path.endsWith(text)) {
            path += text;
        }

        try {
            temp = OptionalInt.of(((Number) xpath.evaluate(path, node, XPathConstants.NUMBER)).intValue());
        } catch (XPathExpressionException ignored) {}
        return temp;
    }

    public Optional<Node> getXPathAsNode(String path, final Node node) {
        Optional<Node> temp = Optional.empty();
        try {
            temp = Optional.of((Node) xpath.evaluate(path, node, XPathConstants.NODE));
        } catch (XPathExpressionException ignored) {}
        return temp;
    }

    public Optional<NodeList> getXPathAsNodeList (final String path, final Node node) {
        Optional<NodeList> temp = Optional.empty();
        try {
            temp = Optional.of((NodeList) xpath.evaluate(path, node, XPathConstants.NODESET));
        } catch (XPathExpressionException ignored) {}
        return temp;
    }


}
