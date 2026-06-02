package com.pos.packaging;

/**
 * jpackage configuration for creating native installers.
 * Bundles JRE + app into .msi/.dmg/.deb.
 *
 * Usage:
 *   jpackage \
 *     --input target/libs \
 *     --main-jar pos-app.jar \
 *     --main-class com.pos.App \
 *     --name "POS System" \
 *     --app-version 1.0.0 \
 *     --dest output/ \
 *     --type msi \
 *     --win-dir-chooser \
 *     --win-menu \
 *     --win-shortcut
 */
public class JPackageConfig {

    // Maven plugin configuration (pom.xml):
    /*
    <plugin>
        <groupId>org.panteleyev</groupId>
        <artifactId>jpackage-maven-plugin</artifactId>
        <version>1.6.0</version>
        <configuration>
            <name>POS System</name>
            <appVersion>1.0.0</appVersion>
            <vendor>MyCompany</vendor>
            <destination>target/installer</destination>
            <module>com.pos/com.pos.App</module>
            <runtimeImage>target/image</runtimeImage>
            <javaOptions>
                <option>-Xmx512m</option>
                <option>-XX:+UseZGC</option>
            </javaOptions>
            <winDirChooser>true</winDirChooser>
            <winMenu>true</winMenu>
            <winShortcut>true</winShortcut>
        </configuration>
    </plugin>
    */

    // Key flags reference:
    // --type: msi | exe | dmg | deb | rpm | pkg
    // --win-dir-chooser: let user pick install dir
    // --win-menu: add to Start Menu
    // --win-shortcut: create desktop shortcut
    // --linux-shortcut: add to application menu
    // --icon: path to .ico/.icns/.png
    // --app-version: semantic version string
    // --vendor: company name
    // --copyright: copyright string
    // --description: app description
}
