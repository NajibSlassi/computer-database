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
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOComputerImpl;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.persistence.DAOFactory;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.vue.CLI;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOComputerImpl dao;
	private ServiceComputer serviceComputer;
	private static Logger LOGGER = LoggerFactory.getLogger(CLI.class);

    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.dao = daoFactory.getDAOComputer();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/ressources/views/addComputer.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DTOComputer dtoComputer = new DTOComputer();
        dtoComputer.setName(request.getParameter("computerName"));
        dtoComputer.setIntroduced(request.getParameter("introduced"));
        dtoComputer.setDiscontinued(request.getParameter("discontinued"));
        dtoComputer.setCompanyId("companyId");

        try {
			serviceComputer.insert(dtoComputer);
		} catch (IllegalArgumentException | DAOException | ParseException e) {
			LOGGER.warn("erreur lors de l'insertion du computer");
		}
        
        this.getServletContext().getRequestDispatcher("/ressources/views/addComputer.jsp").forward(request, response);
        
        
    }

}
