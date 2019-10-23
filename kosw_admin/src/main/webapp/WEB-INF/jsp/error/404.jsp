<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>404 - ERROR</h1>
	<table>
		<tr>
			<td>Status</td>
			<td>${status  }</td>
		</tr>
		
		<tr>
			<td>Error</td>
			<td>${error  }</td>
		</tr>
		
		<tr>
			<td>Message</td>
			<td>${message  }</td>
		</tr>
		
		<tr>
			<td>Exception</td>
			<td>${exception  }</td>
		</tr>
		
		<tr>
			<td>Trace</td>
			<td>${trace  }</td>
		</tr>
		
	</table>
	
</body>
</html>