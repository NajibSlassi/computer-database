package com.excilys.cdb.mapper;

import com.excilys.cdb.model.Company;

public class MapperCompany {
	
	/**
	 * Converti un model en DTO
	 * @param company
	 * @return un objet DTOCompany
	 */
	public static DTOCompany modelToDTO(Company company) {
		return new DTOCompany(Long.toString(company.getId()),company.getName());
	}
	
	/**
	 * converti un DTO en model
	 * @param dtoCompany
	 * @return un objet Company
	 */
	public static Company DTOToModel(DTOCompany dtoCompany) {
		
		return new Company(Long.parseLong(dtoCompany.getId()),dtoCompany.getName());
	}

}
