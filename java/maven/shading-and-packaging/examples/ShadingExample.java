package com.example.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class demonstrating an executable shaded jar entry point.
 * The maven-shade-plugin ManifestResourceTransformer sets this as Main-Class.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Application starting with {} args", args.length);
        var service = new GreetingService();
        String message = service.greet("World");
        log.info("Greeting: {}", message);
    }
}

class GreetingService {
    String greet(String name) {
        return "Hello, " + name + "!";
    }
}
