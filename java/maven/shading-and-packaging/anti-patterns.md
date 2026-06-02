# Shading and Packaging — Anti-Patterns

## 1. No ServicesResourceTransformer — SPI files overwritten

```xml
<!-- WRONG — META-INF/services files from deps silently overwrite each other -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.6.0</version>
    <executions><execution>
        <phase>package</phase><goals><goal>shade</goal></goals>
        <configuration>
            <transformers>
                <transformer implementation=
                    "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                    <mainClass>com.example.app.Main</mainClass>
                </transformer>
            </transformers>
        </configuration>
    </execution></executions>
</plugin>
```

```xml
<!-- FIX: add ServicesResourceTransformer to merge SPI files -->
<transformers>
    <transformer implementation=
        "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
        <mainClass>com.example.app.Main</mainClass>
    </transformer>
    <transformer implementation=
        "org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
</transformers>
```

## 2. Forgetting Main-Class in manifest

```xml
<!-- WRONG — no Main-Class, jar is not executable -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.6.0</version>
    <executions><execution>
        <phase>package</phase><goals><goal>shade</goal></goals>
        <!-- no transformer config at all -->
    </execution></executions>
</plugin>
```

```java
// SHADDED JAR: java -jar app-shaded.jar
// Exception: no main manifest attribute, in app-shaded.jar
```

```xml
<!-- FIX: always specify Main-Manifest entry -->
<transformer implementation=
    "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
    <manifestEntries>
        <Main-Class>com.example.app.Main</Main-Class>
    </manifestEntries>
</transformer>
```

## 3. Shading without relocating conflicting common deps

```java
// WRONG — app uses gRPC 1.62 that shades in old protobuf 3.21,
// but app code also depends on protobuf 3.25 → ClassNotFoundException
// or silent wrong-version usage
```

```xml
<!-- FIX: relocate known-conflicting deps to shaded namespace -->
<relocations>
    <relocation>
        <pattern>com.google.protobuf</pattern>
        <shadedPattern>shaded.com.google.protobuf</shadedPattern>
    </relocation>
    <relocation>
        <pattern>io.grpc</pattern>
        <shadedPattern>shaded.io.grpc</shadedPattern>
    </relocation>
</relocations>
```

## 4. Shading provided-scope dependencies

```xml
<!-- WRONE — servlet-api marked provided but shade bundles it anyway -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <scope>provided</scope>
</dependency>
```

```xml
<!-- FIX: shade plugin ignores scope; exclude provided deps explicitly -->
<configuration>
    <artifactSet>
        <excludes>
            <exclude>jakarta.servlet:jakarta.servlet-api</exclude>
        </excludes>
    </artifactSet>
</configuration>
```
