package Main;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOFactory;
import com.excilys.cdb.vue.CLI;

public class cdb{
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

	public static void doSomething() {
	      LOGGER.info("Lancement du programme");
	  }

	public static void main(String[] args) throws ParseException {
		
		DAOComputerImpl dao = new DAOComputerImpl(DAOFactory.getInstance());
		doSomething();
		final CLI cli = new CLI();
        cli.run();
	}}
		
