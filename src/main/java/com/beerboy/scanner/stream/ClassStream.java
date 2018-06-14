package com.beerboy.scanner.stream;

import com.beerboy.scanner.PackageScanner;
import com.beerboy.scanner.loader.ClassResourceLoader;
import com.beerboy.scanner.predicate.AnnotatedClassPredicate;
import com.beerboy.scanner.predicate.ImplementerClassPredicate;
import com.beerboy.scanner.predicate.SubClassPredicate;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author manusant
 */
public class ClassStream implements IClassStream<Class> {

    private Stream<Class<?>> stream;

    private ClassStream(Collection<Class<?>> classes) {
        this.stream = classes.stream();
    }

    public static ClassStream scan(ClassLoader classLoader, final String packageName) throws IOException {
        Optional.ofNullable(packageName).orElseThrow(() -> new IllegalArgumentException("Package name is required"));

        ClassResourceLoader resourceLoader = new ClassResourceLoader(classLoader, false);
        PackageScanner<Class<?>> scanner = new PackageScanner<>(resourceLoader);
        Set<Class<?>> scanResult = scanner.scan(packageName);
        return new ClassStream(scanResult);
    }

    @Override
    public IClassStream<Class> annotatedWith(Class<? extends Annotation> annotationType) {
        stream = stream.filter(new AnnotatedClassPredicate(annotationType));
        return this;
    }

    @Override
    public IClassStream<Class> subClasses(Class<? extends Class> baseType) {
        stream = stream.filter(new SubClassPredicate(baseType));
        return this;
    }

    @Override
    public IClassStream<Class> implementers(Class<? extends Class> interfaceType) {
        stream = stream.filter(new ImplementerClassPredicate(interfaceType));
        return this;
    }

    @Override
    public IClassStream<Class> filter(Supplier<Predicate<Class>> predicateSupplier) {
        stream = stream.filter(predicateSupplier.get());
        return this;
    }

    @Override
    public <R, A> R collect(Collector<? super Class, A, R> collector) {
        return this.stream.collect(collector);
    }
}
