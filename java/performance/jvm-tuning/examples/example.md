# JVM flags for Simple POS
#
# In pom.xml (javafx-maven-plugin) or .jvmopts:
#
# -Xms256m           # Initial heap (50% of max)
# -Xmx512m           # Max heap — plenty for desktop
# -XX:+UseG1GC       # Garbage collector (Java 21 default)
# -Xshare:on         # Class Data Sharing — faster startup
# -XX:+EnableDynamicAgentLoading  # JavaFX needs this
# -XX:MaxGCPauseMillis=200       # Target GC pause
#
# For production:
# -XX:+HeapDumpOnOutOfMemoryError  # Debug OOM issues