package com.excilys.cdb.controller;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.vue.CLI;

public class Controller {
	
	private Controller() {}
	
	private static Controller INSTANCE = null;
	
	public static Controller getInstance()
    {           
        if (INSTANCE == null){   
        	INSTANCE = new Controller(); 
        }
        return INSTANCE;
    }
	
	private ServiceCompany serviceCompany = ServiceCompany.getInstance();
	private ServiceComputer serviceComputer = ServiceComputer.getInstance();
	CLI cli = CLI.getInstance();
	
	/**
	 * Send the choice of the user to the dedicated service
	 * @param choice of the user
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public void sendToService(int choice) throws DAOException, ParseException {
		
		switch(choice) {
		case 0:
			cli.quit();
			break;
		case 1:
			List<Computer> l =new LinkedList<Computer>();
			for (DTOComputer x:serviceComputer.list()) {
				l.add(MapperComputer.DTOToModel(x));
			}
			cli.showComputers(l);
			break;
		case 2:
			List<Company> c =new LinkedList<Company>();
			for (DTOCompany x:serviceCompany.list()) {
				c.add(MapperCompany.DTOToModel(x));
			}
			cli.showCompanies(c);
			break;
		case 3:
			cli.showComputerDetails(MapperComputer.DTOToModel(serviceComputer.find(cli.readInt())));
			break;
		case 4:
			serviceComputer.insert(cli.createComputer());
			break;
		case 5:
			serviceComputer.update(cli.updateComputer());
			break;
		case 6:
			serviceComputer.delete(cli.deleteComputer());
			break;
		}
	}
	
	

}
