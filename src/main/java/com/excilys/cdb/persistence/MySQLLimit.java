package com.excilys.cdb.persistence;

public class MySQLLimit {

    private final MySQLOffset offset;
    private final int limit;

    public MySQLLimit(MySQLOffset offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public String getLimit() {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit should be a strictly positive number.");
        }

        return String.format("LIMIT %s %s", offset.getOffset(), limit);
    }
}