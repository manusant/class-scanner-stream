package com.beerboy.scanner.predicate;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author manusant
 */
@SuppressWarnings("unchecked")
public class SubClassPredicate implements Predicate<Class> {

    private Class baseType;

    public SubClassPredicate(Class baseType) {
        Optional.ofNullable(baseType).orElseThrow(() -> new IllegalArgumentException("Base type is required"));
        this.baseType = baseType;
    }

    @Override
    public boolean test(Class clazz) {
        return clazz.isAssignableFrom(baseType);
    }
}
