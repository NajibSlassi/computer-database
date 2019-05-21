package com.excilys.cdb.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;
import com.excilys.cdb.vue.CLI;

/**
 * Servlet implementation class AddComputer
 */

@Controller
@RequestMapping({ "/addcomputer"})
public class AddComputerController {
	private static final long serialVersionUID = 29042019L;
    
    private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);
	private Validator validator;
	
	
	private ServiceComputer serviceComputer;
	private ServiceCompany serviceCompany;
	
	
	public AddComputerController(ServiceComputer computerService,ServiceCompany companyService,Validator validator) {
  
    	this.serviceComputer = computerService;
    	this.serviceCompany=companyService;
    	this.validator=validator;    	
        }
	
	@GetMapping
    public String doGet() {
		
		return "addComputer";
	}
            
	
	
	@PostMapping		
	public ModelAndView doPost( @RequestParam(value="computerName") String computerName,
			@RequestParam(value="introduced") String introduced,
			@RequestParam(value="discontinued") String discontinued,
			@RequestParam(value="companyId") String companyId) {
		DTOComputer dtoComputer= new DTOComputer();
		final ModelAndView modelAndView = new ModelAndView("addComputer");
		dtoComputer = new DTOComputer(
				computerName,
				introduced,
				discontinued,
				companyId);
		LOGGER.info(dtoComputer.toString());
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				Computer computer = MapperComputer.DTOToModel(dtoComputer);
				serviceComputer.insert(computer);
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
	private String redirectToPageNumber(long pageNumber, long pageSize){
    	return "redirect:"+"dashboard?page=" + pageNumber + "&size=" + pageSize;
    }	
	
	private void setCompanyIdList(HttpServletRequest request) throws Exception {
		request.setAttribute("companyList", serviceCompany.list(1,100));
	}
}