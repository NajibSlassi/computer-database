package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class DAOCompany {
	
	public DAOCompany(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	private static final String SQL_SELECT_ALL_COMPANY = "SELECT id, name FROM company ";
	private static final String SQL_FIND_COMPANY_BY_ID ="SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_COMPANY_BY_NAME ="SELECT id, name FROM company WHERE name LIKE ? ";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	private static final String SQL_DELETE_COMPUTERS_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";
	
	private final DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	

    /* Impl�mentation de la méthode listCompany() définie dans l'interface DAOCompany */
	/**
	 * 
	 * @param pagination
	 * @return
	 * @throws DAOException
	 * @throws ParseException
	 */
    public List<Company> listCompany(int pageNumber,int pageSize) throws DAOException, ParseException {
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
    
    
    @SuppressWarnings("unchecked")
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
            
           Company company=q.getSingleResult();
           
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
    
    public List<Company> listCompanyByName(MySQLPage pagination,String name) throws DAOException, ParseException {
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_COMPANY_BY_NAME+pagination.getPagination(),new Object[] { '%'+name+'%' });
        
        return CompanyRowMapper.mapCompaniesMapper(rows);
    }
    
    @Transactional
	public void deleteCompany(Long id) {
    	jdbcTemplate = new JdbcTemplate(dataSource); 
        jdbcTemplate.update( SQL_DELETE_COMPUTERS_BY_COMPANY_ID, new Object[] { id });
        jdbcTemplate.update( SQL_DELETE_COMPANY, new Object[] { id });
    }
}