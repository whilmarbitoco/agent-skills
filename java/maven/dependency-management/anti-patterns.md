# Dependency Management — Anti-Patterns

## 1. Specifying versions in child POMs

```xml
<!-- WRONG — child hard-codes a version, bypassing parent management -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
```

```xml
<!-- FIX: omit version — parent dependencyManagement controls it -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>
```

## 2. Not importing a BOM — versions drift across a library family

```xml
<!-- WRONG — each ebean module has its own version -->
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean-core</artifactId>
    <version>15.1.0</version>
</dependency>
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean-querybean</artifactId>
    <version>15.0.0</version> <!-- mismatch! -->
</dependency>
```

```xml
<!-- FIX: import ebean-bom first, then omit versions -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.ebean</groupId>
            <artifactId>ebean-bom</artifactId>
            <version>15.1.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 3. Using compile scope for test-only dependencies

```xml
<!-- WRONG — junit is compile scope, ships in production jar -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <!-- missing scope defaults to compile -->
</dependency>
```

```xml
<!-- FIX: test scope for test-only deps -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

## 4. Not excluding conflicting transitive dependencies

```xml
<!-- WRONG — library-a brings in log4j, but we use slf4j + logback -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>library-a</artifactId>
    <version>2.0</version>
</dependency>
<!-- Now classpath has both log4j and logback → unpredictable logging -->
```

```xml
<!-- FIX: exclude the unwanted transitive dep -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>library-a</artifactId>
    <version>2.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

## 5. Guessing which version wins in a conflict

```java
// WRONG — assuming slf4j 2.0.9 is on the classpath because
// that's what you declared, but a transitive dep pulled in 1.7.36
// and Maven's "nearest definition" rule picked the older one
import org.slf4j.LoggerFactory;
// May get NoSuchMethodError at runtime if 2.0 API is called
```

```bash
# FIX: always inspect the resolved tree
mvn dependency:tree -Dincludes=org.slf4j
# Output shows which version actually won and why
```
