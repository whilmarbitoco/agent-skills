#!/usr/bin/env bash
# detect-pinning.sh — JVM flags for virtual thread pinning detection in dev.
# Usage: ./scripts/detect-pinning.sh (add to MAVEN_OPTS or java -D)

echo "=== Virtual Thread Pinning Detection Flags ==="
echo ""
echo "Development (warnings in stderr):"
echo '  -Djdk.tracePinnedThreads=short'
echo ""
echo "Production (full stack trace):"
echo '  -Djdk.tracePinnedThreads=full'
echo ""
echo "Example with Maven:"
echo '  export MAVEN_OPTS="-Djdk.tracePinnedThreads=short"'
echo '  ./mvnw javafx:run'
echo ""
echo "Example with java directly:"
echo '  java -Djdk.tracePinnedThreads=short -jar target/app.jar'
