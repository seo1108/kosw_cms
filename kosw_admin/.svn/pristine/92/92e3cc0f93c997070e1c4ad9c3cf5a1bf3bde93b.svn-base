<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/png" href="<c:url value="/favicon.ico"/>">
<title>Insert title here</title>

<%
	String message = (String)request.getAttribute("message");
	if (message != null){
%>
		<script>alert("<%=message %>");</script>
<%		
	}
%>
</head>
<body>
	FILE UPLOAD
	<c:url var="fileUpload" value="/fileUpload"/>
	<form action="${fileUpload}" method="post" enctype="multipart/form-data">
		<input type="file" name="file">
		<input type="submit" value="Submit" />
	</form>
	
</body>
</html>