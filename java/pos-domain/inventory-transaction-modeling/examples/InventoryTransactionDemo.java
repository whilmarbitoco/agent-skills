package com.pos.inventory.transaction;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

// ─── Currency Enum ───────────────────────────────────────────────────────────
enum Currency {
    PHP("PHP", 2),
    USD("USD", 2),
    EUR("EUR", 2);

    private final String code;
    private final int defaultFractionDigits;

    Currency(String code, int defaultFractionDigits) {
        this.code = code;
        this.defaultFractionDigits = defaultFractionDigits;
    }

    public String getCode() { return code; }
    public int defaultFractionDigits() { return defaultFractionDigits; }
}

// ─── Money Record ────────────────────────────────────────────────────────────
public record Money(BigDecimal amount, Currency currency) {
    public static final MathContext MATH_CONTEXT = new MathContext(16, RoundingMode.HALF_UP);

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
        if (amount.scale() > currency.defaultFractionDigits()) {
            throw new IllegalArgumentException(
                "amount scale %d exceeds currency %s max fraction digits %d"
                    .formatted(amount.scale(), currency, currency.defaultFractionDigits())
            );
        }
    }

    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(this.amount.add(other.amount, MATH_CONTEXT), this.currency);
    }

    public Money subtract(Money other) {
        assertSameCurrency(other);
        return new Money(this.amount.subtract(other.amount, MATH_CONTEXT), this.currency);
    }

    public Money multiply(int quantity) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity), MATH_CONTEXT), this.currency);
    }

    public Money negate() {
        return new Money(this.amount.negate(), this.currency);
    }

    public boolean isNegative() {
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    private void assertSameCurrency(Money other) {
        if (this.currency != other.currency) {
            throw new IllegalArgumentException(
                "Currency mismatch: %s vs %s".formatted(this.currency, other.currency)
            );
        }
    }
}

// ─── Movement Type (Sealed) ──────────────────────────────────────────────────
sealed interface MovementType permits MovementType.Purchase, MovementType.Sale,
    MovementType.Adjustment, MovementType.Return, MovementType.Loss {

    enum Purchase implements MovementType { PURCHASE }
    enum Sale implements MovementType { SALE }
    enum Adjustment implements MovementType { INCREASE, DECREASE }
    enum Return implements MovementType { RETURN }
    enum Loss implements MovementType { LOSS }
}

// ─── StockMovement Record ────────────────────────────────────────────────────
record StockMovement(
    UUID id,
    String sku,
    int quantity,
    Money unitCost,
    MovementType type,
    UUID journalEntryId,
    Instant timestamp
) {
    public StockMovement {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(sku, "sku must not be null");
        if (sku.isBlank()) throw new IllegalArgumentException("sku must not be blank");
        if (quantity == 0) throw new IllegalArgumentException("quantity must not be zero");
        Objects.requireNonNull(unitCost, "unitCost must not be null");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(journalEntryId, "journalEntryId must not be null");
        Objects.requireNonNull(timestamp, "timestamp must not be null");
    }

    public Money totalCost() {
        return unitCost().multiply(Math.abs(quantity()));
    }
}

// ─── Ledger Side ─────────────────────────────────────────────────────────────
enum Side { DEBIT, CREDIT }

// ─── Ledger Line Record ──────────────────────────────────────────────────────
record LedgerLine(String accountCode, Money amount, Side side) {
    public LedgerLine {
        Objects.requireNonNull(accountCode, "accountCode must not be null");
        if (accountCode.isBlank()) throw new IllegalArgumentException("accountCode must not be blank");
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(side, "side must not be null");
    }
}

// ─── Journal Entry Record ────────────────────────────────────────────────────
record JournalEntry(
    UUID id,
    List<LedgerLine> lines,
    Instant timestamp,
    String description
) {
    public JournalEntry {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(lines, "lines must not be null");
        if (lines.isEmpty()) throw new IllegalArgumentException("lines must not be empty");
        lines = List.copyOf(lines); // defensive immutable copy
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        Objects.requireNonNull(description, "description must not be null");
    }

    public boolean isBalanced() {
        var totals = lines.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                LedgerLine::side,
                java.util.stream.Collectors.reducing(
                    new Money(BigDecimal.ZERO, lines.get(0).amount().currency()),
                    LedgerLine::amount,
                    Money::add
                )
            ));
        Money debits = totals.getOrDefault(Side.DEBIT, new Money(BigDecimal.ZERO, lines.get(0).amount().currency()));
        Money credits = totals.getOrDefault(Side.CREDIT, new Money(BigDecimal.ZERO, lines.get(0).amount().currency()));
        return debits.compareTo(credits) == 0;
    }

    public JournalEntry createReversal(String reason) {
        List<LedgerLine> reversed = lines.stream()
            .map(line -> new LedgerLine(
                line.accountCode(),
                line.amount(),
                line.side() == Side.DEBIT ? Side.CREDIT : Side.DEBIT
            ))
            .toList();
        return new JournalEntry(UUID.randomUUID(), reversed, Instant.now(), "REVERSAL: " + reason);
    }
}

// ─── Transaction Ledger (Append-Only) ────────────────────────────────────────
class TransactionLedger {
    private final List<JournalEntry> entries = new ArrayList<>();

    public void addEntry(JournalEntry entry) {
        Objects.requireNonNull(entry, "entry must not be null");
        if (!entry.isBalanced()) {
            throw new IllegalArgumentException("Journal entry is not balanced: " + entry.id());
        }
        entries.add(entry);
    }

    public void reverseEntry(UUID originalEntryId, String reason) {
        JournalEntry original = entries.stream()
            .filter(e -> e.id().equals(originalEntryId))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Entry not found: " + originalEntryId));
        entries.add(original.createReversal(reason));
    }

    public Money trialBalance(Currency currency) {
        Money sum = new Money(BigDecimal.ZERO, currency);
        for (JournalEntry entry : entries) {
            for (LedgerLine line : entry.lines()) {
                if (line.amount().currency() == currency) {
                    sum = switch (line.side()) {
                        case DEBIT -> sum.add(line.amount());
                        case CREDIT -> sum.subtract(line.amount());
                    };
                }
            }
        }
        return sum;
    }

    public List<JournalEntry> entries() {
        return Collections.unmodifiableList(entries);
    }
}

// ─── Stock Movement Service (Constructor Injection) ──────────────────────────
class StockMovementService {
    private final TransactionLedger ledger;

    // Constructor injection — no @Autowired, no field injection
    public StockMovementService(TransactionLedger ledger) {
        this.ledger = Objects.requireNonNull(ledger, "ledger must not be null");
    }

    public StockMovement recordPurchase(String sku, int quantity, Money unitCost) {
        Money total = unitCost.multiply(quantity);
        UUID journalId = UUID.randomUUID();

        JournalEntry entry = new JournalEntry(
            journalId,
            List.of(
                new LedgerLine("INVENTORY_ASSET", total, Side.DEBIT),
                new LedgerLine("ACCOUNTS_PAYABLE", total, Side.CREDIT)
            ),
            Instant.now(),
            "Stock purchase: %s x %d @ %s".formatted(sku, quantity, unitCost)
        );

        ledger.addEntry(entry);

        return new StockMovement(
            UUID.randomUUID(), sku, quantity, unitCost,
            MovementType.Purchase.PURCHASE, journalId, Instant.now()
        );
    }

    public StockMovement recordSale(String sku, int quantity, Money unitCost) {
        Money total = unitCost.multiply(quantity);
        UUID journalId = UUID.randomUUID();

        JournalEntry entry = new JournalEntry(
            journalId,
            List.of(
                new LedgerLine("COST_OF_GOODS_SOLD", total, Side.DEBIT),
                new LedgerLine("INVENTORY_ASSET", total, Side.CREDIT)
            ),
            Instant.now(),
            "Stock sale: %s x %d @ %s".formatted(sku, quantity, unitCost)
        );

        ledger.addEntry(entry);

        return new StockMovement(
            UUID.randomUUID(), sku, -quantity, unitCost,
            MovementType.Sale.SALE, journalId, Instant.now()
        );
    }
}

// ─── Demo ─────────────────────────────────────────────────────────────────────
class InventoryTransactionDemo {
    public static void main(String[] args) {
        var ledger = new TransactionLedger();
        var service = new StockMovementService(ledger);

        // Purchase 100 units @ ₱25.00 each
        var purchase = service.recordPurchase("SKU-001", 100, new Money(new BigDecimal("25.00"), Currency.PHP));
        System.out.println("Purchase: " + purchase);
        System.out.println("Total cost: " + purchase.totalCost());

        // Sell 30 units @ ₱25.00 COGS
        var sale = service.recordSale("SKU-001", 30, new Money(new BigDecimal("25.00"), Currency.PHP));
        System.out.println("Sale: " + sale);

        // Verify trial balance is zero
        Money balance = ledger.trialBalance(Currency.PHP);
        System.out.println("Trial balance (PHP): " + balance);
        System.out.println("Balanced: " + balance.isZero());

        // Print all ledger entries
        ledger.entries().forEach(entry -> {
            System.out.println("\nJournal Entry: " + entry.id());
            System.out.println("  Description: " + entry.description());
            entry.lines().forEach(line ->
                System.out.println("  %s %s %s".formatted(line.side(), line.accountCode(), line.amount()))
            );
        });
    }
}
