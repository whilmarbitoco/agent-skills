# Barcode Generation (ZXing) — Anti-Patterns

## Problem 1: Generating barcodes on the FX thread

```java
// WRONG — blocks UI during generation
public void onPrintReceipt() {
    BufferedImage barcode = generateBarcode(receiptId); // slow
    printReceipt(barcode);
}
```

```java
// FIX — generate async
public void onPrintReceipt() {
    CompletableFuture.supplyAsync(() -> generateBarcode(receiptId))
        .thenAcceptAsync(barcode -> printReceipt(barcode), Platform::runLater);
}
```

## Problem 2: No error handling for invalid data

```java
// WRONG — crashes on empty string
BitMatrix matrix = new MultiFormatWriter().encode("", BarcodeFormat.CODE_128, 200, 50);
```

```java
// FIX — validate before encoding
if (data == null || data.isBlank()) {
    throw new IllegalArgumentException("Barcode data cannot be empty");
}
try {
    BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, 200, 50);
} catch (WriterException e) {
    throw new RuntimeException("Failed to generate barcode for: " + data, e);
}
```

## Problem 3: Wrong barcode format for data

```java
// WRONG — EAN-13 for non-numeric data
new MultiFormatWriter().encode("ABC-123", BarcodeFormat.EAN_13, 200, 50);
```

```java
// FIX — choose format based on data
BarcodeFormat format = data.matches("\d{12,13}") ? BarcodeFormat.EAN_13 : BarcodeFormat.CODE_128;
```
