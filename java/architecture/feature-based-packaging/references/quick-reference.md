# Feature-Based Packaging — Quick Reference

## Naming convention
```
com.<app>.<feature>.<class>
```

## Template feature package
```
com.shop.invoice/
├── Invoice.java              entity
├── InvoiceDto.java           public DTO (record)
├── InvoiceCreate.java        request (record)
├── InvoiceController.java    public entry point
├── InvoiceService.java       package-private
├── InvoiceRepository.java    interface
├── InvoiceRepositoryImpl.java package-private
└── InvoiceMapper.java        package-private
```

## When to split
- Feature package grows beyond 15 classes
- A class is imported by more than 2 other features
- Two sub-domains evolve at different rates
