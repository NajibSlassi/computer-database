package com.excilys.cdb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 29042019L;

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
		try {
			ServiceComputer.getInstance().insert(new DTOComputer(
					request.getParameter("computerName"),
					request.getParameter("introduced"),
					request.getParameter("discontinued"),
					request.getParameter("companyId"))
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		response.sendRedirect("dashboard?page=1&size=10");
	}
	
	private void setCompanyIdList(HttpServletRequest request) throws Exception {
		request.setAttribute("companyList", ServiceCompany.getInstance().list(1,100));
	}
}