package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public interface DAOCompany {

    Company listCompany() throws DAOException;

}