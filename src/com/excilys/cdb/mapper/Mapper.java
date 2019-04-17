package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class Mapper {
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	public static Company mapCompany( DTOCompany dtoCompany ) {
		Company company = new Company();
		company.setId( Long.valueOf(Optional.ofNullable(dtoCompany.getId()).orElseGet(() -> "-1")) );
		company.setName(dtoCompany.getName());
	    return company;
	}
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	public static Computer mapComputer( DTOComputer dtoComputer ) throws ParseException{
		Computer computer = new Computer();
		computer.setId(Long.valueOf(Optional.ofNullable(dtoComputer.getId()).orElseGet(() -> "-1")));
		computer.setName( dtoComputer.getName());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		computer.setIntroduced(dateFormat.parse(Optional.ofNullable(dtoComputer.getIntroduced()).orElseGet(() -> "1996-01-15 08:00:00")));
		computer.setDiscontinued( dateFormat.parse(Optional.ofNullable(dtoComputer.getDiscontinued()).orElseGet(() -> "1996-01-15 08:00:00")));
		computer.setCompanyId(Long.valueOf(Optional.ofNullable(dtoComputer.getCompanyId()).orElseGet(() -> "-1")));
		
	    return computer;
	}
	}

	

