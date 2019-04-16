package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static DTOCompany mapCompany( ResultSet resultSet ) throws SQLException {
		DTOCompany company = new DTOCompany();
		company.setId( resultSet.getLong( "id" ) );
		company.setName( resultSet.getString( "name" ) );
	    return company;
	}
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static DTOComputer mapComputer( ResultSet resultSet ) throws SQLException {
		DTOComputer computer = new DTOComputer();
		computer.setId( resultSet.getLong( "id" ) );
		computer.setName( resultSet.getString( "name" ) );
		computer.setIntroduced( resultSet.getString( "introduced" ) );
		computer.setDiscontinued( resultSet.getString( "discontinued" ) );
	    return computer;
	}
	}

	

