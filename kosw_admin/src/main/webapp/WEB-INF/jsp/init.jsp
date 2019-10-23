<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
	    <c:choose>
			<c:when test="${isSuper eq true}">
				<%  response.sendRedirect("cafeList"); %>
			</c:when>
			<c:otherwise>
				<%  response.sendRedirect("cafeOfMineList"); %>
			</c:otherwise>
		</c:choose>	
	    
    
</html>

