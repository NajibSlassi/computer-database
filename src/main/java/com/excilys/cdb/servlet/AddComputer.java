package com.excilys.cdb.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


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
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 29042019L;
    
    private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);
	private Validator validator;
	
	
	private ServiceComputer serviceComputer;
	private ServiceCompany serviceCompany;
	
	
	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.serviceComputer = wac.getBean(ServiceComputer.class);
		
		this.validator = wac.getBean(Validator.class);
		this.serviceCompany=wac.getBean(ServiceCompany.class);
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		try {
			this.setCompanyIdList(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/ressources/views/addComputer.jsp").forward( request, response );
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		DTOComputer dtoComputer= new DTOComputer();
		
		
		dtoComputer = new DTOComputer(
				request.getParameter("computerName"),
				request.getParameter("introduced"),
				request.getParameter("discontinued"),
				request.getParameter("companyId"));
		LOGGER.info(dtoComputer.toString());
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				Computer computer = MapperComputer.DTOToModel(dtoComputer);
				serviceComputer.insert(computer);
			} catch (IllegalArgumentException | DAOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("dashboard?page=1&size=10");
		}
		else {
			
			request.setAttribute("al", validator.validateDTOComputer(dtoComputer));
			doGet(request, response);
		}
		
		
		
		
		
	}
	
	private void setCompanyIdList(HttpServletRequest request) throws Exception {
		request.setAttribute("companyList", serviceCompany.list(1,100));
	}
}