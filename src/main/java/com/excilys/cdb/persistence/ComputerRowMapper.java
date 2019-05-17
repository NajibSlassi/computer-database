package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
 
@SuppressWarnings("rawtypes")
public class ComputerRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DTOComputer dtoComputer = new DTOComputer();
    		dtoComputer.setId( Long.toString(rs.getLong( "id" )) );
    		dtoComputer.setName( rs.getString( "name" ) );
    		dtoComputer.setIntroduced( rs.getString( "introduced" ) );
    		dtoComputer.setDiscontinued( rs.getString( "discontinued" ) );
    		
    		Object item = rs.getObject("company_id");
    		String strValue1 = (item == null ? null : item.toString());

    		dtoComputer.setCompanyId(strValue1);
    	    return MapperComputer.DTOToModel(dtoComputer);}
        
        public static List<Computer> mapComputersMapper(List<Map<String, Object>> rows){
        	List<Computer> computers = new ArrayList<Computer>();
            for (Map row : rows) {
                DTOComputer dtoComputer = new DTOComputer();
                dtoComputer.setId(String.valueOf(row.get("id")));
                dtoComputer.setName((String)row.get("name"));
                Timestamp introduced = (Timestamp) row.get("introduced");
                Timestamp discontinued = (Timestamp) row.get("discontinued");
                Date dateIntroduced = new Date();
                Date dateDiscontinued = new Date();
                if (introduced!=null) {
                	dateIntroduced.setTime(introduced.getTime());
                    dtoComputer.setIntroduced((String) new SimpleDateFormat("yyyy-MM-dd").format(dateIntroduced));	
                }
                if (discontinued!=null) {
                	dateDiscontinued.setTime(discontinued.getTime());
                    dtoComputer.setDiscontinued((String)new SimpleDateFormat("yyyy-MM-dd").format(dateDiscontinued));
                }
                
                if (row.get("company_id")!=null) {
                	dtoComputer.setCompanyId(String.valueOf(row.get("company_id")));
              
                computers.add(MapperComputer.DTOToModel(dtoComputer));
            }
                }
            return computers;
        }
        }
	
