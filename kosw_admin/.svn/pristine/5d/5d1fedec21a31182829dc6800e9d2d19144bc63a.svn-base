<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">


		<ol class="breadcrumb">
  			<li><a href="${customerOneURL }">우리 회사 설정</a></li>
			<li class="active">건물 정보</li>
		</ol>

        <div class="row">
            <div class="col-md-12">
            	<div class="card">
            		<div class="header">
                        <p class="category">회사 정보</p>
                    </div>
                    
                    <div class="content">
		            	<table class="table">
							<tr>
								<th>번호</th>
								<th>고객사명</th>
								<th>담당자명</th>
								<th>담당자 이메일</th>
								<th>담당자 전화번호</th>
								<th>비고</th>
								<th>등록일시</th>
							</tr>
								<tr>
									<td>${customer.custSeq }</td>
									<td>${customer.custName }</td>
									<td>${customer.postName }</td>
									<td>${customer.postEmail }</td>
									<td>${customer.postPhone }</td>
									<td>${customer.custRemarks }</td>
									<td>${customer.custRegTimeFormat }</td>
								</tr>
						</table>
					</div>	
				</div>

                <div class="card">
                    <div class="header">
                        <p class="category">건물 리스트</p>
                    </div>
                    <div class="content">
                    	<c:if test="${empty buildingList }">
                    		<h5 class="text-danger">등록된 건물이 없습니다. 건물을 등록해주세요.</h5>
                    	</c:if>
						<c:if test="${not empty buildingList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>건물명</th>
										<th>건물 코드</th>
										<th>층수</th>
										<th>층간 계단 수</th>
										<th>주소</th>
										<th>GPS 위도</th>
										<th>GPS 경도</th>
										<th>관리자명</th>
										<th>등록일시</th>
									</tr>
									<c:forEach var="a" items="${buildingList }">
										<tr>
											<td>${a.buildName }</td>
											<td>${a.buildCode }</td>
											<td>${a.buildFloorAmt }</td>
											<td>${a.buildStairAmt }</td>
											<td>${a.buildAddr }</td>
											<td>${a.latitude }</td>
											<td>${a.longitude }</td>
											<td>${a.adminName }</td>
											<td>${a.buildRegTimeFormat }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

                    </div>
                </div>




                <div class="card">
                    <div class="header" style="display:none;">
                        <p class="category">계단 리스트</p>
                    </div>
                    <div class="content">
                    	<c:if test="${empty stairList }">
                    		<h5 class="text-danger">등록된 계단이 없습니다. 계단을 등록해주세요.</h5>
                    	</c:if>
                    	
						<c:if test="${not empty stairList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>건물명</th>
										<th>계단명</th>
										<th>계단 등록일시</th>
									</tr>
									<c:forEach var="a" items="${stairList }">
										<tr>
											<td>${a.buildName }</td>
											<td>${a.stairName }</td>
											<td>${a.stairRegTimeFormat }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
                    </div>
                </div>

                <div class="card">
                    <div class="header">
                    	<p class="category">계단 수정</p>
                    </div>
                    <div class="content">
                    	<c:url var="stairEdit" value="/stairEdit"/>
                    	<form name="stairEdit" method="POST" action="${stairEdit }" class="form-horizontal">
                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">건물 선택</label>
                    			<div class="col-sm-10">
                    				<select name="buildSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
                    					<c:forEach var="a" items="${buildingList }">
											<option value="${a.buildSeq }" <c:if test="${stair.buildSeq eq a.buildSeq}">selected</c:if> >${a.buildName }</option>
										</c:forEach>
                    				</select>
                    			</div>
                    		</div>
                    		</fieldset>

                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">계단 명</label>
                    			<div class="col-sm-10">
                    				<input name="stairName" value="${stair.stairName }" type="text" class="form-control" autocomplete="off">
                    			</div>
                    		</div>
                    		</fieldset>
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">수정</button>
                    		</div>
                    		
                    	</form>
                    </div>
                </div>

                <script>
                    (function($) {
                		$(document.stairEdit).validate({
                			rules : {
                				buildSeq : {
                					required : true
                				},
                				stairName : {
                					required : true,
                					minlength : 2,
                					maxlength : 10
                				}
                			},
                			messages : {
                				buildSeq : {
                					required : "건물을 선택해주세요."
                				},
                				stairName : {
                					required : "계단 명을 입력해주세요.",
                					minlength : "계단 명을 2자리 이상으로 입력해주세요.",
                					maxlength : "계단 명을 10자리 미만으로 입력해주세요."
                				}
                			}
                		});
                    })(jQuery);
                </script>
                

            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

