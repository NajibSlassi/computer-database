package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer;

public class DAOComputerImpl implements DAOComputer {
		
	private static DAOFactory daoFactory;
	
	private static final String SQL_SELECT_PAR_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?, ?, ?, ?)";


	private static Computer mapComputer( ResultSet resultSet ) throws SQLException, ParseException {
		DTOComputer dtoComputer = new DTOComputer();
		
		
		
		dtoComputer.setId( Long.toString(resultSet.getLong( "id" )) );
		dtoComputer.setName( resultSet.getString( "name" ) );
		dtoComputer.setIntroduced( resultSet.getString( "introduced" ) );
		dtoComputer.setDiscontinued( resultSet.getString( "discontinued" ) );
		
		Object item = resultSet.getObject("company_id");
		String strValue1 = (item == null ? null : item.toString());

		dtoComputer.setCompanyId(strValue1);
	    return Mapper.mapComputer(dtoComputer);}
	    
	public DAOComputerImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public Computer listComputer() throws DAOException {
        return null;
    }

	public void createComputer(Computer computer) throws IllegalArgumentException,DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_INSERT, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création du computer, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            computer.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	@Override
	public void updateComputer(Computer computer) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Computer showComputer(String name) throws DAOException, ParseException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_NAME, false, name );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
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
	
}