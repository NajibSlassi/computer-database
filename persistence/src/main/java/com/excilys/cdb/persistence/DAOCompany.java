package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class DAOCompany {
	
    public List<Company> listCompany(int pageNumber,int pageSize) throws ParseException {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Company> query = builder.createQuery(Company.class);
           Root<Company> root = query.from(Company.class);
           query.multiselect(root.get("id"),root.get("name"));
           
           Query<Company> q=session.createQuery(query);
           q.setFirstResult((pageNumber-1) * pageSize);
           q.setMaxResults(pageSize);
           
           List<Company> list=q.getResultList();
           
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
    
	public Company showCompany(int id) {
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Company> query = builder.createQuery(Company.class);
           Root<Company> root = query.from(Company.class);
           query.multiselect(root.get("id"),root.get("name"));
           query.where(builder.equal(root.get("id"), id));
    
           Query<Company> q=session.createQuery(query);
           Company company;
           if (id ==-1) {
        	   company=new Company((long)-1,"NULL");
           }else {
        	   company=q.getSingleResult();
           }
           transaction.commit();
           return company;
        } catch (Exception e) {
           e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
		return null;
	}
    
	public void deleteCompany(Long id) {
   
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			
	        // create update
	        CriteriaDelete<Computer> delete = cb.
	        createCriteriaDelete(Computer.class);
	
	        //delete company
	     
	        CriteriaDelete<Company> deleteCompany = cb.
	        createCriteriaDelete(Company.class);
	        
	        // set the root class
	        Root<Computer> e = delete.from(Computer.class);
	   
	        delete.where(cb.equal(e.get("company"), id));
	
	        Root<Company> eCompany = deleteCompany.from(Company.class);
	 	   
	        deleteCompany.where(cb.equal(eCompany.get("id"), id));
	        // perform update
	        session.createQuery(delete).executeUpdate();
	        session.createQuery(deleteCompany).executeUpdate();
		}catch (Exception e) {
	           e.printStackTrace();
	           if (transaction != null) {
	              transaction.rollback();
	           }
	        }
	}
}