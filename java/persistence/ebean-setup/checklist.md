# Ebean Setup — Checklist

## Implementation

- [ ] Add `ebean-starter` dependency in `pom.xml`
- [ ] Create `ServerConfig` programmatically — no XML
- [ ] Constructor-inject `DataSource` into config class
- [ ] Set explicit entity packages via `addPackage()`
- [ ] Configure DDL mode: `generate=true`, `run=false` in prod
- [ ] Set `defaultServer(true)` on primary config
- [ ] Register shutdown hook for `database.shutdown()`

## Review

- [ ] No wildcard package scanning (e.g., `com` instead of `com.example.domain`)
- [ ] No XML configuration files
- [ ] DDL `run=false` in production
- [ ] Explicit entity packages listed
