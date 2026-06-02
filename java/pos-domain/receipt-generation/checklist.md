# Checklist: Receipt Generation

## Template Design
- [ ] `.jrxml` template compiled once and cached (not per-request)
- [ ] Store info (name, address, TIN, permit) passed as parameters, not hardcoded
- [ ] Template supports both PDF (A4/letter) and thermal (80mm/58mm) layouts
- [ ] QR code placeholder included in template as image element
- [ ] Tax breakdown sub-report included (VATable, VAT-exempt, zero-rated)
- [ ] Receipt number follows sequential format (e.g., REG-YYYYMMDD-XXXXX)

## Data Model
- [ ] `Receipt` is an immutable record
- [ ] `ReceiptItem` is an immutable record with `Money` fields
- [ ] `ReceiptTotals` is an immutable record (subtotal, tax, discount, grand total)
- [ ] `PaymentInfo` is an immutable record (tendered, change, payment method)
- [ ] All monetary fields use `Money` (BigDecimal + Currency.PHP)
- [ ] Collections defensively copied with `List.copyOf()`

## PDF Output
- [ ] `JasperExportManager.exportReportToPdf()` used for PDF generation
- [ ] PDF bytes can be written to `OutputStream` or returned as `byte[]`
- [ ] PDF suitable for email attachment

## Thermal Printer Output
- [ ] ESC/POS byte commands generated for thermal printer
- [ ] Correct code page set (e.g., CP437) before printing text
- [ ] Paper cut command sent at end of receipt
- [ ] Bold/double-height formatting for header and totals
- [ ] QR code printed via ESC/POS QR command (not as image)

## QR Code
- [ ] QR code contains URL to digital receipt verification endpoint
- [ ] QR code cached for identical URLs
- [ ] QR code embedded in PDF template as `BufferedImage`
- [ ] QR code size appropriate for receipt (e.g., 120x120px)

## Performance
- [ ] Template compilation cached
- [ ] QR code generation cached
- [ ] No `double`/`float` arithmetic anywhere in receipt calculations
- [ ] Constructor injection for all service dependencies
