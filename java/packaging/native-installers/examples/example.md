# GitHub Actions matrix for native installers
#
# jobs:
#   build:
#     strategy:
#       matrix:
#         os: [ubuntu-latest, windows-latest, macos-latest]
#     steps:
#       - uses: actions/checkout@v4
#       - uses: actions/setup-java@v4
#         with: { java-version: '21' }
#       - run: ./mvnw package -DskipTests
#       - run: |
#         jpackage --type ${{ matrix.type }} \
#           --input target/app-image \
#           --dest target/installer \
#           --main-class com.simplepos.Main \
#           --icon resources/icon.${{ matrix.ext }}