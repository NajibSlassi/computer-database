package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOCompany;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;


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
	public List<Company> list(int page, int limit) throws DAOException, ParseException{
		
		List<Company> companies = dao.listCompany(new MySQLPage(new MySQLLimit(new MySQLOffset((page-1)*limit), limit),(page-1)*limit));
		LOGGER.info(companies.size() +" companies found");
		
		return companies;
	}
	
	public Company find(int id) throws DAOException, ParseException {
		return (dao.showCompany(id));
	}
	public List<Company> listByName(int offset, int limit,String name) throws DAOException, ParseException{
			
			List<Company> companies = dao.listCompanyByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name);
			
			return companies;
		}
	
	public void deleteCompany(Long id) {
		dao.deleteCompany(id);
		
	}
}
