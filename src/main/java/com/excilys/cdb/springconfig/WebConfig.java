package com.excilys.cdb.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.excilys.cdb" })
public class WebConfig implements WebMvcConfigurer {

	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/ressources/**").addResourceLocations("/ressources/");
	    }

	    @Override
	    public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/ressources/views/", ".jsp");
	    }
}