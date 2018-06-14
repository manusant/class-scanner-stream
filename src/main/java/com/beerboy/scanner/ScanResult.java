package com.beerboy.scanner;

import com.beerboy.scanner.stream.ClassStream;

import java.util.Set;
import java.util.stream.Collector;

public class ScanResult {

    private Set<Class<?>> scanResult;

    private ScanResult(final Set<Class<?>> scanResult) {
        this.scanResult = scanResult;
    }

    public static ScanResult wrap(final Set<Class<?>> scanResult) {
        return new ScanResult(scanResult);
    }

    public ClassStream stream() {
        return new ClassStream(scanResult);
    }

    public <R, A> R collect(Collector<? super Class, A, R> collector) {
        return scanResult.stream().collect(collector);
    }
}