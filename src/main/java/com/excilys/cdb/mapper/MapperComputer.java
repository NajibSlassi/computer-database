package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.excilys.cdb.model.Computer;

public class MapperComputer {
static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Map a model Computer to his DTO
	 * @param computer
	 * @return the corresponding DTO
	 */
	public static DTOComputer modelToDTO(Computer computer) {
		String id = Long.toString(computer.getId());
		String name = computer.getName();
		String introduced = "NULL";
		if(computer.getIntroduced() != null) {
			introduced = dateFormat.format(computer.getIntroduced());
		}
		
		String discontinued = "NULL";
		if(computer.getDiscontinued() != null) {
			discontinued = dateFormat.format(computer.getDiscontinued());;
		}
		String company = "NULL";
		if(!computer.getCompanyId().equals(0) && computer.getCompanyId() != null) {
			company = Long.toString(computer.getCompanyId());
		}
		return new DTOComputer(id, name, introduced, discontinued, company);
	}
	
	/**
	 * Map a DTO Computer to his model
	 * @param computer
	 * @return the corresponding model
	 * @throws ParseException 
	 */
	public static Computer DTOToModel(DTOComputer dtoComputer) throws ParseException {
		Computer computer=new Computer();
		computer.setId(Long.valueOf(Optional.ofNullable(dtoComputer.getId()).orElseGet(() -> "-1")));
		computer.setName( dtoComputer.getName());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		computer.setIntroduced(dateFormat.parse(Optional.ofNullable(dtoComputer.getIntroduced()).orElseGet(() -> "1996-01-15 08:00:00")));
		computer.setDiscontinued( dateFormat.parse(Optional.ofNullable(dtoComputer.getDiscontinued()).orElseGet(() -> "1996-01-15 08:00:00")));
		computer.setCompanyId(Long.valueOf(Optional.ofNullable(dtoComputer.getCompanyId()).orElseGet(() -> "-1")));
		
		return computer;
	}

}
