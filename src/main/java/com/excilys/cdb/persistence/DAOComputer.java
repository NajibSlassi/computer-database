package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

import ch.qos.logback.classic.Logger;

@Component
@Repository
public class DAOComputer{
		
	public DAOComputer(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(DAOComputer.class);
	
	private final DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM computer";
	private static final String SQL_SELECT_ALL_COMPUTERS = "SELECT id, name, introduced, discontinued, company_id FROM computer ";
	private static final String SQL_SELECT_ALL_SEARCH = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer left join company on (computer.company_id=company.id) WHERE computer.name LIKE ? OR company.name LIKE ? "; 
	private static final String SQL_SELECT_COMPUTER_BY_COMPANY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ? ";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued= ?, company_id = ?  WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	private static final String SQL_MAX_ID ="SELECT MAX(id) AS LastID FROM computer";

	private static final String SQL_COUNT_SEARCH = "SELECT COUNT(id) AS count FROM (SELECT computer.id FROM computer left join company on (computer.company_id=company.id) WHERE computer.name LIKE ? OR company.name LIKE ?) AS derived";
	

    public List<Computer> listComputer(int pageNumber,int pageSize,String[] orderBy) throws DAOException, ParseException {
    	/*
    	jdbcTemplate = new JdbcTemplate(dataSource);

    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPUTERS +orderBy+" "+pagination.getPagination());
        LOGGER.info("Longueure de la liste des computers reçu: "+ Integer.toString(rows.size()));
        return ComputerRowMapper.mapComputersMapper(rows);
        */
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
    
	public List<Computer> listComputer(int pageNumber,int pageSize) throws DAOException, ParseException {
	    	/*
	    	jdbcTemplate = new JdbcTemplate(dataSource);
	
	    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPUTERS+pagination.getPagination());
	        LOGGER.info("Longueure de la liste des computers reçu: "+ Integer.toString(rows.size()));
	        return ComputerRowMapper.mapComputersMapper(rows);
	        */
		
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
  
    public List<Computer> listComputerByName(int pageNumber,int pageSize,String name,String[] orderBy) throws DAOException, ParseException {
    	
    	Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           transaction = session.beginTransaction();

           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Computer> query = builder.createQuery(Computer.class);
           Root<Computer> root = query.from(Computer.class);
           query.multiselect(root.get("name"),root.get("introduced"),root.get("discontinued"),root.get("company"));
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
    /*
    public List<Computer> listComputerByName(MySQLPage pagination,String name) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_SEARCH+pagination.getPagination(),new Object[] { '%'+name+'%','%'+name+'%' });
        LOGGER.info("2 after executing the requests :" + rows.size()+" lignes trouvés ");
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    */
	public void createComputer(Computer computer) throws IllegalArgumentException,DAOException {
		//Problem with time zone
		computer.setIntroduced(new Date(computer.getIntroduced().getTime() + 3600*1000));
		computer.setDiscontinued(new Date(computer.getDiscontinued().getTime() + 3600*1000));
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
      
        //Save the employee in database
        session.save(computer);
 
        //Commit the transaction
        session.getTransaction().commit();
        HibernateUtil.shutdown();
    }
	

	public void updateComputer(Computer computer) throws DAOException {
		
		//Problem with time zone
		computer.setIntroduced(new Date(computer.getIntroduced().getTime() + 3600*1000));
		computer.setDiscontinued(new Date(computer.getDiscontinued().getTime() + 3600*1000));
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
      
        //Save the employee in database
        LOGGER.info("Updating ");
        session.update(computer);
 
        //Commit the transaction
        session.getTransaction().commit();
        HibernateUtil.shutdown();
		
	}

	@SuppressWarnings("unchecked")
	public Computer showComputer(int id) throws DAOException, ParseException {
		jdbcTemplate = new JdbcTemplate(dataSource);
        Computer computer = (Computer) jdbcTemplate.queryForObject(
        		SQL_SELECT_PAR_ID, new Object[] { id }, new ComputerRowMapper());
        
	    return computer;
	}
	public List<Computer> showComputerByCompanyId(Long long1) throws DAOException, ParseException {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_COMPUTER_BY_COMPANY_ID,new Object[] { long1 });
        return ComputerRowMapper.mapComputersMapper(rows);
		
	}
	@Transactional
	public void deleteComputer(Long id) {
		
		jdbcTemplate = new JdbcTemplate(dataSource); 
        jdbcTemplate.update( SQL_DELETE, new Object[] { id });
        
	}

	public long count() {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
        Long id = jdbcTemplate.queryForObject(
        		SQL_COUNT, Long.class);
        
	    return id;
		
    }
	
	public long maxId() {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
        Long id = jdbcTemplate.queryForObject(
        		SQL_MAX_ID, Long.class);
        
	    return id;
	}
	public long count(String search) {
		jdbcTemplate = new JdbcTemplate(dataSource);
        Long id = jdbcTemplate.queryForObject(
        		SQL_COUNT_SEARCH,new Object[] { "%"+search+"%","%"+search+"%" }, Long.class);
        LOGGER.info("number of computers searched: "+id);
	    return id;
	}
	
	}
