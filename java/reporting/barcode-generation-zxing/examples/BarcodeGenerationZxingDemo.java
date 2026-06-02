package com.pos.reporting.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Barcode generation with ZXing: CODE_128, EAN-13, QR_CODE.
 * Generate barcodes for receipts, product labels, inventory tags.
 */
public class BarcodeGeneration {

    // Generate CODE_128 barcode (alphanumeric, most flexible)
    public static BufferedImage generateCode128(String data, int width, int height) {
        return generate(data, BarcodeFormat.CODE_128, width, height);
    }

    // Generate EAN-13 barcode (numeric only, 13 digits)
    public static BufferedImage generateEAN13(String data, int width, int height) {
        if (!data.matches("\d{12,13}")) {
            throw new IllegalArgumentException("EAN-13 requires 12-13 digits");
        }
        return generate(data, BarcodeFormat.EAN_13, width, height);
    }

    // Generate QR Code (for URLs, payment links)
    public static BufferedImage generateQR(String data, int size) {
        return generate(data, BarcodeFormat.QR_CODE, size, size);
    }

    private static BufferedImage generate(String data, BarcodeFormat format, int w, int h) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix matrix = new MultiFormatWriter().encode(data, format, w, h, hints);
            return MatrixToImageWriter.toBufferedImage(matrix);
        } catch (WriterException e) {
            throw new RuntimeException("Barcode generation failed: " + data, e);
        }
    }

    // Generate barcode for receipt (CODE_128 with receipt ID)
    public static BufferedImage generateReceiptBarcode(String receiptId) {
        return generateCode128(receiptId, 200, 50);
    }
}
