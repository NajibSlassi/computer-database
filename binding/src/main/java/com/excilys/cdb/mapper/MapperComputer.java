package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.excilys.cdb.dto.DTOComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class MapperComputer {
static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	/**
	 * Converti un objet Model Computer en DTOComputer
	 * @param computer
	 * @return un objet DTOComputer
	 */
	public static DTOComputer modelToDTO(Computer computer) {
		String id = Long.toString(computer.getId());
		String name = computer.getName();
		String introduced = "NULL";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(computer.getIntroduced() != null ) {
			introduced = dateFormat.format(computer.getIntroduced());
		}
		
		String discontinued = "NULL";
		if(computer.getDiscontinued() != null ) {
			discontinued = dateFormat.format(computer.getDiscontinued());
		}
		String companyId = "NULL";
		
		if(computer.getCompany()!=null && computer.getCompany().getId() != null) {
			companyId = Long.toString(computer.getCompany().getId());
		}
		
		
		return new DTOComputer(id, name, introduced, discontinued, companyId);
	}
	
	/**
	 * Converti un DTOComputer en Model
	 * @param computer
	 * @return Le model qui est un objet Computer
	 * @throws ParseException 
	 */
	public static Computer DTOToModel(DTOComputer dtoComputer)  {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Computer computer = new Computer();
		try {
			computer =  new Computer(
					Long.valueOf(Optional.ofNullable(dtoComputer.getId()).orElseGet(() -> "-1")),
					dtoComputer.getName(),
					dateFormat.parse(Optional.ofNullable(dtoComputer.getIntroduced()).orElseGet(() -> "1996-01-15")),
					dateFormat.parse(Optional.ofNullable(dtoComputer.getDiscontinued()).orElseGet(() -> "1996-01-15")),
					new Company(Long.valueOf(Optional.ofNullable(dtoComputer.getCompanyId()).orElseGet(() -> "-1")),dtoComputer.getCompanyName())
					);
		} catch (NumberFormatException | ParseException e) {
			
		}
		return computer;
	}

}
