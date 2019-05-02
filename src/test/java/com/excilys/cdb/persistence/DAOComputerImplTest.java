package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;

@RunWith(MockitoJUnitRunner.class)
public class DAOComputerImplTest {

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    int userId = 100;

    public DAOComputerImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateWithNoExceptions() throws SQLException {

        DAOComputerImpl instance = new DAOComputerImpl(DAOFactory.getInstance());
        Computer computer = null;
		try {
			computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2015-01-01","2016-01-02","1"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        instance.createComputer(computer);
        long id= instance.maxId();
        Computer actual = null;
		try {
			actual = instance.showComputer((int)(id));
		} catch (DAOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals("Les informations sont incorrectes", computer.getName(), actual.getName());
        assertEquals("Les informations sont incorrectes", computer.getIntroduced(), actual.getIntroduced());
        assertEquals("Les informations sont incorrectes", computer.getDiscontinued(), actual.getDiscontinued());
        assertEquals("Les informations sont incorrectes", computer.getCompanyId(), actual.getCompanyId());
    }
    @Test
    public void testDeleteComputer() {
    	DAOComputerImpl instance = new DAOComputerImpl(DAOFactory.getInstance());
        Computer computer = null;
		try {
			computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2015-01-01","2016-01-02","1"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instance.createComputer(computer);
		long id= instance.maxId();
        instance.deleteComputer(id);
        long expected= id-1;
        long actual = instance.maxId();
        
        assertEquals("L'élément n'est pas supprimé", expected, actual);
        
    }
    
    @Test
    public void testUpdateComputer() {
    	DAOComputerImpl instance = new DAOComputerImpl(DAOFactory.getInstance());
        Computer computer = null;
		try {
			computer = MapperComputer.DTOToModel(new DTOComputer("-1","Excilys","2015-01-01","2016-01-02","1"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instance.updateComputer(computer);
		long id= instance.maxId();
        instance.deleteComputer(id);
        long expected= id-1;
        long actual = instance.maxId();
        
        assertEquals("L'élément n'est pas supprimé", expected, actual);
    }
    
    
}