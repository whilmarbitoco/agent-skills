package com.pos.domain.session;

import java.time.*;
import java.util.*;

/**
 * Cash session: track cash in/out during a shift.
 * Opening balance → transactions → closing balance → variance.
 */
public class CashSessionManagement {

    enum SessionStatus { OPEN, CLOSED, RECONCILED }

    record CashSession(
        String id,
        String cashierId,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        java.math.BigDecimal openingBalance,
        java.math.BigDecimal closingBalance,
        java.math.BigDecimal expectedBalance,
        java.math.BigDecimal variance,
        SessionStatus status
    ) {}

    class CashSessionService {
        private CashSession current;

        public CashSession open(String cashierId, java.math.BigDecimal openingBalance) {
            current = new CashSession(
                UUID.randomUUID().toString(), cashierId,
                LocalDateTime.now(), null,
                openingBalance, null, openingBalance,
                java.math.BigDecimal.ZERO, SessionStatus.OPEN
            );
            return current;
        }

        public CashSession close(java.math.BigDecimal closingBalance,
                                  java.math.BigDecimal totalSales) {
            java.math.BigDecimal expected = current.openingBalance().add(totalSales);
            java.math.BigDecimal variance = closingBalance.subtract(expected);
            current = new CashSession(
                current.id(), current.cashierId(),
                current.openedAt(), LocalDateTime.now(),
                current.openingBalance(), closingBalance,
                expected, variance, SessionStatus.CLOSED
            );
            return current;
        }
    }
}
