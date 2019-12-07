<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="text-center">
	<ul class="pagination">
	
		<c:if test="${pageNavigation.pageNo <= pageNavigation.startPage}">
			<c:set var="firstClass" value="disabled"/>
		</c:if>
		
		<c:if test="${pageNavigation.pageNo <= pageNavigation.beforePage}">
			<c:set var="beforeClass" value="disabled"/>
		</c:if>
		
		<c:if test="${pageNavigation.pageNo >= pageNavigation.nextPage}">
			<c:set var="nextClass" value="disabled"/>
		</c:if>
		
		<c:if test="${pageNavigation.pageNo >= pageNavigation.totalPage}">
			<c:set var="lastClass" value="disabled"/>
		</c:if>
		
		<li class="page-first ${firstClass}"><a href="javascript:setPage(1, '${type}');">«</a></li>
		<li class="page-before ${beforeClass}"><a href="javascript:setPage(${pageNavigation.beforePage }, '${type}');">«</a></li>
		<c:forEach var="p" begin="${pageNavigation.startPage}" end="${pageNavigation.endPage}">
			<c:if test="${pageNavigation.pageNo == p}">
				<c:set var="currentClass" value="active"/>
			</c:if>
			<c:if test="${pageNavigation.pageNo != p}">
				<c:set var="currentClass" value=""/>
			</c:if>
			<li class="${currentClass }"><a href="javascript:setPage(${p}, '${type}');">${p}</a></li>
		</c:forEach>
		<li class="page-next ${nextClass}"><a href="javascript:setPage(${pageNavigation.nextPage }, '${type}');">›</a></li>
		<li class="page-last ${lastClass}"><a href="javascript:setPage(${pageNavigation.totalPage }, '${type}');">››</a></li>
	</ul>
</div>