package Main;


import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.excilys.cdb.vue.CLI;

public class cdb{
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

	public static void doSomething() {
	      LOGGER.info("Lancement du programme");
	  }

	public static void main(String[] args) throws ParseException {
		doSomething();
		final CLI cli = new CLI();
        cli.run();
	}}
		
