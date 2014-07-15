package me.predatorray.velocli;

import me.predatorray.velocli.util.DomUtils;
import me.predatorray.velocli.util.XPathUtils;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlContextReader implements ContextReader {

    @Override
    public void readAllInto(InputStream inputStream, VelocityContext context)
            throws IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputStream);
            context.put("document", document);
            context.put("xpath", new XPathUtils());
            context.put("DomUtils", new DomUtils());
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        } catch (SAXException e) {
            throw new IllegalStateException(e);
        }
    }
}
