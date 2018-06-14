package com.beerboy.scanner.loader;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * {@link ResourceLoader} for loading class files.
 * <p>
 * <p>Can be configured to load inner classes or not.</p>
 *
 * @author manusant
 */
public class ClassResourceLoader implements ResourceLoader<Class<?>> {

    private ClassLoader classLoader;
    private boolean includeInnerClasses;

    public ClassResourceLoader(ClassLoader classLoader, boolean includeInnerClasses) {
        this.classLoader = classLoader;
        this.includeInnerClasses = includeInnerClasses;
    }


    @Override
    public Class<?> load(String packageName, JarFile jarFile, JarEntry entry) {
        return loadClassFromFile(packageName, StringUtils.substringAfterLast(entry.getName(), "/"));
    }

    @Override
    public Class<?> load(String packageName, File directory, String fileName) {
        return loadClassFromFile(packageName, fileName);
    }

    private Class<?> loadClassFromFile(String packageName, String fileName) {
        if ((fileName.endsWith(".class") && (includeInnerClasses || !fileName.contains("$")))) {
            try {
                return classLoader.loadClass(packageName + "." + StringUtils.substringBeforeLast(fileName, "."));
            } catch (ClassNotFoundException e) {
                // IGNORE
            }
        }
        return null;
    }
}
