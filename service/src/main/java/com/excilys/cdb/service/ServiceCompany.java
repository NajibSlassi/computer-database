package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOCompany;

@Component()
public class ServiceCompany {
	
	
	
	public ServiceCompany(DAOCompany dao) {
		super();
		this.dao = dao;
	}
	private final DAOCompany dao;
	private static Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);
		
	/**
	 * List all the company with pagination
	 * @param j 
	 * @param i 
	 * @return the page of companies
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<Company> list(int pageNumber,int pageSize) throws ParseException{
		
		List<Company> companies = dao.listCompany(pageNumber,pageSize);
		LOGGER.info(companies.size() +" companies found");
		
		return companies;
	}
	
	public Company find(int id) throws ParseException {
		return (dao.showCompany(id));
	}
	
	public void deleteCompany(Long id) {
		dao.deleteCompany(id);
		
	}
	
}
