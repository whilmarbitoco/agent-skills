# Barcode Scanning Flow — Anti-Patterns

## Problem 1: Blocking the UI thread during scan

```java
// WRONG — camera capture blocks FX thread
public void onScanButtonClick() {
    BarcodeResult result = camera.captureAndDecode(); // blocks for seconds
    displayResult(result);
}
```

```java
// FIX — async capture with callback
public void onScanButtonClick() {
    camera.captureAsync(result -> {
        Platform.runLater(() -> displayResult(result));
    });
}
```

## Problem 2: No debounce on rapid scans

```java
// WRONG — same barcode triggers multiple times
barcodeListener.onScan(code -> processSale(code));
```

```java
// FIX — debounce rapid scans
private String lastScanned = "";
private long lastScanTime = 0;

barcodeListener.onScan(code -> {
    long now = System.currentTimeMillis();
    if (code.equals(lastScanned) && now - lastScanTime < 500) return;
    lastScanned = code;
    lastScanTime = now;
    processSale(code);
});
```

## Problem 3: Hardcoded barcode format

```java
// WRONG — only supports EAN-13
if (barcode.length() != 13) throw new InvalidBarcodeException();
```

```java
// FIX — support multiple formats
MultiFormatReader reader = new MultiFormatReader();
reader.setHints(Map.of(
    DecodeHintType.POSSIBLE_FORMATS,
    List.of(BarcodeFormat.EAN_13, BarcodeFormat.CODE_128, BarcodeFormat.QR_CODE)
));
```
