package com.excilys.cdb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOCompanyImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.DAOFactory;

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
	 * @return the page of companies
	 * @throws ParseException 
	 * @throws DAOException 
	 */
	public List<DTOCompany> list() throws DAOException, ParseException{
		List<DTOCompany> dtoCompanies = new ArrayList<DTOCompany>();
		List<Company> companies = dao.listCompany();
		for(Company company:companies) {
			dtoCompanies.add(MapperCompany.modelToDTO(company));
		}
		
		return dtoCompanies;
	}

}
