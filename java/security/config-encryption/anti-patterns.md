# Config Encryption — Anti-Patterns

## Problem 1: Plain text secrets in config files

```properties
# WRONG — anyone with file access sees the password
db.password=supersecret123
api.key=sk-abc123
```

```properties
# FIX — store encrypted values
db.password=ENC(AbCdEfGh1234567890)
api.key=ENC(XyZ9876543210)
```

## Problem 2: Hardcoded encryption key

```java
// WRONG — key in source code
String key = "my-secret-key-123";
```

```java
// FIX — key from environment variable
String key = System.getenv("CONFIG_ENCRYPTION_KEY");
if (key == null) throw new RuntimeException("CONFIG_ENCRYPTION_KEY not set");
```

## Problem 3: Using weak encryption

```java
// WRONG — DES is broken
Cipher cipher = Cipher.getInstance("DES");
```

```java
// FIX — use AES-256-GCM
Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
```
