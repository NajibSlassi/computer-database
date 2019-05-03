package com.excilys.cdb.persistence;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	 
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream fichierProperties = classLoader.getResourceAsStream( "computer-database/src/main/java/com/excilys/cdb/persistence/datasource.properties" );
	private static HikariConfig config = new HikariConfig("computer-database/src/main/java/com/excilys/cdb/persistence/datasource.properties" );
    private static HikariDataSource ds;
 
    static {
        ds = new HikariDataSource( config );
    }
 
    private DataSource() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

