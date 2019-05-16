 package com.excilys.cdb.persistence;


public class MySQLOffset implements Offset {

    private final int offset;

    public MySQLOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String getOffset() {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset couldn't be a negative number.");
        }

        return offset + ",";
    }
}

