# JavaFX Maven Plugin — Anti-Patterns

## 1. Not importing JavaFX BOM — version mismatch

```xml
<!-- WRONG — manual versions, prone to mismatch -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21.0.2</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21.0.1</version> <!-- off by one! -->
</dependency>
```

```xml
<!-- FIX: import BOM, omit individual versions -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-bom</artifactId>
            <version>21.0.2</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
    </dependency>
</dependencies>
```

## 2. Using maven-jar-plugin instead of javafx-maven-plugin

```xml
<!-- WRONG — standard jar plugin doesn't extract native libs,
     app crashes with "no javafx.graphics in java.library.path" -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <configuration>
        <archive>
            <manifest><mainClass>com.example.Main</mainClass></manifest>
        </archive>
    </configuration>
</plugin>
```

```xml
<!-- FIX: use the JavaFX plugin -->
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <version>0.0.8</version>
    <configuration>
        <mainClass>com.example.app/com.example.app.Main</mainClass>
    </configuration>
</plugin>
```

## 3. Missing module-info for Java 21 + JavaFX

```java
// WRONG — unnamed module can't access JavaFX named modules →
// "package javafx.scene is not visible"
```

```java
// FIX: module-info.java
// module com.example.app {
//     requires javafx.controls;
//     requires javafx.fxml;
//     requires org.slf4j;
//     exports com.example.app;
// }
```

## 4. Not specifying mainClass with module prefix

```xml
<!-- WRONG — missing module prefix in modular project -->
<configuration>
    <mainClass>com.example.app.Main</mainClass>
</configuration>
```

```xml
<!-- FIX: use module/mainClass format -->
<configuration>
    <mainClass>com.example.app/com.example.app.Main</mainClass>
</configuration>
```

## 5. Platform-specific dependency without classifier on CI

```xml
<!-- WRONG — CI builds on Linux but deps are macOS natives -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <version>21.0.2</version>
    <!-- No platform jar → works on build platform only -->
</dependency>
```

```xml
<!-- FIX: let javafx-maven-plugin resolve platform jars automatically,
     or use profiles per platform with <classifier> -->
<profiles>
    <profile><id>linux</id><dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-graphics</artifactId>
            <version>21.0.2</version>
            <classifier>linux</classifier>
        </dependency>
    </dependencies></profile>
</profiles>
```
