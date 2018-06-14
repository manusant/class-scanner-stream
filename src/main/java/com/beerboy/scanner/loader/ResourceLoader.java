package com.beerboy.scanner.loader;


import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Player responsible for loading a resource from the classpath.
 * <p>
 * <p>By using an interface, the {@link com.beerboy.scanner.PackageScanner} can be used to discover different
 * types of resources.</p>
 * <p>
 * <p>The most typical use will be to load classes with {@link ClassResourceLoader}.</p>
 *
 * @author manusant
 */
public interface ResourceLoader<T> {

    T load(String packageName, JarFile jarFile, JarEntry entry);

    T load(String packageName, File directory, String fileName);
}
