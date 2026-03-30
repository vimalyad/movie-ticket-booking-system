package models.users;

import enums.UserRole;

import java.util.Objects;
import java.util.UUID;

public abstract class User {

    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final UserRole role;

    protected User(String name, String email, String phone, UserRole role) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(role, "role must not be null");

        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email.toLowerCase().trim();
        this.phone = phone;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role + "[" + id.substring(0, 8) + "] " + name;
    }
}
