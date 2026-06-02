# Jpackage Basics Anti-Patterns

Running jpackage without module mode — produces broken installers. Fix: always set --module-path.

Using jlink for distribution — jpackage is preferred. jlink requires manual module assembly.

Missing icon in jpackage config — app uses default JDK icon. Fix: set --icon with .ico/.icns/.png.

Including JavaFX libraries as regular dependencies instead of module path. Fix: add to --module-path, not --classpath.

Not testing installer on clean OS — assumptions about existing Java/runtime corrupt the build. Fix: test on fresh VM.