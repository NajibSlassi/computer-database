package com.excilys.cdb.persistence;

import com.excilys.cdb.mapper.DTOCompany;

public interface DAOCompany {

    DTOCompany listCompany() throws DAOException;

}