package com.excilys.cdb.console;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.console","com.excilys.cdb.controller","com.excilys.cdb.service","com.excilys.cdb.persistence"})
public class ConsoleConfig {
	
}
