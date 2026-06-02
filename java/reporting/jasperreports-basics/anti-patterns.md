# JasperReports Basics — Anti-Patterns

## Problem 1: Compiling reports at runtime

```java
// WRONG — compiles .jrxml every time (slow)
JasperReport report = JasperCompileManager.compileReport("receipt.jrxml");
```

```java
// FIX — compile once at startup, cache compiled report
private static final JasperReport RECEIPT_REPORT;

static {
    try {
        RECEIPT_REPORT = JasperCompileManager.compileReport(
            getClass().getResourceAsStream("/reports/receipt.jrxml")
        );
    } catch (JRException e) {
        throw new RuntimeException("Failed to compile report", e);
    }
}
```

## Problem 2: Passing data as parameters only

```java
// WRONG — all data as parameters (limited, no collections)
parameters.put("productName", product.getName());
parameters.put("price", product.getPrice());
// Can't pass a list of line items!
```

```java
// FIX — use JRDataSource for collections
parameters.put("REPORT_DATA_SOURCE", new JRBeanCollectionDataSource(sale.getLineItems()));
parameters.put("RECEIPT_NUMBER", sale.getReceiptNumber());
```

## Problem 3: Not closing resources

```java
// WRONG — memory leak
JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
JasperExportManager.exportReportToPdfFile(print, "receipt.pdf");
// print never closed
```

```java
// FIX — use try-with-resources
try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
    JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
    JasperExportManager.exportReportToPdfStream(print, out);
    return out.toByteArray();
}
```
