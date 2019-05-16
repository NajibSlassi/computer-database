 package com.excilys.cdb.persistence;


public class MySQLOffset {

    private final int offset;

    public MySQLOffset(int offset) {
        this.offset = offset;
    }

  
    public String getOffset() {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset couldn't be a negative number.");
        }

        return offset + ",";
    }
}

