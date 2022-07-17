<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Chore Tracker</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/main.css"> <!-- change to match your file/naming structure -->
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="welcome">
		<h1>Welcome, <c:out value="${user.firstName}"></c:out>!</h1>
		<div>
			<a href="/addJob"><button>Add a Job!</button></a>
			<a href="/logout"><button>Log Out</button></a>
		</div>
	</div>
	<div class="mainBox">
		<table class="table table-bordered table-hover" id="table">
			<tr>
				<th scope="col">Job</th>
				<th scope="col">Location</th>
				<th scope="col">Action</th>
			</tr>
		    <c:forEach var="job" items="${jobs}">
		    	<tr>
		        	<td><c:out value="${job.title}"/></td>
		        	<td><c:out value="${job.location}"></c:out></td>
		        	<td class="horizontal">
		        		<a href="/view/${job.id}"><button>View</button></a>
		        		<a href="/dashboard/${job.id}"><button id="submit" class="white">Add to My Jobs</button></a>
		        		<c:if test="${ job.postedBy.id == user.id }">
		        			<a href="/edit/${job.id}"><button>Edit</button></a>
		        			<form action="/cancel/${job.id}" method="post">
						 		<input type="hidden" name="_method" value="delete">
						 		<input type="submit" value="Cancel" id="delete">
							</form>
		        		</c:if>
		        	</td>
		    	</tr>
		    </c:forEach>
		</table>
		<table class="table table-bordered table-hover" id="table">
			<tr>
				<th scope="col">My Job</th>
				<th scope="col">Actions</th>
			</tr>
			<c:forEach var="job" items="${user.pendingJobs}">
				<tr>
					<td><c:out value="${job.title}"/></td>
					<td class="horizontal">
						<a href="/view/${job.id}"><button>View</button></a>
			        	<form action="/done/${job.id}" method="post">
					 		<input type="hidden" name="_method" value="delete">
					 		<input type="submit" value="Done" id="delete">
						</form>
			        </td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>