package com.deptManager.deptManager.model;

public enum PersonRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    String roleName;

    PersonRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
