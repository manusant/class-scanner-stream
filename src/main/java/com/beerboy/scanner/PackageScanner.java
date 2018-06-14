package com.beerboy.scanner;

import com.beerboy.scanner.loader.ResourceLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <T>
 */
public class PackageScanner<T> {

    private ClassLoader classLoader;
    private ResourceLoader<T> resourceLoader;
    private ScannerConfig config;

    /**
     * Creates an instance with the {@link ResourceLoader} and a default {@link ClassLoader}.
     *
     * @param resourceLoader
     */
    public PackageScanner(ResourceLoader<T> resourceLoader) {
        this(getDefaultClassLoader(), resourceLoader, ScannerConfig.withDefaults());
    }

    /**
     * Creates an instance with the {@link ResourceLoader} and a default {@link ClassLoader}.
     *
     * @param resourceLoader
     * @param config
     */
    public PackageScanner(ResourceLoader<T> resourceLoader, ScannerConfig config) {
        this(getDefaultClassLoader(), resourceLoader, config);
    }

    /**
     * Advanced : use this constructor if you need to specify the {@link ClassLoader}.
     *
     * @param classLoader
     * @param resourceLoader
     * @param config
     */
    public PackageScanner(ClassLoader classLoader, ResourceLoader<T> resourceLoader, ScannerConfig config) {
        Optional.ofNullable(classLoader).orElseThrow(() -> new IllegalArgumentException("Class Loader is required"));
        Optional.ofNullable(config).orElseThrow(() -> new IllegalArgumentException("Scanner configuration is required"));
        Optional.ofNullable(config).orElseThrow(() -> new IllegalArgumentException("Scanner configuration is required"));
        this.classLoader = classLoader;
        this.resourceLoader = resourceLoader;
        this.config = config;
    }

    public Set<T> scan(String packageName) throws IOException {
        final Set<T> result = new HashSet<>();
        scan(packageName, result::add);
        return result;
    }

    public void scan(String packageName, Consumer<T> consumer) throws IOException {

        // on windows, the Sun JVM uses uses '/' and not '\' (File.separatorChar) in the classpath
        // thanks to jeremy chone (http://www.bitsandbuzz.com/) for this insight.
        //
        // NOTE : for non-Sun JVMs, i have not idea what the behavior is.  but i'm not going to lose
        // any sleep over it. ;)
        packageName = packageName.replace('.', '/');
        Pattern packageDirMatcher = Pattern.compile("(" + Pattern.quote(packageName) + "(/.*)?)$");

        Enumeration<URL> dirs = classLoader.getResources(packageName);

        while (dirs.hasMoreElements()) {
            String path = URLDecoder.decode(dirs.nextElement().getPath(), "UTF-8");

            if (path.contains(".jar!") || path.contains("zip!")) {
                String jarName = path.substring("file:".length());
                jarName = jarName.substring(0, jarName.indexOf('!'));

                JarFile jarFile = new JarFile(jarName);
                if (config.isScanJars()) {
                    visitJarFile(jarFile, packageName, consumer);
                }
            } else {
                File dir = new File(path);
                Matcher dirMatcher = packageDirMatcher.matcher(path);

                if (dirMatcher.find() && config.isScanDirs()) {
                    visitDirectory(dir, packageDirMatcher, consumer);
                }
            }
        }
    }

    private void visitJarFile(JarFile jarFile, String packageNameForJarPath, Consumer<T> consumer) {

        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            String entryPackage = StringUtils.substringBeforeLast(entry.getName(), "/");
            if (packageNameForJarPath.equals(entryPackage) || (config.isRecursive() && entryPackage.startsWith(packageNameForJarPath))) {

                String packageName = entryPackage.replace('/', '.');
                if (!entry.isDirectory()) {

                    T resource = resourceLoader.load(packageName, jarFile, entry);
                    if (resource != null) {
                        consumer.accept(resource);
                    }
                }
            }
        }
    }


    private void visitDirectory(File dir, Pattern packageDirMatcher, Consumer<T> consumer) {

        for (Object obj : FileUtils.listFiles(dir, null, config.isRecursive())) {
            File file = (File) obj;

            // because the JVM appears to use '/' on all platforms in the classpath entries, this pattern
            // always ends up with '/' and never File.separatorChar.  so, on windows, we'll need to modify
            // our search pattern.
            String absolutePath = file.getParentFile().getAbsolutePath();
            if (File.separatorChar != '/') {
                absolutePath = absolutePath.replace(File.separatorChar, '/');
            }

            Matcher dirMatcher = packageDirMatcher.matcher(absolutePath);
            if (dirMatcher.find()) {
                String packageNameForDir = dirMatcher.group(1).replace('/', '.');

                T resource = resourceLoader.load(packageNameForDir, file.getParentFile(), file.getName());
                if (resource != null) {
                    consumer.accept(resource);
                }
            }
        }
    }

    private static ClassLoader getDefaultClassLoader() {
        return PackageScanner.class.getClassLoader();
    }
}
