package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.DAOFactory;
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;
import com.excilys.cdb.vue.CLI;

import ch.qos.logback.classic.Logger;

public class ServiceComputer {
	
	private ServiceComputer() {}
	
	
		
	private static ServiceComputer INSTANCE = null;
	
	
	
	public static ServiceComputer getInstance()
    {           
        if (INSTANCE == null){   
        	INSTANCE = new ServiceComputer(); 
        }
        return INSTANCE;
    }
	
	DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
	
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(ServiceComputer.class);

	/**
	 * Insert a new computer
	 * @param computer
	 * @throws ParseException 
	 * @throws DAOException 
	 * @throws IllegalArgumentException 
	 */
	public void insert(DTOComputer computer) throws IllegalArgumentException, DAOException, ParseException{
		dao.createComputer(MapperComputer.DTOToModel(computer));
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
	public void update(DTOComputer computer) throws DAOException, ParseException {
		dao.updateComputer(MapperComputer.DTOToModel(computer));
	}
	
	/**
	 * Find a computer by is id
	 * @param id of the computer
	 * @return the computer if found
	 * @throws ParseException 
	 * @throws DAOException 
	 * @throws ComputerNotFoundException if not found
	 */
	public DTOComputer find(int id) throws DAOException, ParseException {
		Computer computer = null;
		DTOComputer dtoComputer = null;
		computer =dao.showComputer(id);
		try {
			dtoComputer = MapperComputer.modelToDTO(computer);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("L'id de l'ordinateur cherch� par l'utilisateur ("+id+") n'existe pas dans la base de donn�es "+ e);
		}
		return (dtoComputer);
	}
	
	/**
	 * List all the computers with pagination
	 * @return the current page of computer
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<DTOComputer> list(int offset, int limit) throws DAOException, ParseException{
		List<DTOComputer> dtoComputers = new ArrayList<DTOComputer>();
		List<Computer> computers = dao.listComputer(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit));
		for(Computer computer:computers) {
			dtoComputers.add(MapperComputer.modelToDTO(computer));
		}
		return dtoComputers;
	}

}
