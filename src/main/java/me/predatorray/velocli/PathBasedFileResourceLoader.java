package me.predatorray.velocli;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PathBasedFileResourceLoader extends ResourceLoader {

    @Override
    public void init(ExtendedProperties configuration) {
    }

    @Override
    public InputStream getResourceStream(String filePath)
            throws ResourceNotFoundException {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        long res = resource.getLastModified();
        String filePath = resource.getName();
        File file = new File(filePath);
        long ori = file.lastModified();
        return ori > res;
    }

    @Override
    public long getLastModified(Resource resource) {
        String filePath = resource.getName();
        File file = new File(filePath);
        return file.lastModified();
    }

    @Override
    public boolean resourceExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
