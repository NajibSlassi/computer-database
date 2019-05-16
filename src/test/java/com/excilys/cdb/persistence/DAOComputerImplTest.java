package com.excilys.cdb.persistence;

import java.sql.SQLException;
import java.text.ParseException;


import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.springconfig.SpringConfig;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class DAOComputerImplTest {
	
	@ClassRule
	public static final SpringClassRule springClassRule = new SpringClassRule();
	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	private DAOComputerImpl dao;
    

    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    @Test
    public void testCreateWithNoExceptions() throws SQLException, ParseException {
    	
        Computer computer = null;
		computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2015-01-01","2016-01-02","5"));
        dao.createComputer(computer);
        long id= dao.maxId();
        Computer actual = null;
		try {
			actual = dao.showComputer((int)(id));
		} catch (DAOException | ParseException e) {
			
			e.printStackTrace();
		}
        assertEquals("Les informations sont incorrectes", computer.getName(), actual.getName());
        assertEquals("Les informations sont incorrectes", computer.getIntroduced(), actual.getIntroduced());
        assertEquals("Les informations sont incorrectes", computer.getDiscontinued(), actual.getDiscontinued());
        assertEquals("Les informations sont incorrectes", computer.getCompanyId(), actual.getCompanyId());
    }
    
    @Test
    public void testUpdateComputer() throws ParseException {
        Computer computer = null;
		
		computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2016-01-01","2017-01-02","6"));
	
		long id= dao.maxId();
		computer.setId(id);
		dao.updateComputer(computer);
		Computer actual = null;
		
		actual = dao.showComputer((int)(id));
		
        assertEquals("Les informations sont incorrectes", computer.getName(), actual.getName());
        assertEquals("Les informations sont incorrectes", computer.getIntroduced(), actual.getIntroduced());
        assertEquals("Les informations sont incorrectes", computer.getDiscontinued(), actual.getDiscontinued());
        assertEquals("Les informations sont incorrectes", computer.getCompanyId(), actual.getCompanyId());
    }
    
    @Test
    public void testDeleteComputer() throws ParseException {
        Computer computer = null;
		
		computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2015-01-01","2016-01-02","5"));
		
		dao.createComputer(computer);
		long id= dao.maxId();
        dao.deleteComputer(id);
        long expected= id-1;
        long actual = dao.maxId();
        
        assertEquals("L'élément n'est pas supprimé", expected, actual);
        
    }
    
    
    
    
}