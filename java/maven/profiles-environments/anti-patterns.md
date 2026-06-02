# Profiles and Environments — Anti-Patterns

## 1. Hard-coding environment values in profiles

```xml
<!-- WRONG — database URL and password baked into POM -->
<profile>
    <id>prod</id>
    <properties>
        <db.url>jdbc:postgresql://prod-db.example.com:5432/myapp</db.url>
        <db.password>s3cret!</db.password>
    </properties>
</profile>
```

```xml
<!-- FIX: use placeholders, resolve from env vars at build or runtime -->
<profile>
    <id>prod</id>
    <properties>
        <db.url>${env.DB_URL}</db.url>
        <db.username>${env.DB_USER}</db.username>
        <db.password>${env.DB_PASS}</db.password>
    </properties>
</profile>
<!-- Build with: mvn -Pprod -Ddb.url=$DB_URL package -->
```

## 2. Multiple activeByDefault profiles — unpredictable behavior

```xml
<!-- WRONG — two profiles activeByDefault, Maven picks both unpredictably -->
<profiles>
    <profile><id>mac</id><activation><activeByDefault>true</activeByDefault></activation>...</profile>
    <profile><id>dev</id><activation><activeByDefault>true</activeByDefault></activation>...</profile>
</profiles>
```

```xml
<!-- FIX: only one activeByDefault; use property or OS activation -->
<profiles>
    <profile>
        <id>dev</id>
        <activation><activeByDefault>true</activeByDefault></activation>
        <properties><env>dev</env></properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties><env>prod</env></properties>
    </profile>
</profiles>
```

## 3. Forgetting to enable resource filtering

```xml
<!-- WRONG — ${app.version} never resolved in application.properties -->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <!-- filtering not enabled -->
        </resource>
    </resources>
</build>
```

```properties
# application.properties
# Without filtering, this stays literally "${app.version}"
app.version=${app.version}
```

```xml
<!-- FIX: enable filtering on resources with placeholders -->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```

## 4. Profile-specific dependencies polluting the compile scope

```xml
<!-- WRONG — postgresql driver always present even in prod (uses Oracle) -->
<profile><id>dev</id><dependencies>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.1</version>
    </dependency>
</dependencies></profile>
```

```xml
<!-- FIX: use runtime scope for drivers (loaded via reflection) -->
<profile><id>dev</id><dependencies>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.1</version>
        <scope>runtime</scope>
    </dependency>
</dependencies></profile>
```
