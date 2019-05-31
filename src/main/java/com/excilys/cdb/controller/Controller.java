package com.excilys.cdb.controller;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;

@Component()
public class Controller {
	
	private ServiceCompany serviceCompany;
	private ServiceComputer serviceComputer;
	private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	public Controller(ServiceComputer serviceComputer, ServiceCompany serviceCompany) {
		super();
		this.serviceComputer = serviceComputer;
		this.serviceCompany=serviceCompany;
	}
	
	public List<DTOComputer> listComputers(int readPage, int readLimit) throws ParseException {
		List<DTOComputer> l =new LinkedList<DTOComputer>();
		LOGGER.info("readPage :"+ readPage+" readLimit: "+readLimit);
		List<Computer> computers = serviceComputer.list(readPage,readLimit);
		for (Computer x: computers) {
			l.add(MapperComputer.modelToDTO(x));
		}
		return l;
	}
	
	public List<DTOCompany> listCompanies(int readPage, int readLimit) throws ParseException {
		List<DTOCompany> companies =new LinkedList<DTOCompany>();
		for (Company x:serviceCompany.list(readPage,readLimit)) {
			companies.add(MapperCompany.modelToDTO(x));
		}
		return companies;
	}
	public DTOComputer showComputer(int id) throws ParseException {
		return MapperComputer.modelToDTO(serviceComputer.find(id));
	}
	public void addComputer(DTOComputer dtoComputer) throws IllegalArgumentException, ParseException {
		if(new Validator().validateDTOComputer(dtoComputer).size()==0) {
			Computer computer = MapperComputer.DTOToModel(dtoComputer);
			serviceComputer.insert(computer);
			
		}else LOGGER.warn("Les donn�es entr�es sont incorrectes");
	}
	public void updateComputer(DTOComputer dtoComputer) throws IllegalArgumentException, ParseException {
		
		if(new Validator().validateDTOComputer(dtoComputer).size()==0) {
			Computer computer = MapperComputer.DTOToModel(dtoComputer);
			serviceComputer.update(computer);
		}
	}
	
	public void deleteComputer(long id) {
		serviceComputer.delete(id);
	}
	/*
	public void deleteCompany(long id) {
		serviceCompany.deleteCompany(id);
	}
	*/
	
	
	

}
