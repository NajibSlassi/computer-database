package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Computer;

import ch.qos.logback.classic.Logger;

@Component
@Repository
@Transactional
public class DAOComputer{
		
	public DAOComputer(DataSource dataSource) {
		super();
	}

	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(DAOComputer.class);

    public List<Computer> listComputer(int pageNumber,int pageSize,String[] orderBy) throws ParseException {
    	
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
           Root<Computer> root = query.from(Computer.class);
           query.multiselect(root.get("name"),root.get("introduced"),root.get("discontinued"),root.get("company"));
           switch (orderBy[1]) {
           case "asc":
        	   query.orderBy(builder.asc(root.get(orderBy[0])));
        	   break;
           case "desc":
        	   query.orderBy(builder.desc(root.get(orderBy[0])));
        	   break;
           }
           Query<Computer> q=session.createQuery(query);
           q.setFirstResult((pageNumber-1) * pageSize);
           q.setMaxResults(pageSize);
           
           List<Computer> list=q.getResultList();
           LOGGER.info("longueur de la liste trouvée par hibernate :"+list.size());
           transaction.commit();
           return list;
        } catch (Exception e) {
           e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
		return null;
     }
    
	public List<Computer> listComputer(int pageNumber,int pageSize) throws  ParseException {
		Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
           Root<Computer> root = query.from(Computer.class);
           query.multiselect(root.get("id"),root.get("name"),root.get("introduced"),root.get("discontinued"),root.get("company"));
           
           Query<Computer> q=session.createQuery(query);
           q.setFirstResult((pageNumber-1) * pageSize);
           q.setMaxResults(pageSize);
           List<Computer> list= q.getResultList();
           LOGGER.info("liste trouvée par hibernate :"+list.toString());
           transaction.commit();
           return list;
        } catch (Exception e) {
           e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
		return null;
	    }
  
    public List<Computer> listComputerByName(int pageNumber,int pageSize,String name,String[] orderBy) throws ParseException {
    	
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
           Root<Computer> root = query.from(Computer.class);
           query.multiselect(root.get("id"),root.get("name"),root.get("introduced"),root.get("discontinued"),root.get("company"));
           query.where(builder.like(root.get("name"), "%"+name+"%"));
          
           LOGGER.info("orderby contient "+ orderBy.toString());
           if(orderBy[1]!=null) {
        	   switch (orderBy[1]) {
               case "asc":
            	   query.orderBy(builder.asc(root.get(orderBy[0])));
            	   break;
               case "desc":
            	   query.orderBy(builder.desc(root.get(orderBy[0])));
            	   break;
               }
           }
           Query<Computer> q=session.createQuery(query);
           LOGGER.info("query contient: "+ q.list().toString());
           q.setFirstResult((pageNumber-1) * pageSize);
           q.setMaxResults(pageSize);
           
           List<Computer> list=q.getResultList();
           
           LOGGER.info("longueur de la liste trouvée par hibernate :"+list.size());
           transaction.commit();
           return list;
        } catch (Exception e) {
           e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
		return null;
    }

	public void createComputer(Computer computer) throws IllegalArgumentException {
		//Problem with time zone
		computer.setIntroduced(new Date(computer.getIntroduced().getTime() + 3600*1000));
		computer.setDiscontinued(new Date(computer.getDiscontinued().getTime() + 3600*1000));
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
  
        session.save(computer);
        session.getTransaction().commit();
        
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
    }
	
	public void updateComputer(Computer computer)  {
		
		//Problem with time zone
		computer.setIntroduced(new Date(computer.getIntroduced().getTime() + 3600*1000));
		computer.setDiscontinued(new Date(computer.getDiscontinued().getTime() + 3600*1000));

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			
	        // create update
	        CriteriaUpdate<Computer> update = cb.
	        createCriteriaUpdate(Computer.class);
	
	        // set the root class
	        Root<Computer> e = update.from(Computer.class);
	
	        // set update and where clause
	        update.set("name", computer.getName());
	        update.set("introduced", computer.getIntroduced());
	        update.set("discontinued", computer.getDiscontinued());
	        update.set("company", computer.getCompany());
	   
	        update.where(cb.equal(e.get("id"), computer.getId()));
	
	        // perform update
	        session.createQuery(update).executeUpdate();
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
	}

	public Computer showComputer(int id) throws ParseException {
		Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
           Root<Computer> root = query.from(Computer.class);
           query.multiselect(root.get("id"),root.get("name"),root.get("introduced"),root.get("discontinued"),root.get("company"));
           query.where(builder.equal(root.get("id"), id));
    
           Query<Computer> q=session.createQuery(query);
            
           Computer computer=q.getSingleResult();
           
           transaction.commit();
           return computer;
        } catch (Exception e) {
           e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
		return null;
	}
	
	
	public void deleteComputer(Long id) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			
	        // create update
	        CriteriaDelete<Computer> delete = cb.
	        createCriteriaDelete(Computer.class);
	
	        // set the root class
	        Root<Computer> e = delete.from(Computer.class);
	   
	        delete.where(cb.equal(e.get("id"), id));
	
	        // perform update
	        session.createQuery(delete).executeUpdate();
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
        
	}

	public Long count() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
			CriteriaBuilder qb = session.getCriteriaBuilder();
			CriteriaQuery<Long> cq = qb.createQuery(Long.class);
			cq.select(qb.count(cq.from(Computer.class)));
			return session.createQuery(cq).getSingleResult();
		
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
		return null;
		
    }
	
	public Long maxId() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
		CriteriaBuilder cb1 = session.getCriteriaBuilder();
		CriteriaQuery<Long> cq1 = cb1.createQuery(Long.class);
		Root<Computer> root = cq1.from(Computer.class);
		cq1.select(cb1.max(root.get("id")));
		return session.createQuery(cq1).getSingleResult();
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
		return null;
	}
	
	public Long count(String search) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
			CriteriaBuilder qb = session.getCriteriaBuilder();
			CriteriaQuery<Long> cq = qb.createQuery(Long.class);
			Root<Computer> root = cq.from(Computer.class);
			cq.select(qb.count(root));
			cq.where(qb.like(root.get("name"), "%"+search+"%"));
			return session.createQuery(cq).getSingleResult();
		
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
		return null;
	}
	}
