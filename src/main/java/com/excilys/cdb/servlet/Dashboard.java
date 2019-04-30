package com.excilys.cdb.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.mapper.DTOComputer;
import com.excilys.cdb.persistence.DAOException;
import com.excilys.cdb.service.ServiceComputer;

/**
 * Servlet implementation class Dashboard
 */

public class Dashboard extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_PAGE_SIZE = 50;
    public ServiceComputer computerService = ServiceComputer.getInstance();

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
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long pageIndex = getPageIndex(request);
        long pageSize = getPageSize(request);

        final long numberOfComputers = computerService.count();
        if (redirectIfPageOutOfRange(response, pageIndex, numberOfComputers, pageSize)) {
            return;
        }
        List<DTOComputer> computers = null;
		try {
			computers = computerService.list((int)(pageIndex), (int)pageSize);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        setNumberOfComputers(request, numberOfComputers);
        setComputers(request, computers);
        setPaggingParameters(request, pageIndex, numberOfComputers, pageSize);

        setPageSize(request, pageSize);
        setCurrentPageIndex(request, pageIndex);

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

    private long getPageSize(HttpServletRequest request) {
        final Long pageSize = getParameterAsLong(request, "size");
        return Objects.nonNull(pageSize) ? pageSize : DEFAULT_PAGE_SIZE;
    }

}