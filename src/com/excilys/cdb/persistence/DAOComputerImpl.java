package com.excilys.cdb.persistence;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;

public class DAOComputerImpl implements DAOComputer {
		
	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT_PAR_NAME = "SELECT id, name, introduced, discontinued, companyId FROM Computer WHERE name = ?";

	DAOComputerImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    /* Implémentation de la méthode listCompany() définie dans l'interface DAOCompany */
    @Override
    public DTOComputer listComputer() throws DAOException {
        return null;
    }

	@Override
	public void createComputer(DTOComputer computer) throws IllegalArgumentException,DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComputer(DTOComputer computer) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DTOComputer showComputer(String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}