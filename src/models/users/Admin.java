package models.users;

import enums.UserRole;

public class Admin extends User {

    public Admin(String name, String email, String phone) {
        super(name, email, phone, UserRole.ADMIN);
    }
}