package com.excilys.cdb.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView doGet(@RequestParam(value="id") String idReq) {
		final ModelAndView modelAndView = new ModelAndView("editComputer");
		int id = Integer.parseInt(idReq);
		modelAndView.addObject("id", id);
		return modelAndView;
	}
	
	@PostMapping		
	public ModelAndView doPost( 
			@RequestParam(value="id") String id,
			@RequestParam(value="computerName") String computerName,
			@RequestParam(value="introduced") String introduced,
			@RequestParam(value="discontinued") String discontinued,
			@RequestParam(value="companyId") String companyId) {
		DTOComputer dtoComputer= new DTOComputer();
		final ModelAndView modelAndView = new ModelAndView("editComputer");
		dtoComputer = new DTOComputer(
				id,
				computerName,
				introduced,
				discontinued,
				companyId);
		LOGGER.info(dtoComputer.toString());
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				Computer computer = MapperComputer.DTOToModel(dtoComputer);
				serviceComputer.update(computer);
			} catch (IllegalArgumentException | DAOException | ParseException e) {
				// TODO Auto-generated catch block
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