package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public class DAOCompanyImpl implements DAOCompany {
	private DAOFactory daoFactory;
	
	private static Company mapCompany( ResultSet resultSet ) throws SQLException {
		Company company = new Company();
		company.setId( resultSet.getLong( "id" ) );
		company.setName( resultSet.getString( "name" ) );
	    return company;
	}

	DAOCompanyImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public Company listCompany() throws DAOException {
        return null;
    }

}