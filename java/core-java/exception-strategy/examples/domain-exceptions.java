package com.simplepos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception strategy:
 * - Domain exceptions are unchecked (RuntimeException)
 * - Always include entity context in messages
 * - Use Optional for nullable query results
 * - Log at WARN for recoverable, ERROR for data loss risk
 */

// Domain exception hierarchy
public class DomainException extends RuntimeException {
    public DomainException(String message) { super(message); }
}

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(long productId, int requested, int available) {
        super("Product %d: insufficient stock (requested=%d, available=%d)"
            .formatted(productId, requested, available));
    }
}

public class CashSessionClosedException extends DomainException {
    public CashSessionClosedException(long sessionId) {
        super("Cash session %d is closed. Reopen before recording sales.".formatted(sessionId));
    }
}

// Service usage
public class SaleService {
    private static final Logger log = LoggerFactory.getLogger(SaleService.class);
    private final ProductRepository productRepo;
    private final CashSessionRepository sessionRepo;

    public SaleService(ProductRepository productRepo, CashSessionRepository sessionRepo) {
        this.productRepo = productRepo;
        this.sessionRepo = sessionRepo;
    }

    public Sale recordSale(long sessionId, List<SaleLine> lines) {
        CashSession session = sessionRepo.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (session.isClosed()) {
            throw new CashSessionClosedException(sessionId);
        }

        for (SaleLine line : lines) {
            Product product = productRepo.findById(line.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + line.productId()));

            if (product.getStock() < line.quantity()) {
                throw new InsufficientStockException(product.getId(), line.quantity(), product.getStock());
            }
            product.adjustStock(-line.quantity());
            productRepo.save(product);
        }

        Sale sale = new Sale(sessionId, lines);
        log.info("Recorded sale: id={}, total={}", sale.getId(), sale.getTotal());
        return sale;
    }
}
