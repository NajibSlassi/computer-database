package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.DAOFactory;

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
		return MapperComputer.modelToDTO(dao.showComputer(id));
	}
	
	/**
	 * List all the computers with pagination
	 * @return the current page of computer
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<DTOComputer> list() throws DAOException, ParseException{
		List<DTOComputer> dtoComputers = new ArrayList<DTOComputer>();
		List<Computer> computers = dao.listComputer();
		for(Computer computer:computers) {
			dtoComputers.add(MapperComputer.modelToDTO(computer));
		}
		return dtoComputers;
	}

}
