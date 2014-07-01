package me.predatorray.velocli;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        String templateFile;
        String propertiesFile = null;
        if (args.length == 1) {
            templateFile = args[0];
        } else if (args.length == 2) {
            templateFile = args[0];
            propertiesFile = args[1];
        } else {
            System.err.println("Invalid arguments.");
            System.err.println("Usage: <template-file> <properties-file>");
            System.exit(1);
            return;
        }

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("resource.loader", "input");
        ve.setProperty("input.resource.loader.instance",
                new PathBasedFileResourceLoader());
        ve.init();

        InputStream propertiesInput = (propertiesFile == null)
                ? System.in
                : new FileInputStream(propertiesFile);
        try {
            Properties properties = new Properties();
            properties.load(propertiesInput);

            Template t = ve.getTemplate(templateFile);
            VelocityContext context = new VelocityContext();
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement().toString();
                context.put(propertyName, properties.getProperty(propertyName));
            }

            Writer writer = new PrintWriter(System.out);
            t.merge(context, writer);
            writer.flush();
        } finally {
            propertiesInput.close();
        }
    }
}
