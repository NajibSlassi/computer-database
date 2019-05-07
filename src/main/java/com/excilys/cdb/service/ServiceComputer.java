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
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;


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
	
	DAOComputerImpl dao = new DAOComputerImpl();
	
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
		LOGGER.info("ordinateur créé : " +MapperComputer.DTOToModel(computer).toString());
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
			LOGGER.warn("L'id de l'ordinateur cherché par l'utilisateur ("+id+") n'existe pas dans la base de données "+ e);
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
	
	public List<DTOComputer> listOrderASC(int offset, int limit) throws DAOException, ParseException{
		List<DTOComputer> dtoComputers = new ArrayList<DTOComputer>();
		List<Computer> computers = dao.listComputerOrdASC(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit));
		for(Computer computer:computers) {
			dtoComputers.add(MapperComputer.modelToDTO(computer));
		}
		return dtoComputers;
	}
	public List<DTOComputer> listOrderDESC(int offset, int limit) throws DAOException, ParseException{
		List<DTOComputer> dtoComputers = new ArrayList<DTOComputer>();
		List<Computer> computers = dao.listComputerOrdDESC(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit));
		LOGGER.info("i'm also here");
		for(Computer computer:computers) {
			dtoComputers.add(MapperComputer.modelToDTO(computer));
		}
		return dtoComputers;
	}
	
	public List<DTOComputer> listByName(int offset, int limit,String name) throws DAOException, ParseException{
		List<DTOComputer> dtoComputers = new ArrayList<DTOComputer>();
		List<Computer> computers = dao.listComputerByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name);
		for(Computer computer:computers) {
			dtoComputers.add(MapperComputer.modelToDTO(computer));
		}
		return dtoComputers;
	}
	
	public long count() {
        try {
            return dao.count();
        } catch (DAOException e) {
            LOGGER.warn("count()", e);
        }
        return 0;
    }

	public long findId(DTOComputer dtoComputer) {
        try {
        	LOGGER.info(dtoComputer.toString());
            return dao.findId(MapperComputer.DTOToModel(dtoComputer));
        } catch (DAOException | ParseException e) {
            LOGGER.warn("une erreur est survenu lors de la recherche du computer", e);
        }
        return -1;
    }

}
