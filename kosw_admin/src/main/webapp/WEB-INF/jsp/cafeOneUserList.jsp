<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


	<c:if test="${empty cafeUserList }">
               		<h5 class="text-danger">등록된 사용자가 없습니다.</h5>
               	</c:if>
	<c:if test="${not empty cafeUserList }">
		<div class="table-responsive">
			<table id="sortTable" class="tablesorter table">
				<thead>
				<tr>
					<th>번호</th>
					<th>아이디(이메일)</th>
					<th>사용자명</th>
					<th>닉네임</th>
					<th>가입일시</th>
					<th>가입경로</th>
					<th>카테고리</th>
					<th>OS</th>
					<th>이용카페수</th>
					<th>오른층수</th>
					<th>걸음수</th>
					<th>관리자여부</th>
					<th>관리자로 설정</th>
				</tr>
					</thead>
					<tbody>
				<c:forEach var="a" items="${cafeUserList }">
					<tr>
						<td>${a.userSeq }</td>
						<td><a onclick="userStat('${a.userSeq }');" style="cursor:pointer;">${a.userEmail }</a></td>
						<td><a onclick="userStat('${a.userSeq }');" style="cursor:pointer;">${a.userName }</a></td>
						<td><a onclick="userStat('${a.userSeq }');" style="cursor:pointer;">${a.nickName }</a></td>
						<td>${a.regdate }</td>
						<td>${a.loginType }</td>
						<td>${a.catename }</td>
						<td>${a.deviceType }</td>
						<td>${a.cafeCnt }</td>
						<td><fmt:formatNumber value="${a.sActAmt }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${a.walkcount }" pattern="#,###" /></td>
						<td>
							<c:choose>
							<c:when test="${a.isAdmin eq 'Y' }">관리자</c:when>
							<c:otherwise></c:otherwise>
							</c:choose>
						</td>
						<td>
						<c:if test="${a.isAdmin ne 'Y' }">
							<form class="registAdminForm" method="post" action="<c:url value="cafeAdminChange"/>" style="display:inline-block;">
								<input name="cafeseq" type="hidden" value="${cafe.cafeseq }"/>
								<input name="email" type="hidden" value="${a.userEmail }"/>
								<input name="username" type="hidden" value="${a.userName }"/>
 								<button type="submit" class="btn btn-sm btn-danger btn-fill">
 									 관리자 변경
								</button>
							</form>
						</c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
							
<script>
	$(function() {
	  	$("#sortTable").tablesorter({ headers: {11 : {sorter: false}, 12 : {sorter: false}} });
	});
</script>