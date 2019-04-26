package com.excilys.cdb.mapper;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class MapperComputerTest {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DTOComputer dtoComputer=new DTOComputer("1","Excilys","2012-11-11","2012-12-11","2");
	Computer computer=new Computer();
	
	@Test
	public void testModelToDTO() {
		
		try {
			computer = new Computer(Long.valueOf(1),"Excilys",dateFormat.parse("2012-11-11"),dateFormat.parse("2012-12-11"),Long.valueOf(2));
		} catch (ParseException e) {
			
		}
		assertEquals("Les informations sont incorrectes", dtoComputer, MapperComputer.modelToDTO(computer));
		
	}

	@Test
	public void testDTOToModel() {

		try {
			computer = new Computer(Long.valueOf(1),"Excilys",dateFormat.parse("2012-11-11"),dateFormat.parse("2012-12-11"),Long.valueOf(2));
		} catch (ParseException e) {
			
		}
		try {
			assertEquals("Les informations sont incorrectes", computer, MapperComputer.DTOToModel(dtoComputer));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
		}
		
	}

}
