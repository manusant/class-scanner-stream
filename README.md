# class-scanner-stream
It´s a lightweight class scanner that allows us to apply filters and collect results in a stream pipeline.

### Scan all classes under specified package
```java
  List<Class> scanResults = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .collect(Collectors.toList());
```

### Scan all classes under specified package and group them by package
```java
  Map<Package, List<Class>> scanResultByPackages = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .collect(Collectors.groupingBy(Class::getPackage));
```

### Scan all classes annotated with specified @Annotation under specified package
```java
  List<Class> annotatedClasses = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .annotatedWith(Logger.class)
                .collect(Collectors.toList());
```

### Scan all classes that extends from specified Class under specified package
```java
  List<Class> childClasses = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .subClasses(ParentType.class)
                .collect(Collectors.toList());
```

### Scan all classes that implements specified Interface under specified package
```java
  List<Class> implementerClasses = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .implementers(Interface.class)
                .collect(Collectors.toList());
```

### Scan all classes that meet specified filter under specified package
```java
 List<Class> filteredClasses = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .filter(clazz -> {
                    return true; // Filter code goes here
                })
                .collect(Collectors.toList());
```

### Scan all classes that meet all applied filters under specified package
```java
  List<Class> implementerClasses = ClassStream.scan(classLoader, "com.beerboy.scanner")
                .implementers(Interface.class)
                .subClasses(ParentType.class)
                .annotatedWith(Logger.class)
                .collect(Collectors.toList());
```
