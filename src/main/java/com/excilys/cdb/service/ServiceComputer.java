package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;


import ch.qos.logback.classic.Logger;

@Component()
public class ServiceComputer {
	
	
	
	public ServiceComputer(DAOComputer dao) {
		super();
		this.dao = dao;
	}

	private final DAOComputer dao;
	
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(ServiceComputer.class);

	/**
	 * Insert a new computer
	 * @param computer
	 * @throws ParseException 
	 * @throws DAOException 
	 * @throws IllegalArgumentException 
	 */
	public void insert(Computer computer) throws IllegalArgumentException, DAOException, ParseException{
		dao.createComputer(computer);
		LOGGER.info("ordinateur cree : " +computer.toString());
	}
	
	/**
	 * Delete a computer by is id
	 * @param id of the computer
	 */
	public void delete(Long id){
		dao.deleteComputer(id);
	}
	
	/**
	 * Update a computer
	 * @param id of the computer to update
	 * @param computer the new computer
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public void update(Computer computer) throws DAOException, ParseException {
		dao.updateComputer(computer);
	}
	
	/**
	 * Find a computer by is id
	 * @param id of the computer
	 * @return the computer if found
	 * @throws ParseException 
	 * @throws DAOException 
	 * @throws ComputerNotFoundException if not found
	 */
	public Computer find(int id) throws DAOException, ParseException {
		Computer computer = null;
		try {
			computer =dao.showComputer(id);
		} catch (NullPointerException e) {
			LOGGER.warn("L'id de l'ordinateur cherch� par l'utilisateur ("+id+") n'existe pas dans la base de donn�es "+ e);
		}
		return (computer);
	}
	
	public List<Computer> findComputerByCompanyId(Long long1) throws DAOException, ParseException {
		List<Computer> computers = null;
		try {
			computers =dao.showComputerByCompanyId(long1);
		} catch (NullPointerException e) {
			LOGGER.warn("L'id de l'ordinateur cherch� par l'utilisateur ("+long1+") n'existe pas dans la base de donn�es "+ e);
		}
		return (computers);
	}
	
	/**
	 * List all the computers with pagination
	 * @return the current page of computer
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<Computer> list(int offset, int limit) throws DAOException, ParseException{
		
		List<Computer> computers = dao.listComputer(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit));
		
		return computers;
	}
	public List<Computer> list(int offset, int limit,String orderBy) throws DAOException, ParseException{
			
		List<Computer> computers = dao.listComputer(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),orderBy);
		
		return computers;
	}
	
	public List<Computer> listByName(int offset, int limit,String name) throws DAOException, ParseException{
		
		List<Computer> computers = dao.listComputerByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name);
		
		return computers;
	}
	public List<Computer> listByName(int offset, int limit,String name,String orderBy) throws DAOException, ParseException{
		
		List<Computer> computers = dao.listComputerByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name,orderBy);
		
		return computers;
	}
	
	public long count() {
        try {
            return dao.count();
        } catch (DAOException e) {
            LOGGER.warn("count()", e);
        }
        return 0;
    }
}
