package Main;


import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb.springconfig.SpringConfig;
import com.excilys.cdb.vue.CLI;

@Component
public class cdb{
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);
	
	private final CLI cli;

	
	
	public cdb(CLI cli) {
		super();
		this.cli = cli;
	}

	public static void doSomething() {
	      LOGGER.info("Lancement du programme");
	  }

	public static void main(String[] args) throws ParseException {
		
		doSomething();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		CLI cli = context.getBean("CLI", CLI.class);
        cli.run();
        
		
	}
	
}
		
