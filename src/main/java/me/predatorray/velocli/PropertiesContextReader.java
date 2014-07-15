package me.predatorray.velocli;

import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesContextReader implements ContextReader {

    @Override
    public void readAllInto(InputStream propertiesInput,
                            VelocityContext context) throws IOException {
        Properties properties = new Properties();
        properties.load(propertiesInput);

        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = propertyNames.nextElement().toString();
            String propertyValue = properties.getProperty(propertyName);
            String[] values = StringUtils.arraySplit(propertyValue, ',',
                    true);
            if (values.length == 1) {
                context.put(propertyName, values[0]);
            } else {
                context.put(propertyName, values);
            }
        }
    }
}
