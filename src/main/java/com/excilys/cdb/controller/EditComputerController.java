package com.excilys.cdb.controller;


import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;

/**
 * Servlet implementation class EditComputer
 */

@Controller
@RequestMapping({ "/editcomputer"})
public class EditComputerController {
    public ServiceComputer serviceComputer;
    
    private static Logger LOGGER = LoggerFactory.getLogger(EditComputerController.class);
	Validator validator;
	
	public EditComputerController(ServiceComputer computerService,Validator validator) {
		  
    	this.serviceComputer = computerService;
    	
    	this.validator=validator;    	
        }
	
	@GetMapping
    public String doGet() {
		
		return "editComputer";
	}
	
	@PostMapping
	public ModelAndView doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Computer computer = new Computer();
		DTOComputer dtoComputer= new DTOComputer();
		dtoComputer = new DTOComputer(
				request.getParameter("id"),
				request.getParameter("computerName"),
				request.getParameter("introduced"),
				request.getParameter("discontinued"),
				request.getParameter("companyId"));
		LOGGER.info(dtoComputer.toString());
		final ModelAndView modelAndView = new ModelAndView("addComputer");
		computer = MapperComputer.DTOToModel(dtoComputer);
		
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				serviceComputer.update(computer);
			} catch (IllegalArgumentException | DAOException | ParseException e) {
				
				e.printStackTrace();
			}
			return modelAndView;
		}
		else {
			
			modelAndView.addObject("al", validator.validateDTOComputer(dtoComputer));
			return modelAndView;
		}
		
	}
}