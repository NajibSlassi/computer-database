package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOCompanyImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;


@Component
public class ServiceCompany {
	
	
	
	public ServiceCompany(DAOCompanyImpl dao) {
		super();
		this.dao = dao;
	}
	private final DAOCompanyImpl dao;
		
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
		
		return companies;
	}
	
	public Company find(int id) throws DAOException, ParseException {
		return (dao.showCompany(id));
	}
	public List<Company> listByName(int offset, int limit,String name) throws DAOException, ParseException{
			
			List<Company> companies = dao.listCompanyByName(new MySQLPage(new MySQLLimit(new MySQLOffset((offset-1)*limit), limit),(offset-1)*limit),name);
			
			return companies;
		}
	
	
	

}
