package com.example.nicolas.ihm;

class Account {
    private String id;
    private String name;
    private String password;
    private String adminPassword;

    Account(String id, String name, String password, String adminPassword) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.adminPassword = adminPassword;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    String getAdminPassword() {
        return adminPassword;
    }
}