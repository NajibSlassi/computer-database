<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code=""/>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet"
          media="screen">
    <link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="static/css/main.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    
    
    
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard"> <spring:message code="application.name"/></a>
    </div>
</header>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">${numberOfComputers} <spring:message code="dashboard.ComputersfoundTotal"/> ${numberOfComputersDisplayed} <spring:message code="dashboard.Computersfound"/></h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">
                    <input type="search" id="searchbox" name="search"
                           class="form-control" placeholder="Search name"/> <input
                        type="submit" id="searchsubmit" value=<spring:message code="dashboard.filter"/>
                        class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
		        <div class="dropdown" style="display: inline-block;">
		            <button class="btn btn-danger dropdown-toggle" type="button" id="dropdownMenuButton"
		               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="app.lang.title"/></button>
		            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		               <a class="dropdown-item" href="?lang=en"><spring:message code="app.lang.english"/></a> 
		               <a class="dropdown-item" href="?lang=fr"><spring:message code="app.lang.french"/></a>
		            </div>
		         </div>
                <a class="btn btn-success" id="addComputer" href="addcomputer"><spring:message code="dashboard.addComputer"/></a> <a class="btn btn-default" id="editcomputer" href="#"
                                    onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit"/></a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
        <input type="hidden" name="selection" value="">
    </form>
    
    

    <div class="container" style="margin-top: 10px;">
        <table id="myTable" class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px;"><input
                        type="checkbox" id="selectall"/> <span
                        style="vertical-align: top;"> - <a href="#"
                                                           id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
                        class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
                <th><button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=1&page='+${current}+'&size='+${currentSize}">ASC</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=2&page='+${current}+'&size='+${currentSize}">DESC</button>
                <spring:message code="dashboard.computerName"/></th>
                <th><button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=3&page='+${current}+'&size='+${currentSize}">ASC</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=4&page='+${current}+'&size='+${currentSize}">DESC</button>
                <spring:message code="dashboard.introduced"/></th>
                <!-- Table header for Discontinued Date -->
                <th><button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=5&page='+${current}+'&size='+${currentSize}">ASC</button>
                <button type="button" class="btn btn-default" onclick="window.location.href='dashboard?search='+'${search}'+'&ord=6&page='+${current}+'&size='+${currentSize}">DESC</button>
                <spring:message code="dashboard.discontinued"/></th>
                <!-- Table header for Company -->
                <th><spring:message code="dashboard.company"/></th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${computers}">
                <tr>
                    <td class="editMode"><input type="checkbox" name="cb"
                                                class="cb" value="${computer.getId()}"></td>
                    <td>
                    <a href="editcomputer?id=${computer.getId()}" onclick="">${computer.getName()}</a>
                    </td>
                    <td class ="introduced">${computer.getIntroduced()}</td>
                    <td class = discontinued>${computer.getDiscontinued()}</td>
                    <td class = companyId>${computer.getCompanyId()}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <ul class="pagination">
        <c:choose>
		    <c:when test="${search=='%'}">
		        <c:if test="${not empty previous}">
		                <li><a aria-label="Previous"
		                       onclick="window.location.href='dashboard?page='+${previous}+'&size='+${size}"> <span
		                        aria-hidden="true">&laquo;</span>
		                </a></li>
		            </c:if>
		            <c:forEach var="page" items="${pages}">
		                <li><a onclick="window.location.href='dashboard?page='+${page}+'&size='+${size}">${page}</a></li>
		            </c:forEach>
		            
		            
		
		            <c:if test="${not empty next}">
		                <li><a aria-label="Next" onclick="window.location.href='dashboard?page='+${next}+'&size='+${size}">
		                    <span aria-hidden="true">&raquo;</span>
		                </a></li>
		            </c:if> 
		        
		    </c:when>    
    	<c:otherwise>
	        <c:if test="${not empty previous}">
			                <li><a aria-label="Previous"
			                       onclick="window.location.href='dashboard?search='+'${search}'+'&page='+${previous}+'&size='+${size}"> <span
			                        aria-hidden="true">&laquo;</span>
			                </a></li>
			            </c:if>
			            <c:forEach var="page" items="${pages}">
			                <li><a onclick="window.location.href='dashboard?search='+'${search}'+'&page='+${page}+'&size='+${size}">${page}</a></li>
			            </c:forEach>
			            
			            
			
			            <c:if test="${not empty next}">
			                <li><a aria-label="Next" onclick="window.location.href='dashboard?search='+'${search}'+'&page='+${next}+'&size='+${size}">
			                    <span aria-hidden="true">&raquo;</span>
			                </a></li>
			            </c:if>
    	</c:otherwise>
</c:choose>
            
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group">
            <button type="button" class="btn btn-default"
                    onclick="window.location.href='dashboard?page='+${current}+'&size=10'">10
            </button>
            <button type="button" class="btn btn-default"
                    onclick="window.location.href='dashboard?page='+${current}+'&size=50'">50
            </button>
            <button type="button" class="btn btn-default"
                    onclick="window.location.href='dashboard?page='+${current}+'&size=100'">100
            </button>
            
            <form action="editcomputer" method="POST">
				<input type="hidden" name="computerName" id='computerName' value="" />
			</form>
            
        </div>
    </div>
</footer>

<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

</body>
</html>