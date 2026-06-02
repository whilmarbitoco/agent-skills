package com.pos.domain.barcode;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import java.util.*;
import java.util.concurrent.*;

/**
 * Barcode scanning flow: camera capture → decode → lookup → add to cart.
 * Async processing keeps UI responsive.
 */
public class BarcodeScanningFlow {

    private final ProductRepository productRepo;
    private final CartService cartService;
    private String lastScanned = "";
    private long lastScanTime = 0;

    public BarcodeScanningFlow(ProductRepository productRepo, CartService cartService) {
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    // Called by hardware scanner or camera decoder
    public CompletableFuture<ScanResult> onBarcodeScanned(String barcode) {
        // Debounce: ignore same barcode within 500ms
        long now = System.currentTimeMillis();
        if (barcode.equals(lastScanned) && now - lastScanTime < 500) {
            return CompletableFuture.completedFuture(ScanResult.ignored());
        }
        lastScanned = barcode;
        lastScanTime = now;

        // Async lookup
        return CompletableFuture.supplyAsync(() -> {
            Product product = productRepo.findByBarcode(barcode);
            if (product == null) {
                return ScanResult.notFound(barcode);
            }
            cartService.addItem(product);
            return ScanResult.success(product);
        });
    }

    record ScanResult(String status, Product product, String barcode) {
        static ScanResult ignored() { return new ScanResult("IGNORED", null, ""); }
        static ScanResult notFound(String code) { return new ScanResult("NOT_FOUND", null, code); }
        static ScanResult success(Product p) { return new ScanResult("SUCCESS", p, p.barcode()); }
    }
    record Product(String barcode, String name) {}
    interface ProductRepository { Product findByBarcode(String code); }
    interface CartService { void addItem(Product p); }
}
