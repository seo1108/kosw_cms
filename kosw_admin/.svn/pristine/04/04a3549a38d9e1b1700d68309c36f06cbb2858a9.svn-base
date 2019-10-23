<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

		<ol class="breadcrumb" style="display:none;">
  			<li><a href="#">Breadcrumb 1</a></li>
			<li><a href="#">Breadcrumb 2</a></li>
			<li class="active"></li>
		</ol>
		
		<c:if test="${empty customer }">
			<script>alert("잘못된 접근입니다.");window.history.back(-1);</script>
		</c:if>
		
		<div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="header">
                        <h4 class="title">우리회사 정보</h4>
                    </div>
                    <div class="content">
                    	
                        <form name="customerEdit" action="<c:url value='/customerEdit' />" method="POST">
                        	<input name="custSeq" type="hidden" value="${customer.custSeq }">
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>회사명</label>
                                        <input name="custName" type="text" class="form-control" placeholder="회사명" value="${customer.custName }">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>담당자명</label>
                                        <input name="postName" type="text" class="form-control" placeholder="담당자명" value="${customer.postName }">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label for="exampleInputEmail1">등록일시</label>
                                        <input type="text" disabled class="form-control" value="${customer.custRegTimeFormat }">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>담당자 이메일</label>
                                        <input name="postEmail" type="email" class="form-control" placeholder="이메일" value="${customer.postEmail }">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>담당자 전화번호</label>
                                        <input name="postPhone" type="text" class="form-control" placeholder="전화번호" value="${customer.postPhone }">
                                    </div>
                                </div>
                            </div>

							<c:if test="${user.superAdmin eq 'true'}">
	                            <div class="row">
	                                <div class="col-md-12">
	                                    <div class="form-group">
	                                        <label>비고</label>
	                                        <input name="custRemarks" type="text" class="form-control" placeholder="" value="${customer.custRemarks }">
	                                    </div>
	                                </div>
	                            </div>
                            </c:if>
                            
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>사용자 자동 승인 여부</label>
                                        <input name="userAutoConfirmFlag" type="checkbox" <c:if test="${customer.userAutoConfirmFlag eq 'Y'}">checked</c:if> value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>회사 공개</label>
                                        <input name="shared" type="checkbox" <c:if test="${customer.shared eq 'Y'}">checked</c:if> value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label>집계 시 타 건물 허용여부</label>
                                        <input name="permit_other_building" type="checkbox" <c:if test="${customer.permit_other_building eq 'Y'}">checked</c:if> value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
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
                
                	<c:if test="${empty logo.logoColor }">
                		<c:set var="logoColor">#eee</c:set>
                	</c:if>
                	<c:if test="${not empty logo.logoColor }">
                		<c:set var="logoColor">${logo.logoColor }</c:set>
                	</c:if>
                	
                    <div id="logoColor" class="content" style="background:#${logoColor};min-height:165px;">
                    	<div class="card-user text-center">
                    		<c:set var="logoPath"><c:url value="/logoFile"/>/${customer.custSeq }</c:set>
                    		<img class="avatar border-gray" src="${logoPath }" alt="..."/>
                    	</div>
                	</div>
                    
                    	
	                <div style="padding:6px;">
	                	<form name="updateLogo" action="<c:url value="/updateLogo" />" method="post" enctype="multipart/form-data" >
	                		<input name="custSeq" type="hidden" value="${customer.custSeq }">
                            <div class="form-group">
                                <label>색상</label>
                                <select name="logoColor" class="selectpicker" data-title="색상을 선택하세요." data-style="btn-default btn-block" data-menu-style="dropdown-blue">
                                	<c:forEach var="c" items="${logoColors }">
                                		<option value="${c}" style="background:#${c};"></option>
                                	</c:forEach>
								</select>
								<script>
									$(document.updateLogo.logoColor).on("change", function(){
										$("#logoColor").css("background", "#" + this.value);
									});
								</script>
                            </div>
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
                
				<c:if test="${not empty customer }">
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
											<!-- <th>비밀번호</th>  -->
											<th>이름</th>
											<th>전화번호</th>
											<th>사용여부</th>
											<th>등록일시</th>
											<c:if test="${user.superAdmin eq 'true'}">
												<th>비밀번호</th>
											</c:if>
											<th>ACTION</th>
										</tr>
										<c:forEach var="a" items="${adminList }">
											<tr>
												<td>${a.adminSeq }</td>
												<td>${a.email }</td>
												<!-- <td>${a.passwd }</td>  -->
												<td>${a.adminName }</td>
												<td>${a.adminPhone }</td>
												<td>
													<c:if test="${a.activeFlag eq 'Y'}">
														<p class="text-primary">사용</p>
													</c:if>
													<c:if test="${a.activeFlag ne 'Y'}">
														<p class="text-danger">비사용</p>
													</c:if>
												</td>
												<td>${a.adminRegTimeFormat }</td>
												<c:if test="${user.superAdmin eq 'true'}">
													<td>${a.passwd }</td>
												</c:if>
												
												<td class="tx-actions text-right">
	                                                <a onclick="adminEditShow('${a.adminSeq}','${a.email }','${a.passwd }', '${a.adminName}', '${a.adminPhone}','${a.activeFlag }');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple btn-icon edit" style="display:inline-block;">
	                                                    <i class="fa fa-edit"></i>
	                                                </a>
	                                                
	                                                <form class="deleteForm" action="<c:url value='/adminDelete'/>" style="display:inline-block;">
	                                                	<input type="hidden" value="${a.adminSeq }"/>
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
		                    	<a data-target="#adminAdd"  data-toggle="collapse">
	                        		신규 관리자 등록<b class="caret"></b>
	                    		</a>
		                    </div>
	                    </div>
                    </div>
                    
                    <script>
                    	$(function(){
                    		$("form.deleteForm").on("submit", function(){
                    			var form = this;
                    			swal({
                    				title : "정말로 삭제 하시겠습니까?",
                    				type: 'warning',
                    				showCancelButton: true,
                                    confirmButtonClass: 'btn btn-danger btn-fill',
                    				confirmButtonText: "삭제",
                                    cancelButtonClass: 'btn btn-default btn-fill',
                    				cancelButtonText: "취소",
                                    buttonsStyling: false
                    			}).then(function(){
                    				form.submit();
                    			}).catch(swal.noop);
                    			return false;
                    		});
                    	});
                    </script>
                    
                    <div id="adminEdit" class="card card-collapse collapse">
	                    <div class="header">
	                    	<div class="row">
	                    		<div class="col-sm-8">
	                    			<p class="category">관리자 수정</p>
	                    		</div>
	                    		<div class="col-sm-4">
	                    			<a data-target="#adminEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
	                    		</div>
	                    	</div>
	                    </div>
	                    <div class="content">
	                    	<c:url var="adminEdit" value="/adminEdit"/>
	                    	<form name="adminEdit" method="POST" action="${adminEdit }" class="form-horizontal">
	                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
	                    		<input type="hidden" name="adminSeq" >
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">이메일</label>
	                    			<div class="col-sm-10">
	                    				<input name="email" readonly type="email" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비밀번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="passwd" type="password" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">이름</label>
	                    			<div class="col-sm-10">
	                    				<input name="adminName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">전화번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="adminPhone" type="tel" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<div class="footer text-center">
	                    			<button type="submit" class="btn btn-danger btn-fill btn-wd">수정</button>
	                    		</div>
	                    		
	                    	</form>
	                    </div>
                    </div>
                    

                    <div id="adminAdd" class="card card-collapse collapse">
	                    <div class="header">
	                    	<p class="category">신규 관리자 등록</p>
	                    </div>
	                    <div class="content">
	                    	<c:url var="adminAdd" value="/adminAdd"/>
	                    	<form name="adminAdd" method="POST" action="${adminAdd }" class="form-horizontal">
	                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">이메일</label>
	                    			<div class="col-sm-10">
	                    				<input name="email" type="email" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비밀번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="passwd" type="password" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">이름</label>
	                    			<div class="col-sm-10">
	                    				<input name="adminName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">전화번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="adminPhone" type="tel" class="form-control" autocomplete="off">
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
                    		adminEditShow = function(adminSeq, email,passwd, adminName, adminPhone){
		                		document.adminEdit.adminSeq.value = adminSeq;
		                		document.adminEdit.email.value = email;
		                		document.adminEdit.passwd.value = passwd;
		                		document.adminEdit.adminName.value = adminName;
		                		document.adminEdit.adminPhone.value = adminPhone;
		                		
		                		$("#adminEdit").collapse("show");
		                	};
		                	
		                	var admin_validate_option = {
                        			rules : {
                        				email : {
                        					required : true,
                        					email : true
                        				},
                        				passwd : {
                        					required : true,
                        					minlength : 4,
                        					maxlength : 15
                        				},
                        				adminName : {
                        					required : true,
                        					minlength : 3,
                        					maxlength : 15
                        				},
                        				adminPhone : {
                        					required : true,
                        					minlength : 9,
                        					maxlength : 15,
                        					regex : /^[\d|-]+$/
                        				}
                        			},
                        			messages : {
                        				email : {
                        					required : "관리자 이메일을 입력해주세요.",
                        					email : "관리자 이메일의 형식을 확인해주세요."
                        				},
                        				passwd : {
                        					required : "관리자 비밀번호를 입력해주세요.",
                        					minlength : "비밀번호는 4자리 이상 입력해주세요.",
                        					maxlength : "비밀번호가 15자리 이하로 입력해주세요."
                        				},
                        				adminName : {
                        					required : "관리자 이름을 입력해주세요.",
                        					minlength : "관리자 이름을 3자리 이상 입력해주세요.",
                        					maxlength : "관리자 이름을 15자리 미만으로 입력해주세요.",
                        				},
                        				adminPhone : {
                        					required : "관리자 전화번호를 입력해주세요.",
                        					minlength : "관리자 전화번호를 9자리 이상 입력해주세요.",
                        					maxlength : "관리자 전화번호를 15자리 미만으로 입력해주세요.",
                        					regex : "전화번호 형식으로 확인해주세요."
                        				}
                        			}
                    		};
		                	
		                	$(document.adminAdd).validate(admin_validate_option);
                   			$(document.adminEdit).validate(admin_validate_option);
		                	
                    	});
                    		
                    	
                    </script>

                    
                    <c:if test="${not empty adminList }">
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
												<th>ACTION</th>
											</tr>
											<c:forEach var="a" items="${departmentList }">
												<tr>
													<td>${a.deptName }</td>
													<td>${a.adminName }</td>
													<td>${a.deptRegTimeFormat }</td>
													<td class="tx-actions text-right">
		                                                <a onclick="departmentEditShow('${a.deptSeq}','${a.deptName }');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple btn-icon edit" style="display:inline-block;">
		                                                    <i class="fa fa-edit"></i>
		                                                </a>
		                                                
		                                                <form class="deleteForm" method="post" action="<c:url value='/departmentDelete'/>" style="display:inline-block;">
		                                                	<input name="deptSeq" type="hidden" value="${a.deptSeq }"/>
		                                                	<input name="custSeq" type="hidden" value="${customer.custSeq }">
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
		                        		신규 부서 등록<b class="caret"></b>
		                    		</a>
			                    </div>
		                    </div>
	                    </div>
	                    
	                    
	                    <div id="departmentEdit" class="card card-collapse collapse">
		                    <div class="header">
		                    	<div class="row">
			                        <div class="col-sm-8">
		                    			<p class="category">부서 수정</p>
		                    		</div>
		                    		<div class="col-sm-4">
		                    			<a data-target="#departmentEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
		                    		</div>
	                    		</div>
		                    </div>
		                    <div class="content">
		                    	<c:url var="departmentEdit" value="/departmentEdit"/>
		                    	<form name="departmentEdit" method="POST" action="${departmentEdit }" class="form-horizontal">
		                    		<input name="deptSeq" type="hidden" value="">
		                    		<input name="custSeq" type="hidden" value="${customer.custSeq }">
		                    		<input name="adminSeq" type="hidden" value="${admin.adminSeq }">
		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">부서명</label>
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
		                        <p class="category">부서 등록</p>
		                    </div>
		                    <div class="content">
		                    	<c:url var="departmentAdd" value="/departmentAdd"/>
		                    	<form name="departmentAdd" method="POST" action="${departmentAdd }" class="form-horizontal">
		                    		<input name="custSeq" type="hidden" value="${customer.custSeq }">
		                    		<input name="adminSeq" type="hidden" value="${admin.adminSeq }">
		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">부서명</label>
		                    			<div class="col-sm-10">
		                    				<input name="deptName" type="text" class="form-control" autocomplete="off">
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
		                    	departmentEditShow = function(deptSeq, deptName){
			                		document.departmentEdit.deptSeq.value = deptSeq;
			                		document.departmentEdit.deptName.value = deptName;
			                		
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
												<c:if test="${user.superAdmin eq 'true'}">
													<th>ACTION</th>
					                            </c:if>
											</tr>
											<c:forEach var="a" items="${buildingList }">
												<tr>
													<td>
														<c:url value="/building" var="buildingUrl">
															<c:param name="cSeq" value="${customer.custSeq }" />
															<c:param name="bSeq" value="${a.buildSeq }" />
														</c:url>
														<a href="${buildingUrl }">${a.buildName }</a>
													</td>
													<td>${a.buildCode }</td>
													<td>${a.buildFloorAmt }</td>
													<td>${a.buildStairAmt }</td>
													<td>${a.buildAddr }</td>
													<td>${a.latitude }</td>
													<td>${a.longitude }</td>
													<td>${a.adminName }</td>
													<td>${a.buildRegTimeFormat }</td>
													<c:if test="${user.superAdmin eq 'true'}">
														<td class="tx-actions text-right">
			                                                <a onclick="buildingEditShow('${a.buildSeq}', '${customer.custSeq}', '${a.buildName}' , '${a.buildFloorAmt}', '${a.buildStairAmt}', '${a.buildAddr}', '${a.latitude}', '${a.longitude}');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple btn-icon edit" style="display:inline-block;">
			                                                    <i class="fa fa-edit"></i>
			                                                </a>
			                                                
			                                                <form class="deleteForm" method="post" action="<c:url value='/buildingDelete'/>" style="display:inline-block;">
			                                                	<input name="buildSeq" type="hidden" value="${a.buildSeq }"/>
			                                                	<input name="custSeq" type="hidden" value="${customer.custSeq }">
				                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
				                                                    <i class="fa fa-times"></i>
				                                                </button>
			                                                </form>
			                                             </td>
                            		                    	</c:if>
		                                            
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:if>

								<div class="text-center">
								<c:if test="${user.superAdmin eq 'true'}">
			                    	<a data-target="#buildingAdd"  data-toggle="collapse">
		                        		신규 건물 등록<b class="caret"></b>
		                    		</a>
		                    	</c:if>
			                    </div>

		                    </div>
	                    </div>


						<div id="buildingAdd" class="card card-collapse collapse">
		                    <div class="header">
		                        <p class="category">건물 등록</p>
		                    </div>
		                    <div class="content">
		                    	<c:url var="buildingAdd" value="/buildingAdd"/>
		                    	<form name="buildingAdd" method="POST" action="${buildingAdd }" class="form-horizontal">
		                    		<input name="custSeq" type="hidden" value="${customer.custSeq }">
		                    		<input name="adminSeq" type="hidden" value="${admin.adminSeq }">
		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">건물명</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildName" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">층수</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildFloorAmt" type="number" class="form-control"  autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">층간 계단 수</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildStairAmt" type="number" class="form-control"  autocomplete="off" value="24">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">주소</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildAddr" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">GPS 위도</label>
		                    			<div class="col-sm-10">
		                    				<input name="latitude" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">GPS 경도</label>
		                    			<div class="col-sm-10">
		                    				<input name="longitude" type="text" class="form-control" autocomplete="off">
		                    				<span class="help-block">좌표 확인은 <a target="_blank" href="http://maps.google.com/">http://maps.google.com/</a> 에서 확인가능 합니다.</span>
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<div class="footer text-center">
		                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
		                    		</div>
		                    		
		                    	</form>
		                    </div>
	                    </div>
	                    
	                    <div id="buildingEdit" class="card card-collapse collapse">
		                    <div class="header">
		                    	<div class="row">
			                        <div class="col-sm-8">
		                    			<p class="category">건물 수정</p>
		                    		</div>
		                    		<div class="col-sm-4">
		                    			<a data-target="#buildingEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
		                    		</div>
	                    		</div>
		                    </div>
		                    <div class="content">
		                    	<c:url var="buildingEdit" value="/buildingEdit"/>
		                    	<form name="buildingEdit" method="POST" action="${buildingEdit }" class="form-horizontal">
		                    		<input name="buildSeq" type="hidden" value="">
		                    		<input name="custSeq" type="hidden" value="">
		                    		<input name="adminSeq" type="hidden" value="${admin.adminSeq }">
		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">건물명</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildName" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">층수</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildFloorAmt" type="number" class="form-control"  autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">층간 계단 수</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildStairAmt" type="number" class="form-control"  autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">주소</label>
		                    			<div class="col-sm-10">
		                    				<input name="buildAddr" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">GPS 위도</label>
		                    			<div class="col-sm-10">
		                    				<input name="latitude" type="text" class="form-control" autocomplete="off">
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">GPS 경도</label>
		                    			<div class="col-sm-10">
		                    				<input name="longitude" type="text" class="form-control" autocomplete="off">
		                    				<span class="help-block">좌표 확인은 <a target="_blank" href="http://maps.google.com/">http://maps.google.com/</a> 에서 확인가능 합니다.</span>
		                    			</div>
		                    		</div>
		                    		</fieldset>

		                    		<div class="footer text-center">
		                    			<button type="submit" class="btn btn-danger btn-fill btn-wd">수정</button>
		                    		</div>
		                    		
		                    	</form>
		                    </div>
	                    </div>

	                    <script>
		                    $(function(){
		                    	buildingEditShow = function(buildSeq, custSeq, buildName , buildFloorAmt, buildStairAmt, buildAddr, latitude, longitude){
		                    		document.buildingEdit.buildSeq.value = buildSeq;
		                    		document.buildingEdit.custSeq.value = custSeq;
		                    		document.buildingEdit.buildName.value = buildName;
		                    		document.buildingEdit.buildFloorAmt.value = buildFloorAmt;
		                    		document.buildingEdit.buildStairAmt.value = buildStairAmt;
		                    		document.buildingEdit.buildAddr.value = buildAddr;
		                    		document.buildingEdit.latitude.value = latitude;
		                    		document.buildingEdit.longitude.value = longitude;
		                    		
		                    		$("#buildingEdit").collapse("show");
		                    	}
		                    	
		                    	var building_validation_option = {
		                    			rules : {
		                    				buildName : {
		                    					required : true,
		                    					minlength : 2,
		                    					maxlength : 20
		                    				},
		                    				buildFloorAmt : {
		                    					required : true,
		                    					number : true,
		                    					maxlength: 3
		                    				},
		                    				buildStairAmt : {
		                    					required : true,
		                    					number : true,
		                    					maxlength: 2
		                    				},
		                    				buildAddr : {
		                    					required : true,
		                    					minlength : 10,
		                    					maxlength : 50
		                    				},
		                    				latitude : {
		                    					number : true,
		                    					regex : /^[\d]{1,3}\.[\d]+$/
		                    				},
		                    				longitude : {
		                    					number : true,
		                    					regex : /^[\d]{1,3}\.[\d]+$/
		                    				}
		                    			},
		                    			messages : {
		                    				buildName : {
		                    					required : "건물명을 입력해주세요.",
		                    					minlength : "건물명을 2자리 이상으로 입력해주세요.",
		                    					maxlength : "건물명을 20자리 미만으로 입력해주세요."
		                    				},
		                    				buildFloorAmt : {
		                    					required : "충수를 입력해주세요.",
		                    					number : "숫자로 입력해주세요",
		                    					maxlength: "3자리 이하로 입력해주세요"
		                    				},
		                    				buildStairAmt : {
		                    					required : "층간 계단 수를 입력해주세요.",
		                    					number : "숫자로 입력해주세요",
		                    					maxlength: "2자리 이하로 입력해주세요"
		                    				},
		                    				buildAddr : {
		                    					required : "주소를 입력해주세요.",
		                    					minlength : "주소를 10자리 이상으로 입력해주세요.",
		                    					maxlength : "주소를 50자리 미만으로 입력해주세요."
		                    				},
		                    				latitude : {
		                    					number : "숫자로 입력해주세요",
		                    					regex : "입력형식을 확인해주세요"
		                    				},
		                    				longitude : {
		                    					number : "숫자로 입력해주세요",
		                    					regex : "입력형식을 확인해주세요"
		                    				}
		                    			}
		                    		};
		                    	
	                    		$(document.buildingAdd).validate(building_validation_option);
	                    		$(document.buildingEdit).validate(building_validation_option);
		                    	
		                    });
		                    	
	                    </script>

                    </c:if>
                    
                    <div class="text-center">
                    	<a href="${userListURL }" class="btn btn-fill btn-info">사용자 리스트 보기</a>
                    </div>

				</c:if>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

