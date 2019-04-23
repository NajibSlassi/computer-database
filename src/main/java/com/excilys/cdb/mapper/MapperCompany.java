package com.excilys.cdb.mapper;

import com.excilys.cdb.model.Company;

public class MapperCompany {
	
	public static DTOCompany modelToDTO(Company company) {
		return new DTOCompany(Long.toString(company.getId()),company.getName());
	}
	
	
	public static Company DTOToModel(DTOCompany dtoCompany) {
		return new Company(Long.parseLong(dtoCompany.getId()),dtoCompany.getName());
	}

}
