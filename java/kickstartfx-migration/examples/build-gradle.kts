# build.gradle — KickStartFX project

plugins {
    id 'application'
}

repositories {
    mavenCentral()
}

// KickStartFX applies these via script plugins
apply from: "$rootDir/gradle/gradle_scripts/java.gradle"
apply from: "$rootDir/gradle/gradle_scripts/javafx.gradle"

dependencies {
    // AtlantAFX themes
    implementation 'io.github.mkpaz:atlantafx-base:2.1.0'

    // Reactive bindings (cleaner than raw Property API)
    implementation 'org.int4.fx:fx-values:0.5'
    implementation 'org.int4.fx:fx-builders:0.5'

    // Material icons — no image files needed
    implementation 'org.kordamp.ikonli:ikonli-material2-pack:12.4.0'
    implementation 'org.kordamp.ikonli:ikonli-materialdesign2-pack:12.4.0'
    implementation 'org.kordamp.ikonli:ikonli-javafx:12.4.0'

    // Logging — JDK Platform Logging (no logback.xml)
    implementation 'org.slf4j:slf4j-api:2.0.17'
    implementation 'org.slf4j:slf4j-jdk-platform-logging:2.0.17'
}

application {
    mainModule = 'com.simplepos.app'
    mainClass = 'com.simplepos.app.Main'
}

// Binary CSS compilation at build time
processResources {
    doLast {
        def cssFiles = fileTree(dir: "$sourceSets.main.output.resourcesDir")
        cssFiles.include "**/*.css"
        cssFiles.each { css ->
            providers.javaexec {
                mainClass = "com.sun.javafx.css.parser.Css2Bin"
                args css
                ignoreExitValue = true
            }.result.get()
            delete css
        }
    }
}
