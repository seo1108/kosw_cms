<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="<c:url value="/favicon.ico"/>">

<script type="text/javascript" src='<c:url value="/static/js/jquery-3.3.1.min.js"/>'></script>
<title>Insert title here</title>
</head>
<body>
	<h1>LOGIN</h1>
	<c:url value="/login" var="loginUrl"/>
	<form:form action="${loginUrl }" method="post">
		<c:if test="${param.error != null}">
			<p>
				Invalid username and password.
			</p>
		</c:if>
		<c:if test="${param.logout != null}">
			<p>
				You have been logged out.
			</p>
		</c:if>
	
		<input type="text" id="username" name="username"/>
		<input type="password" id="password" name="password"/>
		<button type="submit" class="btn">Log in</button>
	</form:form>
	
</body>
</html>