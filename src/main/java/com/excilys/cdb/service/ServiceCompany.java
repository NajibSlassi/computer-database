package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOCompanyImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.DAOFactory;
import com.excilys.cdb.persistence.MySQLLimit;
import com.excilys.cdb.persistence.MySQLOffset;
import com.excilys.cdb.persistence.MySQLPage;

public class ServiceCompany {
	
	private ServiceCompany() {}
	
	private static ServiceCompany INSTANCE = null;
	
	public static ServiceCompany getInstance()
    {           
        if (INSTANCE == null){   
        	INSTANCE = new ServiceCompany(); 
        }
        return INSTANCE;
    }
	
	DAOCompanyImpl dao = new DAOCompanyImpl(DAOFactory.getInstance());
		
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

}
