package Main;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOFactory;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.vue.CLI;

public class cdb{
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

	public static void doSomething() {
	      LOGGER.info("Lancement du programme");
	  }

	public static void main(String[] args) throws ParseException {
		/*
		doSomething();
		final CLI cli = new CLI();
        cli.run();
        */
		DAOComputerImpl dao = new DAOComputerImpl();
		DTOComputer dtoComputer = new DTOComputer("HikariCP","1995-12-12","1996-12-12","4");
		Computer computer = MapperComputer.DTOToModel(dtoComputer);
				
		System.out.print(dao.findId(computer));		
		
	}}
		
