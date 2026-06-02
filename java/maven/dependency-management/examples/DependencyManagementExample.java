package com.example.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Example service that depends on slf4j (managed via parent BOM).
 * Demonstrates constructor injection and parameterized logging.
 */
public record ProductService(ProductRepository repository) {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService {
        if (repository == null) {
            throw new IllegalArgumentException("repository must not be null");
        }
    }

    public Optional<Product> findById(long id) {
        log.debug("Finding product by id={}", id);
        return repository.findById(id);
    }

    public List<Product> findByMaxPrice(BigDecimal maxPrice) {
        log.info("Finding products under {}", maxPrice);
        return repository.findAll().stream()
            .filter(p -> p.price().compareTo(maxPrice) <= 0)
            .toList();
    }

    public Product save(Product product) {
        log.info("Saving product: {}", product.name());
        return repository.save(product);
    }
}

interface ProductRepository {
    Optional<Product> findById(long id);
    List<Product> findAll();
    Product save(Product product);
}

record Product(long id, String name, BigDecimal price) {}
