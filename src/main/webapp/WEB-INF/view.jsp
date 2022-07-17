<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	
	<!-- for Bootstrap CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<!-- YOUR own local CSS -->
	<link rel="stylesheet" type="text/css" href="/css/secondary.css"> <!-- change to match your file/naming structure -->
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>
	<div class="headBoxView">
		<h1><c:out value="${job.title}"/></h1>
	   	<a href="/dashboard">Dashboard</a>
	</div>
	<div class="headBodyView">
		<h5>Posted By: </h5>
	   	<p><c:out value="${job.postedBy.firstName}"/></p>
	</div>
	<div class="headBodyView">
		<h5>Location: </h5>
	   	<p><c:out value="${job.location}"/></p>
	</div>
	<div class="headBodyView">
		<h5>Description: </h5>
	   	<p><c:out value="${job.description}"/></p>
	</div>
	<div class="headBodyView">
		<h5>Posted On: </h5>
	   	<p><c:out value="${job.createdAt}"/></p>
	</div>
	<c:set var="count" value="0"/>
	<c:forEach var="worker" items="${job.getWorker()}">
		<c:if test="${worker.getId()==user.id}">
			<c:set var="count" value="${count+1}"/>
		</c:if>
	</c:forEach>
		<c:if test="${count==0}">
			<a href="/dashboard/${job.id}"><button id="submit" class="white">Add to My Jobs</button></a>
		</c:if>
</body>
</html>