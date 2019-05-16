package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
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
	private static final String SQL_SELECT_ALL_ORDER_BY_NAME_ASC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY name ASC ";
	private static final String SQL_SELECT_ALL_ORDER_BY_NAME_DESC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY name DESC ";
	private static final String SQL_SELECT_ALL_ORDER_BY_INTRO_ASC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY introduced ASC ";
	private static final String SQL_SELECT_ALL_ORDER_BY_INTRO_DESC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY introduced DESC ";
	private static final String SQL_SELECT_ALL_ORDER_BY_DISC_ASC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY discontinued ASC ";
	private static final String SQL_SELECT_ALL_ORDER_BY_DISC_DESC = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY discontinued DESC ";
	private static final String SQL_SELECT_COMPUTER_BY_COMPANY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ? ";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued= ?, company_id = ?  WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	private static final String SQL_MAX_ID ="SELECT MAX(id) AS LastID FROM computer";
	private static final String SQL_FIND_ID ="SELECT id FROM computer WHERE name= ? AND introduced = ? AND discontinued = ? AND company_id = ?";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
	private static final String SQL_DELETE_COMPUTERS_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ?";

	


	private static Computer mapComputer( ResultSet resultSet ) throws SQLException, ParseException {
		DTOComputer dtoComputer = new DTOComputer();
		dtoComputer.setId( Long.toString(resultSet.getLong( "id" )) );
		dtoComputer.setName( resultSet.getString( "name" ) );
		dtoComputer.setIntroduced( resultSet.getString( "introduced" ) );
		dtoComputer.setDiscontinued( resultSet.getString( "discontinued" ) );
		
		Object item = resultSet.getObject("company_id");
		String strValue1 = (item == null ? null : item.toString());

		dtoComputer.setCompanyId(strValue1);
	    return MapperComputer.DTOToModel(dtoComputer);}
	
	
    
    public List<Computer> listComputer(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);

    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_COMPUTERS+pagination.getPagination());
       
        return ComputerRowMapper.mapComputersMapper(rows);
    }
      
        
    
    public List<Computer> listComputerByName(MySQLPage pagination,String name) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_COMPUTER_BY_NAME+pagination.getPagination(),new Object[] { '%'+name+'%' });
        
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    
    public List<Computer> listComputerOrderByNameASC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_NAME_ASC+pagination.getPagination());
    	return ComputerRowMapper.mapComputersMapper(rows);
    }
    public List<Computer> listComputerOrderByNameDESC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_NAME_DESC +pagination.getPagination());
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    
    public List<Computer> listComputerOrderByIntroASC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_INTRO_ASC +pagination.getPagination());
        return ComputerRowMapper.mapComputersMapper(rows);

    }
    
    public List<Computer> listComputerOrderByIntroDESC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_INTRO_DESC +pagination.getPagination());
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    public List<Computer> listComputerOrderByDiscASC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_DISC_ASC +pagination.getPagination());
        return ComputerRowMapper.mapComputersMapper(rows);
    }
    	
    
    public List<Computer> listComputerOrderByDiscDESC(MySQLPage pagination) throws DAOException, ParseException {
    	
    	jdbcTemplate = new JdbcTemplate(dataSource);
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_ORDER_BY_DISC_DESC +pagination.getPagination());
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
	
	public long findId(Computer computer) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    long nb = 0;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_FIND_ID, false, computer.getName(),new Date(computer.getIntroduced().getTime() + 3600*1000), new Date(computer.getDiscontinued().getTime() + 3600*1000),computer.getCompanyId());
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
	            nb = resultSet.getLong("id");
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return nb;
    }

	public void deleteCompany(Long id) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_DELETE_COMPUTERS_BY_COMPANY_ID, true, id);
	        int statut = preparedStatement.executeUpdate();
	        if ( statut == 0 ) {
	            throw new DAOException( "Echec du changement de statut." );
		        }
		    } catch ( SQLException e ) {
		        throw new DAOException( e );
		    } finally {
		        UtilitaireDAO.fermeturesSilencieuses(preparedStatement, connexion );
		    }
	    
	    
	    try {
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_DELETE_COMPANY, true, id);
	        int statut = preparedStatement.executeUpdate();
	        if ( statut == 0 ) {
	            throw new DAOException( "Echec du changement de statut." );
		        }
		    } catch ( SQLException e ) {
		        throw new DAOException( e );
		    } finally {
		        UtilitaireDAO.fermeturesSilencieuses( preparedStatement, connexion );
		    }
		
	}
	
	}
