package com.excilys.cdb.persistence;


import java.text.ParseException;

import com.excilys.cdb.model.Computer;

public interface DAOComputer {
	
	void createComputer( Computer computer ) throws DAOException;
	
	void updateComputer( Computer computer ) throws DAOException;

    Computer showComputer( String name ) throws DAOException, ParseException;

	Computer listComputer() throws DAOException;

}
