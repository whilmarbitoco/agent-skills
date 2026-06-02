# Ebean Entities — Checklist

## Implementation

- [ ] Entity is a plain class (not a record) with a no-arg constructor
- [ ] `@Version` field for optimistic locking on concurrent-write entities
- [ ] `equals()`/`hashCode()` based on `@Id` or business key
- [ ] `@SoftDelete` on entities requiring data retention
- [ ] `@DbEnumType(ENUM)` for enum fields (not ordinals)
- [ ] FetchType.LAZY on `@OneToMany` and `@ManyToMany`
- [ ] `@WhenCreated` / `@WhenModified` for audit timestamps

## Review

- [ ] No records used as entities
- [ ] No EAGER fetching on collection relationships
- [ ] Enums stored as strings, not ordinals
- [ ] `@Version` present on writable entities
- [ ] `@JoinColumn` naming is explicit on relationships
