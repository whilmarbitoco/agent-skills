# Ebean Setup — Anti-Patterns

## 1. Using XML config instead of programmatic ServerConfig

```java
// WRONG — XML config is harder to debug and version-control
// ebean.xml:
// <ebean>
//   <datasource>...</datasource>
// </ebean>
```

```java
// FIX: programmatic ServerConfig with constructor injection
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.ServerConfig;
import javax.sql.DataSource;

public class EbeanConfig {

    private final Database database;

    public EbeanConfig(DataSource dataSource) {
        var config = new ServerConfig();
        config.setDataSource(dataSource);
        config.setDefaultServer(true);
        config.setRun(false); // container manages lifecycle

        // Explicitly add entity packages
        config.addPackage("com.example.domain");

        // DDL: generate scripts, don't auto-run in prod
        config.getDdlGenerate();
        config.setDdlRun(false);
        config.setDdlSeedSql("seed.sql");

        this.database = DatabaseFactory.create(config);
    }

    public Database database() { return database; }
}
```

## 2. Scanning entire classpath instead of specific packages

```java
// WRONG — scans everything, slow startup, may pick up test entities
ServerConfig config = new ServerConfig();
config.setPackages(List.of("com"));
```

```java
// FIX: narrow package scan
config.setPackages(List.of("com.example.domain", "com.example.domain.order"));
// Or use ebean.mf in each entity package:
// com/example/domain/ebean.mf  contents: entities: com.example.domain
```

## 3. Auto-running DDL in production

```java
// WRONG — drops/creates tables on startup in production
ServerConfig config = new ServerConfig();
config.setDdlRun(true);
config.setDdlGenerate(true);
// Accidentally drops a table? Good luck.
```

```java
// FIX: generate only, review, then apply via migration tool
config.setDdlRun(false);
config.setDdlGenerate(true);
config.getDdlMigration().setGenerateOnly(true);
// Review generated migration-1.0.sql, then run via Flyway or ebean-ddl-runner
```

## 4. Mixing JPA and Ebean annotations inconsistently

```java
// WRONG — mixing javax.persistence and io.ebean annotations
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @ebean.annotation.Identity(IdType.IDENTITY) // mixed!
    private Long id;
}
```

```java
// FIX: pick one annotation set — io.ebean for Ebean, jakarta.persistence for JPA
import io.ebean.annotation.Identity;
import io.ebean.annotation.IdentityType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Identity(type = IdentityType.IDENTITY)
    private Long id;
}
```

## 5. Not closing the database on shutdown

```java
// WRONG — no shutdown hook, connections leak
Database db = DatabaseFactory.create(config);
// Application exits → connection pool not closed
```

```java
// FIX: register shutdown hook
Database db = DatabaseFactory.create(config);
Runtime.getRuntime().addShutdownHook(new Thread(db::shutdown));
```
