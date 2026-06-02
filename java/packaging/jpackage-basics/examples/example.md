/**
 * jpackage configuration for Simple POS.
 * Run after producing the application image via jlink.
 */
// Command-line usage:
//
// jpackage \
//   --type deb \
//   --name "SimplePOS" \
//   --app-version "1.0.0" \
//   --input target/image \
//   --dest target/installer \
//   --main-jar simple-pos.jar \
//   --main-class com.simplepos.app.MainApp \
//   --icon src/main/resources/icons/app-icon.png \
//   --linux-menu-group "Office" \
//   --description "Simple POS System" \
//   --vendor "SimplePOS Inc."
//
// Maven: configure jpackage-maven-plugin with same params