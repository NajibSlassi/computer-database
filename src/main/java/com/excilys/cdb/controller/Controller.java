package com.excilys.cdb.controller;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
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
	 * re�oit le choix de l'utilisateur et communique avec le service ad�quat pour r�pondre au besoin
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
			List<Computer> computers = serviceComputer.list(i,j);
			for (Computer x: computers) {
				l.add(MapperComputer.modelToDTO(x));
			}
			cli.showComputers(l);
			break;
		case 2:
			int p = cli.readPage();
			int q = cli.readLimit();
			List<DTOCompany> c =new LinkedList<DTOCompany>();
			for (Company x:serviceCompany.list(p,q)) {
				c.add(MapperCompany.modelToDTO(x));
			}
			cli.showCompanies(c);
			break;
		case 3:
			int consulter =cli.readInt("Entrez l'id de l'ordinateur � consulter");
			LOGGER.info("id de l'ordinateur choisi par l'utilisateur: "+consulter);
			cli.showComputerDetails(MapperComputer.modelToDTO(serviceComputer.find(consulter)));
			break;
		case 4:
			DTOComputer dtoComputer = cli.createComputer();
			if(new Validator().validateDTOComputer(dtoComputer).size()==0) {
				Computer computer = MapperComputer.DTOToModel(dtoComputer);
				serviceComputer.insert(computer);
				
			}else LOGGER.warn("Les donn�es entr�es sont incorrectes");
			break;
			
		case 5:
			DTOComputer dtoUComputer= cli.updateComputer();
			if(new Validator().validateDTOComputer(dtoUComputer).size()==0) {
				Computer computer = MapperComputer.DTOToModel(dtoUComputer);
				serviceComputer.update(computer);
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
