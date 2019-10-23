<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% String message = (String)request.getAttribute("message");
		if (message != null){ 
	%><script>alert("<%=message %>");</script><% }%>
	
	<h1> 고객사 리스트 </h1>
	
	<c:if test="${not empty customerList }">
		<table>
			<tr>
				<th>번호</th>
				<th>회사 코드</th>
				<th>고객사명</th>
				<th>담당자명</th>
				<th>담당자 이메일</th>
				<th>담당자 전화번호</th>
				<th>비고</th>
				<th>등록일시</th>
			</tr>
			<c:forEach var="a" items="${customerList }">
				<tr>
					<td>${a.custSeq }</td>
					<td>${a.custCode }</td>
					<td>${a.custName }</td>
					<td>${a.postName }</td>
					<td>${a.postEmail }</td>
					<td>${a.postPhone }</td>
					<td>${a.custRemarks }</td>
					<td>${a.custRegTime }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
		
	
	
	
</body>
</html>