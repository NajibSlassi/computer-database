package com.excilys.cdb.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;


import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class Dashboard
 */

public class Dashboard extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(Dashboard.class);
	
	public static final int DEFAULT_PAGE_SIZE = 50;
    public ServiceComputer serviceComputer;
    public ServiceCompany serviceCompany;
    
    @Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.serviceComputer = wac.getBean(ServiceComputer.class);
		this.serviceCompany=wac.getBean(ServiceCompany.class);
	}

    private List<Long> indexOfPages(long pageCurrent, long pageSize, long numberOfEntities) {
        final Set<Long> pages = new TreeSet<>();
        int shift = 0;
        boolean more = true;
        while (pages.size() < 5 && more) {
            more = false;

            final long before = pageCurrent - shift;
            if (before >= 1) {
                pages.add(before);
                more = true;
            }

            final long next = pageCurrent + shift;
            if ((next - 1) * pageSize < numberOfEntities) {
                pages.add(next);
                more = true;
            }

            shift++;
        }
        return new ArrayList<>(pages);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String checkedComputers = request.getParameter("selection");
    	String[] arrayId = checkedComputers.split(",");
		for(String id:arrayId) {
			LOGGER.info("Le string actuel est :" + id);
			serviceComputer.delete(Long.parseLong(id));
			
		}  
      
        doGet(request, response);
        
     
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long pageIndex = getPageIndex(request);
        long pageSize = getPageSize(request);

        final long numberOfComputers = serviceComputer.count();
        if (redirectIfPageOutOfRange(response, pageIndex, numberOfComputers, pageSize)) {
            return;
        }
        List<Computer> computers = null;
        List<DTOComputer> dtoComputers = new LinkedList<DTOComputer>();
       
		try {
			computers = serviceComputer.list((int)(pageIndex), (int)pageSize);
			for (Computer computer: computers) {
				dtoComputers.add(MapperComputer.modelToDTO(computer));
			}
			setNameCompanyToDTOComputer(dtoComputers);
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		
		if (Integer.toString(1).equals(request.getParameter("ord"))) {
				try {
					
					computers = serviceComputer.listOrderByNameASC((int)(pageIndex), (int)pageSize);
					dtoComputers = new LinkedList<DTOComputer>();
					for (Computer computer: computers) {
						dtoComputers.add(MapperComputer.modelToDTO(computer));
					}
					setNameCompanyToDTOComputer(dtoComputers);
					
				} catch (DAOException | ParseException e) {
					
					e.printStackTrace();
				}
			}
		
		else if (Integer.toString(2).equals(request.getParameter("ord"))) {
			try {
				
				computers = serviceComputer.listOrderByNameDESC((int)(pageIndex), (int)pageSize);
				dtoComputers = new LinkedList<DTOComputer>();
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
				
			} catch (DAOException | ParseException e) {
				
				e.printStackTrace();
			}
		}
		else if (Integer.toString(3).equals(request.getParameter("ord"))) {
			try {
				computers = serviceComputer.listOrderByIntroASC((int)(pageIndex), (int)pageSize);
				dtoComputers = new LinkedList<DTOComputer>();
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
				
			} catch (DAOException | ParseException e) {
				
				e.printStackTrace();
			}
		}
		else if (Integer.toString(4).equals(request.getParameter("ord"))) {
			try {
				computers = serviceComputer.listOrderByIntroDESC((int)(pageIndex), (int)pageSize);
				dtoComputers = new LinkedList<DTOComputer>();
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
			} catch (DAOException | ParseException e) {
				
				e.printStackTrace();
			}
		}
		else if (Integer.toString(5).equals(request.getParameter("ord"))) {
			try {
				computers = serviceComputer.listOrderByDiscASC((int)(pageIndex), (int)pageSize);
				dtoComputers = new LinkedList<DTOComputer>();
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
			} catch (DAOException | ParseException e) {
				
				e.printStackTrace();
			}
		}
		else if (Integer.toString(6).equals(request.getParameter("ord"))) {
			try {
				computers = serviceComputer.listOrderByDiscDESC((int)(pageIndex), (int)pageSize);
				dtoComputers = new LinkedList<DTOComputer>();
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
				
			} catch (DAOException | ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		
	
		if (request.getParameter("search")!=null) {
			try {
				String word = request.getParameter("search");
				computers = serviceComputer.listByName((int)(pageIndex), (int)pageSize,word);
				List<Company> companies = serviceCompany.listByName((int)(pageIndex), (int)pageSize,word);
				
				dtoComputers = new LinkedList<DTOComputer>();
				for (Company company:companies) {
					List<Computer> computersByCompany = serviceComputer.findComputerByCompanyId(company.getId());
					for (Computer computer:computersByCompany) {
						if (Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE).matcher(company.getName()).find()){
							dtoComputers.add(MapperComputer.modelToDTO(computer));
						}
					}
					
				}
				
				for (Computer computer: computers) {
					dtoComputers.add(MapperComputer.modelToDTO(computer));
				}
				setNameCompanyToDTOComputer(dtoComputers);
				
			} catch (DAOException | ParseException e) {
				LOGGER.warn("Erreur survenu lors de l'execution de la fontion search: " + e.toString());;
			}
		}
		
        setNumberOfComputers(request, numberOfComputers);
        setComputers(request, dtoComputers);
        
        setPaggingParameters(request, pageIndex, numberOfComputers, pageSize);

        
        setPageSize(request, pageSize);
        setCurrentPageIndex(request, pageIndex);
        setCurrentPageSize(request,pageSize);

        getServletContext().getRequestDispatcher("/ressources/views/dashboard.jsp").forward(request, response);
    }

	private boolean redirectIfPageOutOfRange(HttpServletResponse response, long pageIndex, double numberOfComputers,
                                             long pageSize) throws IOException {
        long indexLastPage = indexLastPage(numberOfComputers, pageSize);
        if (pageIndex < 1) {
            redirectToPageNumber(response, 1, pageSize);
            return true;
        }
        if (pageIndex > indexLastPage) {
            redirectToPageNumber(response, indexLastPage, pageSize);
            return true;
        }
        return false;
    }

    private long indexLastPage(double numberOfEntities, long pageSize) {
        return (long) Math.ceil(numberOfEntities / pageSize);
    }

    private void redirectToPageNumber(HttpServletResponse response, long pageNumber, long pageSize) throws IOException {
        response.sendRedirect("dashboard?page=" + pageNumber + "&size=" + pageSize);
    }	
    
    private void setPaggingParameters(HttpServletRequest request, long pageCurrent, long numberOfEntities, long pageSize) {
        setPreviousPage(request, pageCurrent);
        setNextPage(request, pageCurrent, numberOfEntities, pageSize);
        setPagesNumbers(request, pageCurrent, numberOfEntities, pageSize);
    }

    private void setPagesNumbers(HttpServletRequest request, long pageCurrent, long numberOfEntries, long pageSize) {
        final List<Long> pages = indexOfPages(pageCurrent, pageSize, numberOfEntries);
        request.setAttribute("pages", pages);
    }

    private void setCurrentPageIndex(HttpServletRequest request, long pageCurrent) {
        request.setAttribute("current", pageCurrent);
    }
    
    private void setCurrentPageSize(HttpServletRequest request, long currentSize) {
        request.setAttribute("currentSize", currentSize);
    }

    private void setPageSize(HttpServletRequest request, long pageSize) {
        request.setAttribute("size", pageSize);
    }

    private void setComputers(HttpServletRequest request, List<DTOComputer> computers) {
        request.setAttribute("computers", computers);
    }

    private void setNextPage(HttpServletRequest request, long pageCurrent, long numberOfEntities, long pageSize) {
        if (pageCurrent * pageSize < numberOfEntities) {
            request.setAttribute("next", pageCurrent + 1);
        }
    }

    private void setPreviousPage(HttpServletRequest request, long pageCurrent) {
        if (pageCurrent > 1) {
            request.setAttribute("previous", pageCurrent - 1);
        }
    }

    private void setNumberOfComputers(HttpServletRequest request, long numberOfComputers) {
        request.setAttribute("numberOfComputers", numberOfComputers);
    }

    private long getPageIndex(HttpServletRequest request) {
        final Long pageIndex = getParameterAsLong(request, "page");
        return Objects.nonNull(pageIndex) ? pageIndex : 1;
    }

    private Long getParameterAsLong(HttpServletRequest request, String nameParameter) {
        final String parameter = request.getParameter(nameParameter);
        try {
            return Long.parseLong(parameter);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private List<DTOComputer> setNameCompanyToDTOComputer(List<DTOComputer> dtoComputers) throws DAOException, ParseException{
    	for (DTOComputer dtoComputer: dtoComputers) {
			try{
				dtoComputer.setCompanyId(MapperCompany.modelToDTO(serviceCompany.find((int)Long.parseLong(dtoComputer.getCompanyId()))).getName());
			}
			catch(NumberFormatException e) {
				LOGGER.warn(e.toString());
			}
		}
    	return dtoComputers;
    }
    
    private long getPageSize(HttpServletRequest request) {
        final Long pageSize = getParameterAsLong(request, "size");
        return Objects.nonNull(pageSize) ? pageSize : DEFAULT_PAGE_SIZE;
    }

}