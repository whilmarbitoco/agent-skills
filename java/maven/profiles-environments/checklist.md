# Profiles and Environments — Checklist

## Implementation

- [ ] `dev` profile is `activeByDefault`
- [ ] `prod` profile activated via `-Pprod`
- [ ] No secrets in POM — use `${env.VAR}` placeholders
- [ ] Resource `<filtering>true</filtering>` for `${...}` placeholders
- [ ] Per-profile deps use `runtime` scope where possible (drivers, tools)
- [ ] Profile-specific properties don't duplicate — override only what changes

## Review

- [ ] `mvn -Pdev package` uses dev database
- [ ] `mvn -Pprod package` uses prod database via env vars
- [ ] `jar tf target/app.jar | grep application.properties` — placeholders
      are resolved
- [ ] `mvn help:active-profiles` shows correct active profile
