package com.beerboy.scanner.stream;

import com.beerboy.scanner.predicate.AnnotatedClassPredicate;
import com.beerboy.scanner.predicate.ImplementerClassPredicate;
import com.beerboy.scanner.predicate.SubClassPredicate;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author manusant
 */
public class ClassStream implements IClassStream<Class> {

    private Stream<Class<?>> stream;

    public ClassStream(Collection<Class<?>> classes) {
        this.stream = classes.stream();
    }

    @Override
    public IClassStream<Class> annotatedWith(Class<? extends Annotation> annotationType) {
        stream = stream.filter(new AnnotatedClassPredicate(annotationType));
        return this;
    }

    @Override
    public IClassStream<Class> subClasses(Class baseType) {
        stream = stream.filter(new SubClassPredicate(baseType));
        return this;
    }

    @Override
    public IClassStream<Class> implementers(Class interfaceType) {
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
