# Ebean Setup — Quick Reference

| Config | How |
|---|---|
| Dependency | `io.ebean:ebean-starter:15.x` |
| Programmatic config | `new ServerConfig()` → `DatabaseFactory.create(config)` |
| Entity scanning | `config.addPackage("com.example.domain")` |
| DDL generate | `config.setDdlGenerate(true)` |
| DDL run | `config.setDdlRun(false)` in prod |
| Shutdown hook | `Runtime.getRuntime().addShutdownHook(new Thread(db::shutdown))` |

| DDL Mode | Effect |
|---|---|
| `none` | No DDL operations |
| `create` | Creates tables on startup |
| `drop` | Drops + recreates tables |
| `migration` | Runs pending migration scripts |

| When | Use |
|---|---|
| New project | `ebean-starter` + `ServerConfig` |
| Dev environment | `ddl.generate=true` + `ddl.run=true` |
| Production | `ddl.generate=false` + `ddl.run=false` + migration tool |
