package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.mapper.DTOCompany;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ServiceCompany;

 
@SuppressWarnings("rawtypes")
public class CompanyRowMapper implements RowMapper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);
	
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DTOCompany dtoCompany = new DTOCompany();
    		dtoCompany.setId( Long.toString(rs.getLong( "id" )) );
    		dtoCompany.setName( rs.getString( "name" ) );
    		LOGGER.info(dtoCompany.toString());
    	    return MapperCompany.DTOToModel(dtoCompany);}
        
        public static List<Company> mapCompaniesMapper(List<Map<String, Object>> rows){
        	List<Company> companies = new ArrayList<Company>();
            for (Map row : rows) {
                DTOCompany dtoCompany = new DTOCompany();
                dtoCompany.setId(String.valueOf(row.get("id")));
                dtoCompany.setName((String)row.get("name"));
                companies.add(MapperCompany.DTOToModel(dtoCompany));
                }
            return companies;
        }
        }
	
