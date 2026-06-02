# Reproducible Builds — Anti-Patterns

## 1. Missing outputTimestamp — timestamps vary per build

```xml
<!-- WRONG — each build produces different jar bytes because
     MANIFEST.MF and entry timestamps change -->
<properties>
    <maven.compiler.release>21</maven.compiler.release>
    <!-- no outputTimestamp -->
</properties>
```

```xml
<!-- FIX: set timestamp from SCM tag (use git log to get actual release commit time) -->
<properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.outputTimestamp>2025-06-01T00:00:00Z</project.build.outputTimestamp>
</properties>
```

## 2. Unpinned plugin versions — different Maven installs get different plugins

```xml
<!-- WRONG — plugin uses whatever the local Maven cache resolves -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <!-- no version — uses default from Maven super POM -->
</plugin>
```

```xml
<!-- FIX: pin every plugin version in pluginManagement -->
<pluginManagement>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.4.2</version>
        </plugin>
    </plugins>
</pluginManagement>
```

## 3. `.mvn/maven.config` not committed — CI uses different flags

```bash
# WRONG — CI build uses developer's local .mvn/maven.config
# that includes -T 4 (parallel) but CI machine has 1 core → different output
# Missing from source control:
# .mvn/maven.config (not in git)
```

```properties
# FIX: commit .mvn/maven.config with deterministic flags
--batch-mode
--strict-checksums
--fail-at-end
```

## 4. Using LATEST or RELEASE for dependencies

```xml
<!-- WRONG — LATEST resolves differently over time -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>RELEASE</version>
</dependency>
```

```xml
<!-- FIX: always pin to exact version -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>33.1.0-jre</version>
</dependency>
```

## 5. System-dependent file ordering in zip entries

```bash
# WRONG — `jar cf` orders entries by filesystem order (non-deterministic on CI)
# Two builds of the same code produce different SHA-256 hashes
```

```xml
<!-- FIX: maven-jar-plugin 3.x uses sorted entries by default when
     outputTimestamp is set. For zip assemblies, use: -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.7.1</version>
    <configuration>
        <reproducible>true</reproducible> <!-- sorts entries -->
    </configuration>
</plugin>
```
