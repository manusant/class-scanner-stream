# class-scanner-stream  <img src="https://travis-ci.org/manusant/class-scanner-stream.svg?branch=master" alt="build:">
It´s a lightweight class scanner that allows us to apply filters and collect results in a stream pipeline.

### Scan all classes under specified package
```java
  List<Class> scanResults = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .collect(Collectors.toList());
```

### Scan all classes under specified package and group them by package
```java
  Map<Package, List<Class>> scanResultByPackages = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .collect(Collectors.groupingBy(Class::getPackage));
```

### Specify scan configurations (By default all flags are true)
```java
 ScannerConfig config = ScannerConfig.newBuilder()
                .withRecursive(true)
                .withScanDirs(true)
                .withScanJars(true)
                .build();

 List<Class> result = ClassScanner.scan(classLoader, "com.beerboy.scanner", config)
                .collect(Collectors.toList());
```             
                
### Scan all classes annotated with specified @Annotation under specified package
```java
  List<Class> annotatedClasses = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .stream()
                .annotatedWith(Logger.class)
                .collect(Collectors.toList());
```

### Scan all classes that extends from specified Class under specified package
```java
  List<Class> childClasses = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .stream()
                .subClasses(ParentType.class)
                .collect(Collectors.toList());
```

### Scan all classes that implements specified Interface under specified package
```java
  List<Class> implementerClasses = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .stream()
                .implementers(Interface.class)
                .collect(Collectors.toList());
```

### Scan all classes that meet specified filter under specified package
```java
 List<Class> filteredClasses = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .stream()
                .filter(clazz -> {
                    return true; // Filter code goes here
                })
                .collect(Collectors.toList());
```

### Scan all classes that meet all applied filters under specified package
```java
  List<Class> implementerClasses = ClassScanner.scan(classLoader, "com.beerboy.scanner")
                .stream()
                .implementers(Interface.class)
                .subClasses(ParentType.class)
                .annotatedWith(Logger.class)
                .collect(Collectors.toList());
```

## Add to your project
### Gradle
Add this entry to your *build.gradle* file
```groovy
 repositories {
    maven {
        url "https://packagecloud.io/manusant/beerRepo/maven2"
    }
}
```
And add the dependency
```groovy
  compile 'com.beerboy.scanner:class-scanner-stream:1.0.0.1'
```
### Maven
Add this to *dependencyManagement* section of your *pom.xml* 
```xml
<repositories>
  <repository>
    <id>manusant-beerRepo</id>
    <url>https://packagecloud.io/manusant/beerRepo/maven2</url>
  </repository>
</repositories>
```
And add the dependency
```xml
  <dependency>
    <groupId>com.beerboy.scanner</groupId>
    <artifactId>class-scanner-stream</artifactId>
    <version>1.0.0.1</version>
  </dependency>
```
