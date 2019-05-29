package com.excilys.cdb.persistence;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * @author imssbora
 */
public class HibernateUtil {

   private static StandardServiceRegistry registry;
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory == null) {
         try {
            StandardServiceRegistryBuilder registryBuilder = 
                  new StandardServiceRegistryBuilder();

            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "1234");
            settings.put(Environment.HBM2DDL_AUTO, "update");
            settings.put(Environment.SHOW_SQL, true);

            // HikariCP settings
            
            // Maximum waiting time for a connection from the pool
            settings.put("hibernate.hikari.connectionTimeout", "20000");
            // Minimum number of ideal connections in the pool
            settings.put("hibernate.hikari.minimumIdle", "10");
            // Maximum number of actual connection in the pool
            settings.put("hibernate.hikari.maximumPoolSize", "20");
            // Maximum time that a connection is allowed to sit ideal in the pool
            settings.put("hibernate.hikari.idleTimeout", "300000");

            registryBuilder.applySettings(settings);

            registry = registryBuilder.build();
            MetadataSources sources = new MetadataSources(registry)
                  .addAnnotatedClass(Computer.class)
                  .addAnnotatedClass(Company.class);
            	  
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
         } catch (Exception e) {
            if (registry != null) {
               StandardServiceRegistryBuilder.destroy(registry);
            }
            e.printStackTrace();
         }
      }
      return sessionFactory;
   }

   public static void shutdown() {
      if (registry != null) {
         StandardServiceRegistryBuilder.destroy(registry);
      }
   }
}