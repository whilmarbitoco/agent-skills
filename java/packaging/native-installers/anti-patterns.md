# Native Installers Anti-Patterns

Building Windows MSI on Linux — doesn't work. Fix: build on target OS or use GitHub Actions matrix.

No WiX toolchain on Windows — MSI fails to build. Fix: install WiX 3.0+ from wixtoolset.org.

Missing dmg background image — DMG looks ugly. Fix: create a DMG background with app icon and Applications shortcut.

Hardcoding version in jpackage config — have to edit for every release. Fix: read from maven/gradle project.version.