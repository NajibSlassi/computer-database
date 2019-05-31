package com.excilys.cdb.springconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.cdb.controller.EditComputerController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"Main","com.excilys.cdb"})
public class SpringConfig {
	private static HikariConfig config;
	
	private static Logger LOGGER = LoggerFactory.getLogger(SpringConfig.class);
    
    private static final String FICHIER_PROPERTIES       = "com/excilys/cdb/persistence/datasource.properties";
    private static final String PROPERTY_URL             = "jdbcUrl";
    private static final String PROPERTY_NOM_UTILISATEUR = "dataSource.user";
    private static final String PROPERTY_MOT_DE_PASSE    = "dataSource.password";
    private static final String PROPERTY_CACHE_PS = "dataSource.cachePrepStmts";
    private static final String PROPERTY_PS_CACHE_SIZE="dataSource.prepStmtCacheSize";
    private static final String PROPERTY_PS_CACHE_SQL_LIMITE="dataSource.prepStmtCacheSqlLimit";

	@SuppressWarnings("unused")
	@Bean
	public HikariConfig hikariConfig() {
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
            LOGGER.warn( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
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
        	LOGGER.warn( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }
        config = new HikariConfig( properties );
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return config;
    }
	

	@Bean
	public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}

}
