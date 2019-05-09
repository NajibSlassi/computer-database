package com.excilys.cdb.controller;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;
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
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);
			     
	
	/**
	 * reçoit le choix de l'utilisateur et communique avec le service adéquat pour répondre au besoin
	 * @param choix de l'utilisateur dans le CLI
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public void sendToService(int choice) throws DAOException, ParseException {
		switch(choice) {
		case 0:
			
			cli.quit();
			break;
		case 1:
			int i = cli.readPage();
			int j = cli.readLimit();
			List<DTOComputer> l =new LinkedList<DTOComputer>();
			for (DTOComputer x:serviceComputer.list(i,j)) {
				l.add(x);
			}
			cli.showComputers(l);
			break;
		case 2:
			int p = cli.readPage();
			int q = cli.readLimit();
			List<Company> c =new LinkedList<Company>();
			for (DTOCompany x:serviceCompany.list(p,q)) {
				c.add(MapperCompany.DTOToModel(x));
			}
			cli.showCompanies(c);
			break;
		case 3:
			int consulter =cli.readInt("Entrez l'id de l'ordinateur à consulter");
			LOGGER.info("id de l'ordinateur choisi par l'utilisateur: "+consulter);
			cli.showComputerDetails(serviceComputer.find(consulter));
			break;
		case 4:
			DTOComputer computer = cli.createComputer();
			if(new Validator().validateDTOComputer(computer).size()==0) {
				serviceComputer.insert(computer);
				
			}else LOGGER.warn("Les données entrées sont incorrectes");
			break;
			
		case 5:
			DTOComputer ucomputer= cli.updateComputer();
			if(new Validator().validateDTOComputer(ucomputer).size()==0) {
				serviceComputer.update(ucomputer);
			}
			break;
		case 6:
			serviceComputer.delete(cli.deleteComputer());
			break;
		case 7:
			serviceComputer.deleteCompany(cli.deletCompany());
			break;
		}
	}
	
	

}
