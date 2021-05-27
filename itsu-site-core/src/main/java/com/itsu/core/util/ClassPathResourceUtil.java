package com.itsu.core.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Jerry Su
 * @Date 2021/5/27 14:10
 */
public class ClassPathResourceUtil {
    private static final PathMatchingResourcePatternResolver matchResource;

    static {
        matchResource = new PathMatchingResourcePatternResolver();
    }

    public Resource getResource(String location) {
        return matchResource.getResource(location);
    }

    public Resource[] getResources(String location) throws IOException {
        return matchResource.getResources(location);
    }

    public InputStream getInputStream(String location) throws IOException {
        Resource resource = getResource(location);
        return resource != null ? resource.getInputStream() : null;
    }

    public File getFile(String location) throws IOException {
        Resource resource = getResource(location);
        return resource != null ? resource.getFile() : null;
    }

    public URL getURL(String location) throws IOException {
        Resource resource = getResource(location);
        return resource != null ? resource.getURL() : null;
    }

}
