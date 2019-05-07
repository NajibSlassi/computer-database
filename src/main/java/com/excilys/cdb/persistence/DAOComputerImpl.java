package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceComputer;

import ch.qos.logback.classic.Logger;


public class DAOComputerImpl{
		
	
	private static DataSource dataSource;
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(DAOComputerImpl.class);
	
	private static final String SQL_COUNT = "SELECT COUNT(id) AS count FROM computer";
	private static final String SQL_SELECT_ALL_COMPUTERS = "SELECT id, name, introduced, discontinued, company_id FROM computer ";
	private static final String SQL_SELECT_COMPUTER_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ? ";
	private static final String SQL_SELECT_PAR_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued= ?, company_id = ?  WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	private static final String SQL_MAX_ID ="SELECT MAX(id) AS LastID FROM computer";
	private static final String SQL_FIND_ID ="SELECT id FROM computer WHERE name= ? AND introduced = ? AND discontinued = ? AND company_id = ?";
	


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
	    
    /* ImplÈmentation de la mÈthode listCompany() dÈfinie dans l'interface DAOCompany */
    public List<Computer> listComputer(Page pagination) throws DAOException, ParseException {
    	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;
	    List<Computer> listComputers= new LinkedList<Computer>();

	    try {
	        /* RÈcupÈration d'une connexion depuis la Factory */
	        //connexion = daoFactory.getConnection();
	    	connexion = dataSource.getConnection();
	    	preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_ALL_COMPUTERS+pagination.getPagination(), false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnÈes de l'Èventuel ResulSet retournÈ */
	        while ( resultSet.next() ) {
	            computer = mapComputer( resultSet );
	            listComputers.add(computer);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return listComputers;
    }
    
    public List<Computer> listComputerByName(Page pagination,String name) throws DAOException, ParseException {
    	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;
	    List<Computer> listComputers= new LinkedList<Computer>();

	    try {
	        /* RÈcupÈration d'une connexion depuis la Factory */
	        //connexion = daoFactory.getConnection();
	    	connexion = dataSource.getConnection();
	    	preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_COMPUTER_BY_NAME+pagination.getPagination(), false, name);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donnÈes de l'Èventuel ResulSet retournÈ */
	        while ( resultSet.next() ) {
	            computer = mapComputer( resultSet );
	            listComputers.add(computer);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return listComputers;
    }

	public void createComputer(Computer computer) throws IllegalArgumentException,DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* R√©cup√©ration d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_INSERT, true, computer.getName(), new Date(computer.getIntroduced().getTime() + 3600*1000), new Date(computer.getDiscontinued().getTime() + 3600*1000), computer.getCompanyId() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retournÈ par la requËte d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Echec de la crÈation du computer, aucune ligne ajoutÈe dans la table." );
	        }
	        /* R√©cup√©ration de l'id auto-g√©n√©r√© par la requ√™te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri√©t√© id du bean Utilisateur avec sa valeur */
	            computer.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "√âchec de la cr√©ation de l'ordinateur en base, aucun ID auto-gÈnÈrÈ retournÈ." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	public void updateComputer(Computer computer) throws DAOException {
		
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_UPDATE, true, computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),computer.getCompanyId(),computer.getId());
	        int statut = preparedStatement.executeUpdate();
	        LOGGER.info("Computer mis ‡ jour au niveau de la DAO: "+computer.toString());
	        if ( statut == 0 ) {
	            throw new DAOException( "Èchec du changement de statut." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( preparedStatement, connexion );
	    }
		
	}

	public Computer showComputer(int id) throws DAOException, ParseException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;

	    try {
	        /* R√©cup√©ration d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn√©es de l'√©ventuel ResulSet retourn√© */
	        if ( resultSet.next() ) {
	            computer = mapComputer( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return computer;
	}

	public void deleteComputer(Long id) {
		// TODO Auto-generated method stub
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_DELETE, true, id);
	        int statut = preparedStatement.executeUpdate();
	        if ( statut == 0 ) {
	            throw new DAOException( "√âchec du changement de statut." );
		        }
		    } catch ( SQLException e ) {
		        throw new DAOException( e );
		    } finally {
		        UtilitaireDAO.fermeturesSilencieuses( preparedStatement, connexion );
		    }
	}
	public long count() {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    long nb = 0;

	    try {
	        /* R√©cup√©ration d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_COUNT, false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn√©es de l'√©ventuel ResulSet retourn√© */
	        while ( resultSet.next() ) {
	            nb = resultSet.getLong("count");
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return nb;
    }
	public long maxId() {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    long nb = 0;

	    try {
	        /* R√©cup√©ration d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_MAX_ID, false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn√©es de l'√©ventuel ResulSet retourn√© */
	        while ( resultSet.next() ) {
	            nb = resultSet.getLong("LastID");
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return nb;
	}
	public long findId(Computer computer) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    long nb = 0;

	    try {
	        /* R√©cup√©ration d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_FIND_ID, false, computer.getName(),new Date(computer.getIntroduced().getTime() + 3600*1000), new Date(computer.getDiscontinued().getTime() + 3600*1000),computer.getCompanyId());
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn√©es de l'√©ventuel ResulSet retourn√© */
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
}