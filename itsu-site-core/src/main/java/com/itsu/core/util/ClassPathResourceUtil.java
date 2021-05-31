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

    public static Resource getResource(String location) {
        return matchResource.getResource(location);
    }

    public static Resource[] getResources(String location) throws IOException {
        return matchResource.getResources(location);
    }

    public static InputStream getInputStream(String location) throws IOException {
        Resource resource = getResource(location);
        return resource.getInputStream();
    }

    public static File getFile(String location) throws IOException {
        Resource resource = getResource(location);
        return resource.getFile();
    }

    public static URL getURL(String location) throws IOException {
        Resource resource = getResource(location);
        return resource.getURL();
    }

}
