package com.example.build;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Utility to verify that a jar or class file is reproducible.
 * Run during CI to hash artifacts and compare against reference build.
 */
public class BuildVerifier {

    public static String sha256Hex(InputStream input) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[8192];
            int n;
            while ((n = input.read(buf)) != -1) {
                digest.update(buf, 0, n);
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static String sha256Hex(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    /**
     * Compare two artifact hashes for reproducibility verification.
     * @return true if byte-identical
     */
    public static boolean isReproducible(byte[] build1, byte[] build2) {
        String hash1 = sha256Hex(build1);
        String hash2 = sha256Hex(build2);
        return hash1.equals(hash2);
    }
}
