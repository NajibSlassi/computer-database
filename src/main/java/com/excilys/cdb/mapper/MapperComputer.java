package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputer;

import ch.qos.logback.classic.Logger;

public class MapperComputer {
static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
private static Logger LOGGER = (Logger) LoggerFactory.getLogger(DAOComputer.class);
	
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
		
		try {
			if(computer.getIntroduced() != null && !computer.getIntroduced().equals(dateFormat.parse("1996-01-15"))) {
				introduced = dateFormat.format(computer.getIntroduced());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String discontinued = "NULL";
		try {
			if(computer.getDiscontinued() != null && !computer.getDiscontinued().equals(dateFormat.parse("1996-01-15"))) {
				discontinued = dateFormat.format(computer.getDiscontinued());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String companyId = "NULL";
		
		if(computer.getCompany().getId() != null) {
			companyId = Long.toString(computer.getCompany().getId());
		}
		String companyName = "NULL";
		
		if(computer.getCompany().getName() != null) {
			companyId = Long.toString(computer.getCompany().getId());
		}
		
		return new DTOComputer(id, name, introduced, discontinued, companyId,companyName);
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
			LOGGER.warn(e.toString());
		}
		return computer;
	}

}
