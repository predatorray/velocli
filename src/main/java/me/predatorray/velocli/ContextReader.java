package me.predatorray.velocli;

import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.io.InputStream;

public interface ContextReader {

    void readAllInto(InputStream inputStream, VelocityContext context)
            throws IOException;
}
