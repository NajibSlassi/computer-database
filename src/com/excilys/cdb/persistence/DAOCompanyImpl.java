package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.mapper.DTOCompany;

public class DAOCompanyImpl implements DAOCompany {
	private DAOFactory daoFactory;
	
	private static DTOCompany mapCompany( ResultSet resultSet ) throws SQLException {
		DTOCompany company = new DTOCompany();
		company.setId( resultSet.getLong( "id" ) );
		company.setName( resultSet.getString( "name" ) );
	    return company;
	}

	DAOCompanyImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public DTOCompany listCompany() throws DAOException {
        return null;
    }

}