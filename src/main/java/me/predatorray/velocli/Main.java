package me.predatorray.velocli;

import me.predatorray.velocli.util.SQLUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;

import java.io.*;
import java.util.*;

public class Main {

    private static final String STDIN = "-";

    private static final String MODE_PROP = "prop";
    private static final String MODE_XML_DOM = "xml";

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Invalid arguments.");
            System.err.println("Usage: <template-file> <input-format> <context-file>");
            System.exit(1);
            return;
        }
        String templateFile = args[0];
        String inputFormat = args[1];
        String contextFile = args[2];

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "input");
        ve.setProperty("input.resource.loader.instance",
                new PathBasedFileResourceLoader());
        ve.init();

        InputStream contextInput = (STDIN.equals(contextFile))
                ? System.in
                : new FileInputStream(contextFile);
        try {
            Template t = ve.getTemplate(templateFile);

            VelocityContext context = new VelocityContext();
            ContextReader reader;
            if (MODE_PROP.equals(inputFormat)) {
                reader = new PropertiesContextReader();
            } else if (MODE_XML_DOM.equals(inputFormat)) {
                reader = new XmlContextReader();
            } else {
                System.err.printf("Invalid input format: %s\n", inputFormat);
                System.exit(1);
                return;
            }
            reader.readAllInto(contextInput, context);

            initContext(context);

            Writer writer = new PrintWriter(System.out);
            t.merge(context, writer);
            writer.flush();
        } finally {
            contextInput.close();
        }
    }

    private static void initContext(VelocityContext context) {
        // keys
        Object[] keys = context.getKeys();
        Set<String> keySet = new HashSet<String>();
        for (Object key : keys) {
            keySet.add(String.valueOf(key));
        }
        Context ctx = new Context(keySet);
        context.put("context", ctx);

        // Escape Tool
        context.put("esc", new EscapeTool());

        // SQLUtils
        context.put("SQLUtils", new SQLUtils());
    }
}
