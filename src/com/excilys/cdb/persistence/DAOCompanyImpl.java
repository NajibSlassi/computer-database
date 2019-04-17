package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DAOCompanyImpl implements DAOCompany {
	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT_ALL_COMPANY = "SELECT id, name FROM company";
	
	private static Company mapCompany( ResultSet resultSet ) throws SQLException, ParseException {
		DTOCompany dtoCompany = new DTOCompany();
		
		
		
		dtoCompany.setId( Long.toString(resultSet.getLong( "id" )) );
		dtoCompany.setName( resultSet.getString( "name" ) );
		
	    return Mapper.mapCompany(dtoCompany);}

	public DAOCompanyImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public List listCompany() throws DAOException, ParseException {
    	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Company company = null;
	    List<Company> listCompany= new LinkedList<Company>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = UtilitaireDAO.initialisationRequetePreparee( connexion, SQL_SELECT_ALL_COMPANY, false);
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

}