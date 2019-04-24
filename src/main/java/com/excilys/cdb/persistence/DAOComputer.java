package com.excilys.cdb.persistence;


import java.text.ParseException;
import java.util.List;

import com.excilys.cdb.model.Computer;

public interface DAOComputer {
	
	void createComputer( Computer computer ) throws DAOException;

    Computer showComputer( int id ) throws DAOException, ParseException;

	List<Computer> listComputer(Page pagination) throws DAOException, ParseException;

	void updateComputer( Computer computer) throws DAOException;
	void deleteComputer(Long id) ;

}
