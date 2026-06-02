# Multi-Module Projects — Anti-Patterns

## 1. Hard-coding versions in child modules

```xml
<!-- WRONG — child POM specifies version directly, bypasses parent management -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>core-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

```xml
<!-- FIX: omit version — inherit from parent dependencyManagement -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>core-api</artifactId>
</dependency>
```

## 2. Module depends on a grandparent instead of declaring the direct dep

```xml
<!-- WRONG — desktop module transitively relies on ebean-orm from core,
     never declares it, so it breaks when core bumps the version -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>core-api</artifactId>
</dependency>
<!-- accidental transitive dependency on io.ebean:ebean-orm -->
```

```xml
<!-- FIX: every module declares every dependency it directly uses -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>core-api</artifactId>
</dependency>
<dependency>
    <groupId>io.ebean</groupId>
    <artifactId>ebean-orm</artifactId>
</dependency>
```

## 3. Circular module dependencies

```xml
<!-- WRONG: core depends on desktop AND desktop depends on core →
     Maven reactor can't determine build order -->
<!-- core/pom.xml -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>desktop</artifactId>
</dependency>
<!-- desktop/pom.xml -->
<dependency>
    <groupId>com.example</groupId>
    <artifactId>core</artifactId>
</dependency>
```

```java
// FIX: extract shared API module that both depend on
// core-api (pure interfaces, records, enums)
//   ├── core-impl (depends on core-api)
//   └── desktop  (depends on core-api)
// core-impl never references desktop; desktop never references core-impl
```

## 4. Parent POM without `<packaging>pom</groupId>`

```xml
<!-- WRONG — parent has <packaging>jar</packaging> -->
<project>
    <groupId>com.example</groupId>
    <artifactId>my-app-parent</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <modules>
        <module>core</module>
        <module>desktop</module>
    </modules>
</project>
```

```xml
<!-- FIX: parent always uses pom packaging -->
<project>
    <groupId>com.example</groupId>
    <artifactId>my-app-parent</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <modules>
        <module>core</module>
        <module>desktop</module>
    </modules>
</project>
```

## 5. Verification module placed before app in reactor

```xml
<!-- WRONG — integration-tests listed before core, so it builds first
     before its dependencies exist -->
<modules>
    <module>integration-tests</module>
    <module>core</module>
    <module>desktop-app</module>
</modules>
```

```xml
<!-- FIX: leaf modules (apps) first, integration tests last -->
<modules>
    <module>core</module>
    <module>desktop-app</module>
    <module>integration-tests</module>
</modules>
```
