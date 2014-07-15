package me.predatorray.velocli.util;

import me.predatorray.velocli.util.DomUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import java.util.Iterator;

public class XPathUtils {

    public Node node(String expression, Node node)
            throws XPathExpressionException {
        return (Node) eval(expression, node, XPathConstants.NODE);
    }

    public Iterator<Node> nodes(String expression, Node node)
            throws XPathExpressionException {
        NodeList nodeList = (NodeList) eval(expression, node,
                XPathConstants.NODESET);
        if (nodeList == null) {
            return null;
        }
        return new DomUtils().iter(nodeList);
    }

    public NodeList nodeList(String expression, Node node)
            throws XPathExpressionException {
        return (NodeList) eval(expression, node, XPathConstants.NODESET);
    }

    public String string(String expression, Node node)
            throws XPathExpressionException {
        return (String) eval(expression, node, XPathConstants.STRING);
    }

    public Boolean bool(String expression, Node node)
            throws XPathExpressionException {
        return (Boolean) eval(expression, node, XPathConstants.BOOLEAN);
    }

    public Object eval(String expression, Node node, QName qName)
            throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression xPathExpr = xpath.compile(expression);
        return xPathExpr.evaluate(node, qName);
    }
}
