package com.pos.domain.stock;

import java.time.*;
import java.util.*;

/**
 * Stock movement architecture: every change is a movement record.
 * Supports IN, OUT, ADJUSTMENT, RETURN, DAMAGE types.
 * Never update stock directly — always through movement.
 */
public class StockMovementArchitecture {

    enum MovementType { SALE, PURCHASE, ADJUSTMENT, RETURN, DAMAGE, TRANSFER }

    record StockMovement(
        String id,
        String productId,
        MovementType type,
        int quantity,       // positive = in, negative = out
        int balanceAfter,
        String referenceId, // sale ID, PO ID, etc.
        String reason,
        String userId,
        Instant timestamp
    ) {}

    class StockService {
        private final MovementRepository movementRepo;
        private final ProductRepository productRepo;

        public StockService(MovementRepository m, ProductRepository p) {
            this.movementRepo = m;
            this.productRepo = p;
        }

        public StockMovement recordMovement(String productId, MovementType type,
                                             int qty, String reason, String userId) {
            Product product = productRepo.findById(productId);
            int newBalance = product.quantity() + qty;
            if (newBalance < 0) throw new RuntimeException("Insufficient stock");

            StockMovement movement = new StockMovement(
                UUID.randomUUID().toString(), productId, type, qty,
                newBalance, null, reason, userId, Instant.now()
            );
            movementRepo.save(movement);
            productRepo.updateQuantity(productId, newBalance);
            return movement;
        }

        public List<StockMovement> getMovements(String productId, Instant from, Instant to) {
            return movementRepo.findByProductAndDateRange(productId, from, to);
        }
    }

    interface MovementRepository {
        void save(StockMovement m);
        List<StockMovement> findByProductAndDateRange(String id, Instant from, Instant to);
    }
    interface ProductRepository {
        Product findById(String id);
        void updateQuantity(String id, int qty);
    }
    record Product(String id, String name, int quantity) {}
}
