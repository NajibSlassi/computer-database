<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code=""/>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="ressources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="ressources/css/main.css" rel="stylesheet" media="screen">

<script src="${pageContext.request.contextPath}/ressources/js/jquery-340.js"></script>
<script src="${pageContext.request.contextPath}/ressources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/ressources/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/ressources/js/dashboard.js"></script>




</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message code="application.name"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="dashboard.addComputer"/></h1>
                    <form id="monFormulaire" action="addcomputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="dashboard.computerName"/></label>
                                <input type="text" name="computerName" class="form-control" id="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="dashboard.introduced"/></label>
                                <input type="date" name="introduced" class="form-control" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="dashboard.discontinued"/></label>
                                <input type="date" name="discontinued" class="form-control" id="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="dashboard.company"/></label>
                                <input type="text" name="companyId" class="form-control" id="companyId" placeholder="Company ID" required>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code="addcomputer.add"/> class="btn btn-primary">
                            <spring:message code="addcomputer.or"/>
                            <a href="dashboard" class="btn btn-default"><spring:message code="addcomputer.cancel"/></a>
                        </div>
                        <div class="actions pull-right">
                        <c:forEach items="${al}" var="current">
	                        <c:if test="${(not empty current)}">
	                        	<div class="alert alert-danger" role="alert">
	                        		<strong>${current}</strong>
	                    		</div>
	            			</c:if>
	            		</c:forEach>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>