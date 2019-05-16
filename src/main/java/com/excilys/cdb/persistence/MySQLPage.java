package com.excilys.cdb.persistence;

public class MySQLPage {

    private final MySQLLimit limit;
    private final int offset;

    public MySQLPage(MySQLLimit limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public String getPagination() {
        return limit.getLimit();
    }

	public int getOffset() {
		return offset;
	}
}