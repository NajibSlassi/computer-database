package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Computer;

import ch.qos.logback.classic.Logger;

@Component
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
	private static final String SQL_SELECT_COMPUTER_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE ? ";
	private static final String SQL_SELECT_COMPUTER_BY_COMPANY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ? ";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued= ?, company_id = ?  WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	private static final String SQL_MAX_ID ="SELECT MAX(id) AS LastID FROM computer";
	

    public List<Computer> listComputer(MySQLPage pagination,String orderBy) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);

    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPUTERS +orderBy+" "+pagination.getPagination());
        LOGGER.info("Longueure de la liste des computers reçu: "+ Integer.toString(rows.size()));
        return ComputerRowMapper.mapComputersMapper(rows);
    }
	public List<Computer> listComputer(MySQLPage pagination) throws DAOException, ParseException {
	    	
	    	jdbcTemplate = new JdbcTemplate(dataSource);
	
	    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPUTERS+pagination.getPagination());
	        LOGGER.info("Longueure de la liste des computers reçu: "+ Integer.toString(rows.size()));
	        return ComputerRowMapper.mapComputersMapper(rows);
	    }
  
    public List<Computer> listComputerByName(MySQLPage pagination,String name,String orderBy) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_COMPUTER_BY_NAME+orderBy+" "+pagination.getPagination(),new Object[] { '%'+name+'%' });
        
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    public List<Computer> listComputerByName(MySQLPage pagination,String name) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_COMPUTER_BY_NAME+pagination.getPagination(),new Object[] { '%'+name+'%' });
        
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    
	public void createComputer(Computer computer) throws IllegalArgumentException,DAOException {

	        jdbcTemplate = new JdbcTemplate(dataSource);
	        jdbcTemplate.update(SQL_INSERT, new Object[] { computer.getName(), new Date(computer.getIntroduced().getTime() + 3600*1000), new Date(computer.getDiscontinued().getTime() + 3600*1000), computer.getCompanyId()  
	        });
	}

	public void updateComputer(Computer computer) throws DAOException {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		  
        jdbcTemplate.update(SQL_UPDATE, new Object[] { computer.getName(),new Date(computer.getIntroduced().getTime() + 3600*1000), 
        		new Date(computer.getDiscontinued().getTime() + 3600*1000),computer.getCompanyId(),computer.getId() 
        });
		
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
	
	}
