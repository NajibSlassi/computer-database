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

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;
import com.excilys.cdb.vue.CLI;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 29042019L;
	private static final String NAME_ERROR = "nom requis";
    private static final String INTRO_ERROR = "date introduction requise";
    private static final String DISC_ERROR = "date sortie requise";
    private static final String INTRO_UNDER_DISC ="date introduction supérieur à date de sortie";
    public ServiceComputer computerService = ServiceComputer.getInstance();
    
    private static Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
	Validator validator=new Validator();
	

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("id", id);
		
		this.getServletContext().getRequestDispatcher("/ressources/views/editComputer.jsp").forward( request, response );
	}
	
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		DTOComputer dtoComputer= new DTOComputer();
		dtoComputer = new DTOComputer(
				request.getParameter("id"),
				request.getParameter("computerName"),
				request.getParameter("introduced"),
				request.getParameter("discontinued"),
				request.getParameter("companyId"));
		LOGGER.info(dtoComputer.toString());
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				ServiceComputer.getInstance().update(dtoComputer);
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
	private void setComputerId(HttpServletRequest request, long computerId) {
        request.setAttribute("computerId", computerId);
    }
	private void setCompanyIdList(HttpServletRequest request) throws Exception {
		request.setAttribute("companyList", ServiceCompany.getInstance().list(1,100));
	}
}