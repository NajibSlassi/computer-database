package com.excilys.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceFactory {

    private static final String FICHIER_PROPERTIES       = "com/excilys/cdb/persistence/datasource.properties";
    private static final String PROPERTY_URL             = "jdbcUrl";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "dataSource.user";
    private static final String PROPERTY_MOT_DE_PASSE    = "dataSource.password";
    private static final String PROPERTY_CACHE_PS = "dataSource.cachePrepStmts";
    private static final String PROPERTY_PS_CACHE_SIZE="dataSource.prepStmtCacheSize";
    private static final String PROPERTY_PS_CACHE_SQL_LIMITE="dataSource.prepStmtCacheSqlLimit";
    private String              url;
    private String              username;
    private String              password;
    private String              cachPS;
    private String              cacheSize;
    private String              sqlLimit;

    DataSourceFactory( String url, String username, String password, String cachPS,String cacheSize, String sqlLimit) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.cachPS=cachPS;
        this.cacheSize=cacheSize;
        this.sqlLimit=sqlLimit;
        
    }
    
    /*
     * MÈthode chargÈe de rÈcupÈrer les informations de connexion ‡† la base de
     * donnÈes, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DataSourceFactory getInstance() throws DAOConfigurationException {
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

        DataSourceFactory instance = new DataSourceFactory( url, nomUtilisateur, motDePasse,cachPS,cachSize, sqlLimit);
        return instance;
    }

    /* M√©thode charg√©e de fournir une connexion √† la base de donn√©es */
     /* package */ Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }

    /*
     * M√©thodes de r√©cup√©ration de l'impl√©mentation des diff√©rents DAO 
     */
    
    
    
}
