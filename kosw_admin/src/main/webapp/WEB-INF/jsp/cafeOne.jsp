<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="내 Cafe 리스트 [${cafe.cafename }]" />

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

		<ol class="breadcrumb" style="display:none;">
  			<li><a href="#">Breadcrumb 1</a></li>
			<li><a href="#">Breadcrumb 2</a></li>
			<li class="active"></li>
		</ol>
		
		<c:if test="${empty cafe }">
			<script>alert("잘못된 접근입니다.");window.history.back(-1);</script>
		</c:if>
		
		<div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="header">
                        <h4 class="title">카페 정보</h4>
                    </div>
                    <div class="content">
                    	
                        <form name="customerEdit" action="<c:url value='/customerEdit' />" method="POST">
                        	<input name="cafeseq" type="hidden" value="${cafe.cafeseq }">
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>회사명</label>
                                        <input name="cafename" type="text" class="form-control" placeholder="카페" value="${cafe.cafename }">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>담당자명</label>
                                        <input name="adminname" type="text" class="form-control" placeholder="담당자명" value="${cafe.adminname }">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">등록일시</label>
                                        <input type="text" disabled class="form-control" value="${cafe.opendate }">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>담당자 이메일</label>
                                        <input name="postEmail" type="email" class="form-control" placeholder="이메일" value="${cafe.adminemail }">
                                    </div>
                                </div>
                                
                            </div>

						
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>비고</label>
                                        <input name="cafedesc" type="text" class="form-control" placeholder="" value="${cafe.cafedesc }">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>추가분류</label><br>
                                        <label>카페 가입시 회원들이 입력할 수 있도록 추가 분류를 할 수 있습니다.(관리자 메모기능)</label><br>
                                        <label>예) 사번 입력</label><br>
                                        <label>예) 동/호수 입력</label>
                                        <input name="additions" type="text" class="form-control" placeholder="" value="${cafe.additions }">
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>회사 공개</label>
                                        <input name="shared" type="checkbox" <c:if test="${cafe.confirm eq 'Y'}">checked</c:if> value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-info btn-fill pull-right">수정</button>
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="clearfix"></div>
                        </form>

                        <script>
		                    (function($) {
	                    		$(document.customerEdit).validate({
	                    			rules : {
		                				custName : {
		                					required : true,
		                					minlength : 3,
		                					maxlength : 32
		                				},
		                				postName : {
		                					required : true,
		                					minlength : 3,
		                					maxlength : 16
		                				},
		                				postEmail : {
		                					required : true,
		                					email : true,
		                				},
		                				postPhone : {
		                					required : true,
		                					minlength : 8,
		                					maxlength : 15
		                				},
		                				custRemarks : {
		                					maxlength : 100
		                				}
		                			},
		                			messages : {
		                				custName : {
		                					required : "고객사명을 입력해주세요.",
		                					minlength : "고객사명을 3자리 이상 입력해주세요.",
		                					maxlength : "고객사명을 32자리 미만으로 입력해주세요.",
		                				},
		                				postEmail : {
		                					required : "담당자 이메일을 입력해주세요.",
		                					email : "담당자 이메일의 형식을 확인해주세요."
		                				},
		                				postName : {
		                					required : "담당자명을 입력해주세요.",
		                					minlength : "담당자명을 3자리 이상 입력해주세요.",
		                					maxlength : "담당자명을 16자리 미만으로 입력해주세요.",
		                				},
		                				postPhone : {
		                					required : "담당자 전화번호를 입력해주세요.",
		                					minlength : "담당자 전화번호를 8자리 이상 입력해주세요.",
		                					maxlength : "담당자 전화번호를 15자리 미만으로 입력해주세요.",
		                				},
		                				custRemarks : {
		                					maxlength : "비고를 100자 미만으로 입력해주세요."	
		                				}
		                			}
	                    		});
		                    })(jQuery);
	                    </script>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                	<div class="card-user text-center" style="padding-top:20px;">
                    	<%-- <c:set var="logoPath"><c:url value="/logoFile"/>/${customer.custSeq }</c:set> --%>
                    	<c:choose>
                    		<c:when test="${cafe.logo eq ''}">
                    			<img class="avatar border-gray" src="assets/img/ic_logo.png" alt="..."/>
                    		</c:when>
                    		<c:otherwise>
		                    	<img class="avatar border-gray" src="${cafe.logo }" alt="..."/>
	                    	</c:otherwise>
	                    </c:choose>	
                    </div>
                	
                	
                    <%-- <div id="logoColor" class="content" style="background:#${logoColor};min-height:165px;">
                    	<div class="card-user text-center">
                    		<c:set var="logoPath"><c:url value="/logoFile"/>/${customer.custSeq }</c:set>
                    		<img class="avatar border-gray" src="${logoPath }" alt="..."/>
                    	</div>
                	</div>
                     --%>
                    	
	                <div style="padding:6px;">
	                	<form name="updateLogo" action="<c:url value="/updateLogo" />" method="post" enctype="multipart/form-data" >
	                		<input name="custSeq" type="hidden" value="${customer.custSeq }">
                            <div class="form-group">
                                <label>로고 이미지 (가로x세로 비율 : 2:1 권장, 세로 제한 200px)</label>
                                <input name="file" type="file" />
                                
                            </div>
                            <button type="submit" class="btn btn-fill btn-info">수정</button>
                        </form>

					</div>
					
                </div>
            </div>

        </div>

	
        <div class="row">
            <div class="col-md-12">
            	<c:if test="${not empty categoryList }">
                    <div class="card">
	                    <div class="header">
	                        <p class="category">카테고리 리스트</p>
	                    </div>
	                    <div class="content">
	                    	<c:if test="${empty categoryList }">
	                    		<h5 class="text-danger">등록된 카테고리가 없습니다. 카테고리를 등록해주세요.</h5>
	                    	</c:if>
							<c:if test="${not empty categoryList }">
								<div class="table-responsive">
									<table class="table">
										<tr>
											<th>카테고리명</th>
											<th>ACTION</th>
										</tr>
										<c:forEach var="a" items="${categoryList }">
											<tr>
												<td>${a.name }</td>
												<td class="tx-actions text-right">
	                                                <a onclick="departmentEditShow('${a.cateseq}','${a.name }');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple btn-icon edit" style="display:inline-block;">
	                                                    <i class="fa fa-edit"></i>
	                                                </a>
	                                                
	                                                <form class="deleteForm" method="post" action="<c:url value='/departmentDelete'/>" style="display:inline-block;">
	                                                	<input name="cateseq" type="hidden" value="${a.cateseq }"/>
	                                                	<input name="cafeseq" type="hidden" value="${a.cafeseq }">
		                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
		                                                    <i class="fa fa-times"></i>
		                                                </button>
	                                                </form>
	                                            </td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
							
							<div class="text-center">
		                    	<a data-target="#departmentAdd"  data-toggle="collapse">
	                        		신규 카테고리 등록<b class="caret"></b>
	                    		</a>
		                    </div>
	                    </div>
                    </div>
                    
	                    
                    <div id="departmentEdit" class="card card-collapse collapse">
	                    <div class="header">
	                    	<div class="row">
		                        <div class="col-sm-8">
	                    			<p class="category">카테고리 수정</p>
	                    		</div>
	                    		<div class="col-sm-4">
	                    			<a data-target="#departmentEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
	                    		</div>
                    		</div>
	                    </div>
	                    <div class="content">
	                    	<c:url var="departmentEdit" value="/departmentEdit"/>
	                    	<form name="departmentEdit" method="POST" action="${departmentEdit }" class="form-horizontal">
	                    		<input name="cateseq" type="hidden" value="${a.cateseq }">
	                    		<input name="user_seq" type="hidden" value="${loginseq }">
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">카테고리명</label>
	                    			<div class="col-sm-10">
	                    				<input name="deptName" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>

	                    		<div class="footer text-center">
	                    			<button type="submit" class="btn btn-danger btn-fill btn-wd">수정</button>
	                    		</div>
	                    		
	                    	</form>
	                    </div>
                    </div>
                    
                    

                    <div id="departmentAdd" class="card card-collapse collapse">
	                    <div class="header">
	                        <p class="category">카테고리 등록</p>
	                    </div>
	                    <div class="content">
	                    	<c:url var="departmentAdd" value="/departmentAdd"/>
	                    	<form name="departmentAdd" method="POST" action="${departmentAdd }" class="form-horizontal">
	                    		<input name="user_seq" type="hidden" value="${loginseq }">
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">카테고리명</label>
	                    			<div class="col-sm-10">
	                    				<input name="name" type="text" class="form-control" autocomplete="off">
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
	                    $(function(){
	                    	departmentEditShow = function(cateseq, name){
		                		document.departmentEdit.cateseq.value = cateseq;
		                		document.departmentEdit.name.value = name;
		                		
		                		$("#departmentEdit").collapse("show");
		                	}
	                    	
	                    	var validate_option = {
	                    			rules : {
	                    				deptName : {
	                    					required : true,
	                    					minlength : 2,
	                    					maxlength : 20
	                    				}
	                    			},
	                    			messages : {
	                    				deptName : {
	                    					required : "부서명을 입력해주세요.",
	                    					minlength : "부서명을 2자리 이상 입력해주세요.",
	                    					maxlength : "부서명을 20자리 미만으로 입력해주세요.",
	                    				}
	                    			}
	                    		};
	                    	$(document.departmentAdd).validate(validate_option);
	                    	$(document.departmentEdit).validate(validate_option);
	                    });
                    </script>
				</c:if>
				
				<c:if test="${not empty cafeUserList }">
                    <div class="card">
	                    <div class="header">
	                        <p class="category">사용자 리스트</p>
	                    </div>
	                    <div class="content">
	                    	<c:if test="${empty cafeUserList }">
	                    		<h5 class="text-danger">등록된 사용자가 없습니다.</h5>
	                    	</c:if>
							<c:if test="${not empty cafeUserList }">
								<div class="table-responsive">
									<table class="table">
										<tr>
											<th>사용자명</th>
											<th>닉네임</th>
											<th>오른층수</th>
											<th>걸음수</th>
											<th>관리자여부</th>
										</tr>
										<c:forEach var="a" items="${cafeUserList }">
											<tr>
												<td>${a.userName }</td>
												<td>${a.nickName }</td>
												<td><fmt:formatNumber value="${a.sActAmt }" pattern="#,###" /></td>
												<td><fmt:formatNumber value="${a.walkcount }" pattern="#,###" /></td>
												<td>${a.isAdmin }</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
	                    </div>
                    </div>
                    
	                    
				</c:if>
				<%-- <div class="card">
                    <div class="header">
                        <p class="category">추가 분류 (관리자 메모기능)</p>
                        
                        <div class="form-group" style="padding-top:10px;padding-bottom:10px;">
                        	<input name="cafedesc" type="text" class="form-control" placeholder="" value="${cafe.additions }" />
                       	</div>
                    </div>
                    
                    
                </div>     --%>
				
                    
<%--                     <div class="text-center">
                    	<a href="${userListURL }" class="btn btn-fill btn-info">사용자 리스트 보기</a>
                    </div> --%>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

