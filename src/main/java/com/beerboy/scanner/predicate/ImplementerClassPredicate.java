package com.beerboy.scanner.predicate;

import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author manusant
 */
@SuppressWarnings("unchecked")
public class ImplementerClassPredicate implements Predicate<Class> {

    private Class baseType;

    public ImplementerClassPredicate(Class baseType) {
        Optional.ofNullable(baseType).orElseThrow(() -> new IllegalArgumentException("Base type is required"));
        this.baseType = baseType;
    }

    @Override
    public boolean test(Class clazz) {
        return clazz.isAssignableFrom(baseType) && (!(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())));
    }
}
