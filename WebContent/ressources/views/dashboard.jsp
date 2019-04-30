<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="ressources/css/bootstrap.min.css" rel="stylesheet"
          media="screen">
    <link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="ressources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard"> Application - Computer
            Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">${numberOfComputers} Computersfound</h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">
                    <input type="search" id="searchbox" name="search"
                           class="form-control" placeholder="Search name"/> <input
                        type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary"/>
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="addComputer">Add
                    Computer</a> <a class="btn btn-default" id="editComputer" href="#"
                                    onclick="$.fn.toggleEditMode();">Edit</a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
        <input type="hidden" name="selection" value="">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
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
                <th>Computer name</th>
                <th>Introduced date</th>
                <!-- Table header for Discontinued Date -->
                <th>Discontinued date</th>
                <!-- Table header for Company -->
                <th>Company</th>

            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">
            <c:forEach var="computer" items="${computers}">
                <tr>
                    <td class="editMode"><input type="checkbox" name="cb"
                                                class="cb" value="${computer.getId()}"></td>
                    <td><a href="editComputer.html" onclick="">${computer.getName()}</a></td>
                    <td>${computer.getIntroduced()}</td>
                    <td>${computer.getDiscontinued()}</td>
                    <td>${computer.getName()}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <ul class="pagination">
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
        </div>
    </div>
</footer>
<script src="ressources/js/jquery.min.js"></script>
<script src="ressources/js/bootstrap.min.js"></script>
<script src="ressources/js/dashboard.js"></script>

</body>
</html>