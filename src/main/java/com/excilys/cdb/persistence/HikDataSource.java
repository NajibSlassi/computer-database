package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class HikDataSource {
	 
    private static HikariConfig config;
    private static HikariDataSource ds;
    
    private static final String FICHIER_PROPERTIES       = "com/excilys/cdb/persistence/datasource.properties";
    private static final String PROPERTY_URL             = "jdbcUrl";
    private static final String PROPERTY_NOM_UTILISATEUR = "dataSource.user";
    private static final String PROPERTY_MOT_DE_PASSE    = "dataSource.password";
    private static final String PROPERTY_CACHE_PS = "dataSource.cachePrepStmts";
    private static final String PROPERTY_PS_CACHE_SIZE="dataSource.prepStmtCacheSize";
    private static final String PROPERTY_PS_CACHE_SQL_LIMITE="dataSource.prepStmtCacheSqlLimit";

    static {
    	
    	Properties properties = new Properties();
        String url;
        String nomUtilisateur;
        String motDePasse;
        String cachPS;
        String cachSize;
        String sqlLimit;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            cachPS= properties.getProperty(PROPERTY_CACHE_PS);
            cachSize= properties.getProperty(PROPERTY_PS_CACHE_SIZE);
            sqlLimit= properties.getProperty(PROPERTY_PS_CACHE_SQL_LIMITE);
            
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }
        config = new HikariConfig( properties );
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds = new HikariDataSource( config );
    }
    
}