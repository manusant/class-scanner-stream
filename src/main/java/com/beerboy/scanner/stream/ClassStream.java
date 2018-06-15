package com.beerboy.scanner.stream;

import com.beerboy.scanner.predicate.AnnotatedClassPredicate;
import com.beerboy.scanner.predicate.ImplementerClassPredicate;
import com.beerboy.scanner.predicate.SubClassPredicate;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author manusant
 */
public class ClassStream implements IClassStream<Class<?>> {

    private Stream<Class<?>> stream;

    public ClassStream(Collection<Class<?>> classes) {
        this.stream = classes.stream();
    }

    @Override
    public IClassStream<Class<?>> annotatedWith(Class<? extends Annotation> annotationType) {
        stream = stream.filter(new AnnotatedClassPredicate(annotationType));
        return this;
    }

    @Override
    public IClassStream<Class<?>> subClasses(Class baseType) {
        stream = stream.filter(new SubClassPredicate(baseType));
        return this;
    }

    @Override
    public IClassStream<Class<?>> implementers(Class interfaceType) {
        stream = stream.filter(new ImplementerClassPredicate(interfaceType));
        return this;
    }

    @Override
    public IClassStream<Class<?>> filter(Predicate<Class<?>> predicateSupplier) {
        stream = stream.filter(predicateSupplier);
        return this;
    }

    @Override
    public <R, A> R collect(Collector<? super Class<?>, A, R> collector) {
        return this.stream.collect(collector);
    }

    @Override
    public long count() {
        return this.stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super Class<?>> predicate) {
        return this.stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super Class<?>> predicate) {
        return this.stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super Class<?>> predicate) {
        return this.stream.noneMatch(predicate);
    }

    @Override
    public Optional<Class<?>> findFirst() {
        return this.stream.findFirst();
    }

    @Override
    public Optional<Class<?>> findAny() {
        return this.stream.findAny();
    }
}
