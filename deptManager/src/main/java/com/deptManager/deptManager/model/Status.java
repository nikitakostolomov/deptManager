package com.deptManager.deptManager.model;

public enum Status {
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETE("COMPLETE");

    String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
