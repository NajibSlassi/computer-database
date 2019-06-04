package com.excilys.cdb;


import java.text.ParseException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb.springconfig.SpringConfig;


@Component()
public class cdb{

	private final CLI cli;
		
	public cdb(CLI cli) {
		super();
		this.cli = cli;
	}

	

	public static void main(String[] args) throws ParseException {
		
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		CLI cli = context.getBean("CLI", CLI.class);
        cli.run();
        
		
	}



	public CLI getCli() {
		return cli;
	}
	
}
		
