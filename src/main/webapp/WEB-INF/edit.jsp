<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Authentication</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/secondary.css">
	<link rel="stylesheet" type="text/css" href="/css/main.css"> <!-- change to match your file/naming structure -->
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 class=headBox><c:out value="${job.title}"/></h1>
	<div class="container">
		<form:form action="/edit/${id}" method="post" modelAttribute="job">
		    <input type="hidden" name="_method" value="put">
		    <div class="formContainer">
		        <form:label path="title">Title: </form:label>
		        <form:input path="title"/>
		        <form:errors class="error" path="title"/>
		    </div>
		    <div class="formContainer">
		        <form:label path="description">Description: </form:label>
		        <form:textarea path="description"/>
		        <form:errors class="error" path="description"/>
		    </div>
		    <div class="formContainer">
		        <form:label path="location">Location: </form:label>
		        <form:input path="location"/>
		        <form:errors class="error" path="location"/>
		    </div>
		    <input type="submit" value="Submit" id="submit"/>
		</form:form>
	</div>
	<a href="/dashboard"><button>Cancel</button></a>
</body>
</html>