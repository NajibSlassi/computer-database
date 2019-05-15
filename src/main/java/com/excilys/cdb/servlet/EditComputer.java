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

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 29042019L;
    public ServiceComputer serviceComputer;
    
    private static Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
	Validator validator;
	
	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.serviceComputer = wac.getBean(ServiceComputer.class);
		this.validator = wac.getBean(Validator.class);
		
	}
	

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("id", id);
		
		this.getServletContext().getRequestDispatcher("/ressources/views/editComputer.jsp").forward( request, response );
	}
	
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Computer computer = new Computer();
		DTOComputer dtoComputer= new DTOComputer();
		dtoComputer = new DTOComputer(
				request.getParameter("id"),
				request.getParameter("computerName"),
				request.getParameter("introduced"),
				request.getParameter("discontinued"),
				request.getParameter("companyId"));
		LOGGER.info(dtoComputer.toString());
		try {
			computer = MapperComputer.DTOToModel(dtoComputer);
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				serviceComputer.update(computer);
			} catch (IllegalArgumentException | DAOException | ParseException e) {
				
				e.printStackTrace();
			}
			response.sendRedirect("dashboard?page=1&size=10");
		}
		else {
			
			request.setAttribute("al", validator.validateDTOComputer(dtoComputer));
			doGet(request, response);
		}
		
	}
}