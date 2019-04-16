package com.excilys.cdb.persistence;

import com.excilys.cdb.mapper.DTOCompany;

public class DAOCompanyImpl implements DAOCompany {
	private DAOFactory daoFactory;

	DAOCompanyImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public DTOCompany listCompany() throws DAOException {
        return null;
    }

}