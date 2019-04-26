package Main;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.vue.CLI;

public class cdb{
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

	public static void doSomething() {
	      LOGGER.info("Lancement du programme");
	  }

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DTOComputer dtoComputer=new DTOComputer("1","Excilys","2012-11-11","2012-12-11","2");
		Computer computer = new Computer();
		try {
			computer.setId(Long.valueOf(1));
			computer.setName("Excilys");
			computer.setIntroduced(dateFormat.parse("2012-11-11"));
			computer.setDiscontinued(dateFormat.parse("2012-12-11"));
			computer.setCompanyId(Long.valueOf(2));
		} catch (ParseException e) {
			
		}
		System.out.print(computer.toString());
		
		//doSomething();
		//final CLI cli = new CLI();
        //cli.run();
	}}
		
