# Secrets Management — Anti-Patterns

## Problem 1: Secrets in version control

```java
// WRONG — committed to git
String apiKey = "sk-proj-abc123def456";
```

```java
// FIX — load from environment
String apiKey = System.getenv("OPENAI_API_KEY");
if (apiKey == null) throw new RuntimeException("OPENAI_API_KEY not set");
```

## Problem 2: Secrets in log output

```java
// WRONG — logs the secret
logger.info("Connecting with API key: {}", apiKey);
```

```java
// FIX — mask secrets in logs
logger.info("Connecting with API key: {}****", apiKey.substring(0, 4));
```

## Problem 3: No secret rotation

```java
// WRONG — same key forever
// No rotation strategy, no expiration
```

```java
// FIX — support rotation with versioned keys
// Store: key_v1, key_v2 with active flag
// Rotate: generate new key, update active, keep old for grace period
```
