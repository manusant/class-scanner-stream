package com.beerboy.scanner.predicate;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author manusant
 */
public class AnnotatedClassPredicate implements Predicate<Class> {

    private Class<? extends Annotation> annotationType;

    public AnnotatedClassPredicate(Class<? extends Annotation> annotationType) {
        Optional.ofNullable(annotationType).orElseThrow(() -> new IllegalArgumentException("Annotation type is required"));
        this.annotationType = annotationType;
    }

    @Override
    public boolean test(Class clazz) {
        return clazz.getAnnotation(annotationType) != null;
    }
}
