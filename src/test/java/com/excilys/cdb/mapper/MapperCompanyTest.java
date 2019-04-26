package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.cdb.model.Company;

public class MapperCompanyTest {

	DTOCompany dtoCompany=new DTOCompany("1","Excilys");
	Company company=new Company(Long.valueOf(1),"Excilys");
	@Test
	public void testModelToDTO() {
		
		assertEquals("Les informations sont incorrectes", dtoCompany, MapperCompany.modelToDTO(company));
		
	}

	@Test
	public void testDTOToModel() {
		assertEquals("Les informations sont incorrectes", company, MapperCompany.DTOToModel(dtoCompany));
	}

}
