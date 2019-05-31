package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputer;

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
	public void insert(Computer computer) throws IllegalArgumentException, ParseException{
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
	public void update(Computer computer) throws ParseException {
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
	public Computer find(int id) throws ParseException {
		Computer computer = null;
		try {
			computer =dao.showComputer(id);
		} catch (NullPointerException e) {
			LOGGER.warn("L'id de l'ordinateur cherch� par l'utilisateur ("+id+") n'existe pas dans la base de donn�es "+ e);
		}
		return (computer);
	}
	
	/**
	 * List all the computers with pagination
	 * @return the current page of computer
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<Computer> list(int offset, int limit) throws ParseException{
		
		List<Computer> computers = dao.listComputer(offset, limit);
		
		return computers;
	}
	public List<Computer> list(int offset, int limit,String[] orderBy) throws ParseException{
			
		List<Computer> computers = dao.listComputer(offset,limit,orderBy);
		
		return computers;
	}
	/*
	public List<Computer> listByName(int offset, int limit,String name) throws DAOException, ParseException{
		
		List<Computer> computers = dao.listComputerByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name);
		
		return computers;
	}
	*/
	public List<Computer> listByName(int offset, int limit,String name,String[] orderBy) throws ParseException{
		
		List<Computer> computers = dao.listComputerByName(offset,limit,name,orderBy);
		LOGGER.info("nombre d'orrdi: " + computers.size());
		
		return computers;
	}
	
	public long count() {
        
		return dao.count();
    }

	public long count(String search) {
		
		return dao.count(search);
        
}
}
