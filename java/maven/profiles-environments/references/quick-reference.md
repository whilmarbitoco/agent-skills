# Profiles and Environments — Quick Reference

| Activation | Element |
|---|---|
| OS | `<os><name>linux</name></os>` |
| JDK | `<jdk>21</jdk>` |
| Property | `<property><name>env</name><value>prod</value></property>` |
| File exists | `<file><exists>src/main/resources/prod.xml</exists></file>` |
| Default | `<activeByDefault>true</activeByDefault>` |

| Goal | Command |
|---|---|
| Show active profiles | `mvn help:active-profiles` |
| Activate profile | `mvn -Pprod package` |
| Deactivate default | `mvn -P'!dev' package` |
| Activate by property | `mvn -Denv=prod package` |

| Placeholder | Source |
|---|---|
| `${project.version}` | POM version |
| `${app.env}` | Profile `<properties>` |
| `${env.VAR}` | System environment variable |
