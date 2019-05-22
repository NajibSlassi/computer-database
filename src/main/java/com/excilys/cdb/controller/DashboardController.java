package com.excilys.cdb.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

import org.springframework.stereotype.Controller;


import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class Dashboard
 */

@Controller
@RequestMapping({ "/dashboard", "/" })
public class DashboardController{
	/**
	 * 
	 */
	private static Logger LOGGER = (Logger) LoggerFactory.getLogger(DashboardController.class);
	
	public static final String DEFAULT_PAGE_SIZE = "50";
	
    public ServiceComputer serviceComputer;
   
    public ServiceCompany serviceCompany;
    
    public DashboardController(ServiceComputer computerService,
    		                   ServiceCompany companyService
    	                       ) {
  
    	this.serviceComputer = computerService;
    	this.serviceCompany=companyService;
    	
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

    @PostMapping
    public String deleteComputers(@RequestParam("selection") String checkedComputers,
					    		@RequestParam(value="page",defaultValue = "1") long pageIndex, 
					            @RequestParam(value="size",defaultValue = DEFAULT_PAGE_SIZE) long pageSize,
					            @RequestParam(value="ord",required = false) long orderBy,
					            @RequestParam(value="search",defaultValue = "%") String search){
    	
    	String[] arrayId = checkedComputers.split(",");
		for(String id:arrayId) {
			LOGGER.info("Le string actuel est :" + id);
			serviceComputer.delete(Long.parseLong(id));
			
		}  
      
		return redirectToPageNumber(pageIndex, pageSize);
        
     
    }
    
    @GetMapping(value={"/dashboard","/"})
    public ModelAndView doGet(@RequestParam(value="page",defaultValue = "1") long pageIndex, 
    		            @RequestParam(value="size",defaultValue = DEFAULT_PAGE_SIZE) long pageSize,
    		            @RequestParam(value="ord",required = false) String orderBy,
    		            @RequestParam(value="search",defaultValue = "%") String search
    		            )
            {

        final long numberOfComputers = serviceComputer.count();
        long indexLastPage = indexLastPage(numberOfComputers, pageSize);
        if (redirectIfPageOutOfRange(pageIndex, numberOfComputers, pageSize)==0) {
        	return new ModelAndView(redirectToPageNumber(1, pageSize));
        }else if (redirectIfPageOutOfRange(pageIndex, numberOfComputers, pageSize)==1) {
        	return new ModelAndView(redirectToPageNumber(indexLastPage, pageSize));
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
		
		
		if (Integer.toString(1).equals(orderBy)) {
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
		
		else if (Integer.toString(2).equals(orderBy)) {
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
		else if (Integer.toString(3).equals(orderBy)) {
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
		else if (Integer.toString(4).equals(orderBy)) {
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
		else if (Integer.toString(5).equals(orderBy)) {
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
		else if (Integer.toString(6).equals(orderBy)) {
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
		
		
	
		if (!search.equals("%")) {
			try {
				String word = search;
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
		final ModelAndView modelAndView = new ModelAndView("dashboard");
		modelAndView.addObject("search", search);
		modelAndView.addObject("numberOfComputers", numberOfComputers);
        modelAndView.addObject("computers", dtoComputers);
        /*
         * setPaggingParameters
         */
        modelAndView.addObject("previous", pageIndex - 1);
        modelAndView.addObject("next", pageIndex + 1);
        final List<Long> pages = indexOfPages(pageIndex, pageSize, numberOfComputers);
        modelAndView.addObject("pages", pages);
        
        modelAndView.addObject("size", pageSize);
        modelAndView.addObject("current", pageIndex);
        modelAndView.addObject("currentSize", pageSize);
        
        return modelAndView;
    }

    private String redirectToPageNumber(long pageNumber, long pageSize){
    	return "redirect:"+"dashboard?page=" + pageNumber + "&size=" + pageSize;
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
    private int redirectIfPageOutOfRange( long pageIndex, double numberOfComputers,
            long pageSize) {
		long indexLastPage = indexLastPage(numberOfComputers, pageSize);
		if (pageIndex < 1) {
		
		return 0;
		}
		if (pageIndex > indexLastPage) {
		
		return 1;
		}
		return 2;
		}
    private long indexLastPage(double numberOfEntities, long pageSize) {
        return (long) Math.ceil(numberOfEntities / pageSize);
    }
    
    private String encode(String s) throws UnsupportedEncodingException {
    	return URLEncoder.encode(s, "UTF-8");
        }
    
    
    
    
    

}