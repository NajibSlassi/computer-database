package com.excilys.cdb.persistence;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;

public interface DAOComputer {
	
	void createComputer( DTOComputer computer ) throws DAOException;
	
	void updateComputer( DTOComputer computer ) throws DAOException;

    DTOComputer showComputer( String name ) throws DAOException;

	DTOComputer listComputer() throws DAOException;

}
