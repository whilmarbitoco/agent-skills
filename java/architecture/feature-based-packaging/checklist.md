# Feature-Based Packaging — Checklist

## Implementation
- [ ] Each business feature has its own top-level package
- [ ] Feature package contains Controller, Service, Repository, Model, DTO
- [ ] Public API surface is minimal — internals are package-private
- [ ] Cross-feature communication via shared events or interfaces, not direct imports
- [ ] Shared/common package is small and well-bounded

## Review
- [ ] No circular dependencies between feature packages
- [ ] Feature packages with >15 classes are candidates for sub-feature splitting
- [ ] Each package has a clear cohesive responsibility
- [ ] New feature = new package, not additions to existing packages
