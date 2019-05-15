package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;

@Component
public class DAOCompany {
	
	public DAOCompany(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	private static final String SQL_SELECT_ALL_COMPANY = "SELECT id, name FROM company ";
	private static final String SQL_FIND_COMPANY_BY_ID ="SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_COMPANY_BY_NAME ="SELECT id, name FROM company WHERE name LIKE ? ";
	
	private final DataSource dataSource;
	
	private static Company mapCompany( ResultSet resultSet ) throws SQLException, ParseException {
		DTOCompany dtoCompany = new DTOCompany();
		
		dtoCompany.setId( Long.toString(resultSet.getLong( "id" )) );
		dtoCompany.setName( resultSet.getString( "name" ) );
		
	    return MapperCompany.DTOToModel(dtoCompany);}

	

    /* Impl�mentation de la méthode listCompany() définie dans l'interface DAOCompany */
	/**
	 * 
	 * @param pagination
	 * @return
	 * @throws DAOException
	 * @throws ParseException
	 */
    public List<Company> listCompany(Page pagination) throws DAOException, ParseException {
    	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Company company = null;
	    List<Company> listCompany= new LinkedList<Company>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_ALL_COMPANY+pagination.getPagination(), false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
	            company = mapCompany( resultSet );
	            listCompany.add(company);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return listCompany;
    }
    
    public Company showCompany(int id) throws DAOException, ParseException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Company company = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = dataSource.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_FIND_COMPANY_BY_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
	            company = mapCompany( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return company;
	}
    
    public List<Company> listCompanyByName(Page pagination,String name) throws DAOException, ParseException {
    	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Company company = null;
	    List<Company> listCompanies= new LinkedList<Company>();

	    try {
	       
	    	connexion = dataSource.getConnection();
	    	preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_FIND_COMPANY_BY_NAME+pagination.getPagination(), false, '%'+name+'%');
	        resultSet = preparedStatement.executeQuery();
	       
	        while ( resultSet.next() ) {
	            company = mapCompany( resultSet );
	            listCompanies.add(company);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        UtilitaireDAO.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return listCompanies;
    }

}