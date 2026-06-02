# JavaFX Maven Plugin — Quick Reference

| Goal | Command |
|---|---|
| Run dev | `mvn javafx:run` |
| Build jlink image | `mvn javafx:jlink` |
| Image location | `target/image/bin/<launcher>` |
| Platforms | `win`, `linux`, `mac`, `mac-aarch64` |

| Module | Artifact |
|---|---|
| Controls + FXML | `javafx-controls` |
| FXML only | `javafx-fxml` |
| WebView | `javafx-web` |
| Media | `javafx-media` |
| Graphics (transitive) | `javafx-graphics` |

| Pattern | Config |
|---|---|
| Non-modular mainClass | `<mainClass>com.example.Main</mainClass>` |
| Modular mainClass | `<mainClass>com.example.app/com.example.Main</mainClass>` |
| Classifier (platform) | `<classifier>linux</classifier>` |
