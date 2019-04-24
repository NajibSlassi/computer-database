package com.excilys.cdb.persistence;

import java.text.ParseException;
import java.util.List;

import com.excilys.cdb.model.Company;

public interface DAOCompany {

	List<Company> listCompany(Page pagination) throws DAOException, ParseException;

}