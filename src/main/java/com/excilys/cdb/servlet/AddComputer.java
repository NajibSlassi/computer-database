package com.excilys.cdb.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.Validator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 29042019L;
	private static final String NAME_ERROR = "nom requis";
    private static final String INTRO_ERROR = "date introduction requise";
    private static final String DISC_ERROR = "date sortie requise";
    private static final String INTRO_UNDER_DISC ="date introduction supérieur à date de sortie";
	Validator validator=new Validator();
	

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
		
		if (validator.validateDTOComputer(dtoComputer).size()==0) {
			try {
				ServiceComputer.getInstance().insert(dtoComputer);
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
		request.setAttribute("companyList", ServiceCompany.getInstance().list(1,100));
	}
}