<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

		<ol class="breadcrumb">
  			<li><a href="#">Breadcrumb 1</a></li>
			<li><a href="#">Breadcrumb 2</a></li>
			<li class="active">Current Page</li>
		</ol>

        <div class="row">
            <div class="col-md-12">
                
                <c:if test="${empty customer }">
                	<script>
                		alert("잘못된 접근입니다.");window.history.back(-1);
                	</script>
                </c:if>
                
				<c:if test="${not empty customer }">
                	<div class="card">
	                    <div class="header">
	                        <h4 class="title">${customer.custName }</h4>
	                        <p class="category"></p>
	                    </div>
	                    <div class="content">
							
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>회사 코드</th>
										<th>담당자명</th>
										<th>담당자 이메일</th>
										<th>담당자 전화번호</th>
										<th>비고</th>
										<th>등록일시</th>
									</tr>
									<tr>
										<td>${customer.custCode }</td>
										<td>${customer.postName }</td>
										<td>${customer.postEmail }</td>
										<td>${customer.postPhone }</td>
										<td>${customer.custRemarks }</td>
										<td>${customer.custRegTime }</td>
									</tr>
								</table>
							</div>
	                    </div>
                    </div>

                    <div class="card">
	                    <div class="header">
	                        <p class="category">관리자 리스트</p>
	                    </div>
	                    <div class="content">
	                    	<c:if test="${empty adminList }">
	                    		<h5 class="text-danger">등록된 관리자 없습니다. 관리자를 등록해주세요.</h5>
	                    	</c:if>
		                    <c:if test="${not empty adminList }">
			                    <div class="table-responsive">
			                    	<table class="table">
										<tr>
											<th>번호</th>
											<th>이메일</th>
											<th>이름</th>
											<th>전화번호</th>
											<th>사용여부</th>
											<th>등록일시</th>
										</tr>
										<c:forEach var="a" items="${adminList }">
											<tr>
												<td>${a.adminSeq }</td>
												<td>${a.email }</td>
												<td>${a.adminName }</td>
												<td>${a.adminPhone }</td>
												<td>${a.activeFlag }</td>
												<td>${a.adminRegTime }</td>
											</tr>
										</c:forEach>
									</table>
								</div>
		                    </c:if>
	                    </div>
                    </div>


                    <div class="card">
	                    <div class="header">
	                        <p class="category">부서 리스트</p>
	                    </div>
	                    <div class="content">
	                    	<c:if test="${empty departmentList }">
	                    		<h5 class="text-danger">등록된 부서가 없습니다. 부서를 등록해주세요.</h5>
	                    	</c:if>
							<c:if test="${not empty departmentList }">
								<div class="table-responsive">
									<table class="table">
										<tr>
											<th>부서명</th>
											<th>관리자명</th>
											<th>등록일시</th>
										</tr>
										<c:forEach var="a" items="${departmentList }">
											<tr>
												<td>${a.deptName }</td>
												<td>${a.adminName }</td>
												<td>${a.deptRegTime }</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
							
							<div class="text-center">
	                    		<a href="<c:url value="/departmentAdd" />?seq=${customer.custSeq }" class="btn btn-info btn-fill btn-wd">부서 등록</a>
	                    	</div>
	                    </div>
                    </div>

                    
                    <div class="card">
	                    <div class="header">
	                        <legend>부서 등록</legend>
	                    </div>
	                    <div class="content">
	                    	<c:url var="departmentAdd" value="/departmentAdd"/>
	                    	<form name="departmentAdd" method="POST" action="${departmentAdd }" class="form-horizontal">
	                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">부서명</label>
	                    			<div class="col-sm-10">
	                    				<input name="deptName" type="text" class="form-control" type="email" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>

	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">관리자</label>
	                    			<div class="col-sm-10">
	                    				<select name="adminSeq" class="form-control">
	                    					<c:forEach var="a" items="${adminList }">
	                    						<option value="${a.adminSeq}">${a.adminName}</option>
	                    					</c:forEach>
	                    				</select>
	                    			</div>
	                    		</div>
	                    		</fieldset>

	                    		
	                    		<div class="footer text-center">
	                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
	                    		</div>
	                    		
	                    	</form>
	                    </div>
                    </div>
                    
                    <script>
	                    (function($) {
                    		$(document.departmentAdd).validate({
                    			rules : {
                    				deptName : {
                    					required : true,
                    					minlength : 2,
                    					maxlength : 20
                    				},
                    				adminSeq : {
                    					required : true
                    				}
                    			},
                    			messages : {
                    				deptName : {
                    					required : "부서명을 입력해주세요.",
                    					minlength : "부서명을 2자리 이상 입력해주세요.",
                    					maxlength : "부서명을 20자리 미만으로 입력해주세요.",
                    				},
                    				adminSeq : {
                    					required : "관리자를 선택해주세요."
                    				}
                    			}
                    		});
	                    })(jQuery);
                    </script>
	                    
				</c:if>
            </div>
        </div>
    </div>
</div>



<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

