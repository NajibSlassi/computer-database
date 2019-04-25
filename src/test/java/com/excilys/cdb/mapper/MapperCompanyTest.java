package com.excilys.cdb.mapper;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DAOComputerImpl;


public class MapperCompanyTest {
	
    private MapperCompany converterRegisterUserDto;
    private DTOCompany registerUserDto;

    @Before
    void init(){
        converterRegisterUserDto = new MapperCompany();
        DTOCompany dto = new DTOCompany();
    }

    private DTOCompany getSampleDtoCompany(){
        DTOCompany registerUserDto = new DTOCompany();
        registerUserDto.setId("1");
        registerUserDto.setName("Excilys");
        return registerUserDto;
    }


}