package com.beerboy.scanner.predicate;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author manusant
 */
public class ClassNamePatternPredicate implements Predicate<Class<?>> {

    private Pattern pattern;
    private boolean completeMatch;

    public ClassNamePatternPredicate(String regex) {
        this(Pattern.compile(regex));
    }

    public ClassNamePatternPredicate(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean test(Class<?> clazz) {
        Matcher matcher = pattern.matcher(clazz.getSimpleName());
        return completeMatch ? matcher.matches() : matcher.find();
    }
}
