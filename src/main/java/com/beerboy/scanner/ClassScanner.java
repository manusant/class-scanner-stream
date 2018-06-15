package com.beerboy.scanner;


import com.beerboy.scanner.loader.ClassResourceLoader;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;


public class ClassScanner {

    private ClassResourceLoader resourceLoader;
    private PackageScanner<Class<?>> resourceScanner;

    private ClassScanner(final ClassLoader classLoader, final ScannerConfig config) {
        this.resourceLoader = new ClassResourceLoader(classLoader, false);
        this.resourceScanner = new PackageScanner<>(resourceLoader, config);
    }

    public ClassResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public PackageScanner<Class<?>> getResourceScanner() {
        return resourceScanner;
    }

    public static ScanResult scan(ClassLoader classLoader, final String packageName, final ScannerConfig config) throws IOException {
        Optional.ofNullable(packageName).orElseThrow(() -> new IllegalArgumentException("Package name is required"));
        Optional.ofNullable(classLoader).orElseThrow(() -> new IllegalArgumentException("ClassLoader is required"));

        PackageScanner<Class<?>> resourceScanner = new ClassScanner(classLoader, config).getResourceScanner();
        Set<Class<?>> result = resourceScanner.scan(packageName);
        return ScanResult.wrap(result);
    }

    public static ScanResult scan(ClassLoader classLoader, final String packageName) throws IOException {
        return scan(classLoader, packageName, ScannerConfig.withDefaults());
    }

    public static ScanResult scan(final String packageName) throws IOException {
        return scan(ClassLoader.getSystemClassLoader(), packageName, ScannerConfig.withDefaults());
    }
}
