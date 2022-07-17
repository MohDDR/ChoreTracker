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
	<link rel="stylesheet" type="text/css" href="/css/index.css">
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="indexContainer">
		<div>
		   	<h1>Registration!</h1>
			<form:form action="/register" method="post" modelAttribute="newUser">
			    <p>
			        <form:label path="firstName">First Name </form:label>
			        <form:input path="firstName"/>
			        <form:errors path="firstName"/>
			    </p>
			    <p>
			        <form:label path="lastName">Last Name </form:label>
			        <form:input path="lastName"/>
			        <form:errors path="lastName"/>
			    </p>
			    <p>
			        <form:label path="email">Email </form:label>
			        <form:input type="email" path="email"/>
			        <form:errors path="email"/>
			    </p>
			    <p>
			        <form:label path="password">Password </form:label>
			        <form:input type="password" path="password"/>
			        <form:errors path="password"/>
			    </p>
			    <p>
			        <form:label path="confirm">Confirm Password </form:label>
			        <form:input type="password" path="confirm"/>
			        <form:errors path="confirm"/>
			    </p>
			    <input type="submit" value="Submit" id="submit"/>
			</form:form> 
		</div>
		<hr class="test">
		<div>
			<h1>Login!</h1>
			<form:form action="/login" method="post" modelAttribute="newLogin">
			    <p>
			        <form:label path="email">Email </form:label>
			        <form:input type="email" path="email"/>
			        <form:errors path="email"/>
			    </p>
			    <p>
			        <form:label path="password">Password </form:label>
			        <form:input type="password" path="password"/>
			        <form:errors path="password"/>
			    </p>
			    <input type="submit" value="Submit" id="submit"/>
			</form:form> 
		</div>
	</div>
</body>
</html>