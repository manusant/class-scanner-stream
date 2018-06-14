package com.beerboy.scanner;

/**
 * @author manusant
 */
public class ScannerConfig {

    private boolean recursive;
    private boolean scanJars;
    private boolean scanDirs;

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isScanJars() {
        return scanJars;
    }

    public void setScanJars(boolean scanJars) {
        this.scanJars = scanJars;
    }

    public boolean isScanDirs() {
        return scanDirs;
    }

    public void setScanDirs(boolean scanDirs) {
        this.scanDirs = scanDirs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static ScannerConfig withDefaults() {
        return new Builder()
                .withRecursive(true)
                .withScanDirs(true)
                .withScanJars(true)
                .build();
    }

    public static final class Builder {
        private boolean recursive;
        private boolean scanJars;
        private boolean scanDirs;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withRecursive(boolean recursive) {
            this.recursive = recursive;
            return this;
        }

        public Builder withScanJars(boolean scanJars) {
            this.scanJars = scanJars;
            return this;
        }

        public Builder withScanDirs(boolean scanDirs) {
            this.scanDirs = scanDirs;
            return this;
        }

        public ScannerConfig build() {
            ScannerConfig scannerConfig = new ScannerConfig();
            scannerConfig.setRecursive(recursive);
            scannerConfig.setScanJars(scanJars);
            scannerConfig.setScanDirs(scanDirs);
            return scannerConfig;
        }
    }
}
