package com.pos.packaging;

/**
 * Native installer strategies for POS desktop apps.
 * Covers jpackage, jlink custom runtime, and platform-specific packaging.
 */
public class NativeInstallerGuide {

    // Strategy 1: jpackage (recommended)
    // Bundles custom JRE + app into platform installer
    // Pros: self-contained, no Java pre-install needed
    // Cons: large bundle size (~80-120MB)

    // Strategy 2: jlink custom runtime
    // Create minimal JRE with only needed modules
    // Pros: smaller bundle
    // Cons: manual packaging, no native installer

    // jlink example:
    // jlink --module-path $JAVA_HOME/jmods:target/modules
    //       --add-modules com.pos
    //       --output target/jre
    //       --strip-debug
    //       --no-man-pages
    //       --no-header-files
    //       --compress=2

    // Platform-specific notes:
    // Windows: .msi preferred, .exe for portable
    // macOS: .dmg for distribution, .pkg for enterprise
    // Linux: .deb for Debian/Ubuntu, .rpm for RHEL/Fedora

    // Code signing (required for macOS/Windows):
    // --mac-sign
    // --mac-signing-key-user-name "Developer ID"
    // --win-sign
    // --win-signing-key-file cert.pfx
}
