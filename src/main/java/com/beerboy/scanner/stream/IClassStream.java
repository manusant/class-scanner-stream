package com.beerboy.scanner.stream;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @param <T>
 * @author manusant
 */
public interface IClassStream<T> {

    IClassStream<T> annotatedWith(Class<? extends Annotation> annotationType);

    IClassStream<T> subClasses(Class<? extends T> baseType);

    IClassStream<T> implementers(Class<? extends T> interfaceType);

    IClassStream<T> filter(Supplier<Predicate<T>> predicateSupplier);

    <R, A> R collect(Collector<? super T, A, R> collector);
}
