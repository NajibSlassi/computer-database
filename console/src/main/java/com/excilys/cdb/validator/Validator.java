package com.excilys.cdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DTOComputer;



@Component()
public class Validator {
	

	public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public ArrayList<String> validateDTOComputer(DTOComputer computer) {
		ArrayList<String> al = new ArrayList<String>();
		if(computer.getName() == null || computer.getName().equals("NULL")) {
			al.add("nom requis" );
		}
		if(computer.getIntroduced() == null || computer.getIntroduced().equals("NULL")) {
			al.add("date introduction requise");
		} 
		if(computer.getDiscontinued() == null || computer.getDiscontinued().equals("NULL")) { 
			al.add("date sortie requise");
		} 
		Date introduced = null;
		Date discontinued = null;
		try {
			introduced = simpleDateFormat.parse(computer.getIntroduced());
			discontinued = simpleDateFormat.parse(computer.getDiscontinued());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		if (introduced.after(discontinued)){ 
			al.add("date introduction sup�rieur � date de sortie");
			}
		
		return al;
	}
}