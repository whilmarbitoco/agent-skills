package com.pos.security.auth;

import org.mindrot.jbcrypt.BCrypt;
import java.util.*;

/**
 * POS authentication: BCrypt password hashing, role-based access,
 * session management. No plain text passwords.
 */
public class AuthPatternsPos {

    enum Role { CASHIER, MANAGER, ADMIN }

    record User(String id, String username, String passwordHash, Set<Role> roles) {
        boolean hasRole(String role) { return roles.contains(Role.valueOf(role)); }
        boolean canDelete() { return roles.contains(Role.ADMIN); }
        boolean canRefund() { return roles.contains(Role.MANAGER) || roles.contains(Role.ADMIN); }
    }

    class AuthService {
        private final UserRepository userRepo;
        private User currentUser;

        public AuthService(UserRepository userRepo) { this.userRepo = userRepo; }

        public boolean login(String username, String password) {
            User user = userRepo.findByUsername(username);
            if (user == null) return false;
            if (BCrypt.checkpw(password, user.passwordHash())) {
                currentUser = user;
                return true;
            }
            return false;
        }

        public void logout() { currentUser = null; }
        public User getCurrentUser() { return currentUser; }

        public void requireRole(String role) {
            if (currentUser == null) throw new RuntimeException("Not authenticated");
            if (!currentUser.hasRole(role)) throw new RuntimeException("Access denied: " + role);
        }

        public String hashPassword(String plain) {
            return BCrypt.hashpw(plain, BCrypt.gensalt(12));
        }
    }

    interface UserRepository {
        User findByUsername(String username);
        void save(User user);
    }
}
