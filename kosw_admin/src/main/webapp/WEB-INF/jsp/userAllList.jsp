<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12">
            	
				
				

                <script>
                	$(function(){
	                	setPage = function(page){
	                		document.pageForm.p.value=page;
	                		document.pageForm.submit();
	                	}
	                	
	                	$(document.search).show();
	                	$(document.sort).show();
	                	
	                	// 달력
	            	    $('.datetimepicker').datetimepicker({
	                		locale : "ko",
	                		defaultDate : false,
	                		useCurrent : false,
	                		format : 'YYYYMMDD',
	                		dayViewHeaderFormat : "YYYY MM",
	                		minDate : moment().add(-2, 'year'),
	                		maxDate : moment().add(1,'day'),
	                	    icons: {
	                	        time: "fa fa-clock-o",
	                	        date: "fa fa-calendar",
	                	        up: "fa fa-chevron-up",
	                	        down: "fa fa-chevron-down",
	                	        previous: 'fa fa-chevron-left',
	                	        next: 'fa fa-chevron-right',
	                	        today: 'fa fa-screenshot',
	                	        clear: 'fa fa-trash',
	                	        close: 'fa fa-remove'
	                	    }
	                	});
                	});
                	
                	function download() {
            	    	event.preventDefault();
            			$('form#ReqForm').attr('action', 'userAllList/download');
            			$('form#ReqForm').submit();
            		}      
                	
                	
                </script>

				
               	<div class="card">
               		<div class="header">
                        <p class="category">사용자 리스트</p>
                    </div>
                    
                    <div class="col-md-8" style="float:left;padding-top:30px;">
                    	<form name="pageForm"  method="get">
							<div class="input-group">
		                    	<input name="p" type="hidden" value="1">
		                    	<input name="cSeq" type="hidden" value="${customer.custSeq}">
								<input name="search"  type="hidden" value="${param.search }">
				            	<input name="sort" type="hidden" value="${param.sort }">
				            	<input name="sortName" type="hidden" value="${param.sortName }">
				            	<input name="reqType" type="hidden" value=""/>
				            	<input name="startSearchDate" type="hidden" value="${param.startSearchDate }"/>
				            	<input name="startEndDate" type="hidden" value="${param.startEndDate }"/>
		                	</div>
                
                    	<label class="col-sm-2 control-label">날짜 선택${user.endDate } - ${user.startDate }</label>
							<div class="col-sm-12">
								<div class="row">
									<div class="col-sm-5">
										<input name="startUserDate" type="text" class="form-control datetimepicker" placeholder="조회 시작일자 선택해주세요" value="${startSearchDate }">
									</div>
									<label class="col-sm-1 control-label" style="text-align:center;"> ~ </label>
									<div class="col-sm-5">
										<input name="endUserDate" type="text" class="form-control datetimepicker" placeholder="조회 종료일자 선택해주세요" value="${endSearchDate }">
									</div>
									
 									<div style="text-align:right;">
										<input type="button" style="float:right;" class="btn btn-fill btn-primary" onclick="searchUser();" value="조회">
										
									</div>
 								</div>
							</div>
							</form>
                    </div>
                    
                    <div style="float:right;margin-bottom:20px;">
                    	<table style="width:300px;" class="table">
                    		<tr>
								<th>전체회원</th>
								<th>${userStatus.totalUser }</th>
							</tr>
							
							<tr>
								<th style="color:blue;">정상</th>
								<th style="color:blue;">${userStatus.normalUser }</th>
							</tr>
							
							<tr>
								<th style="color:red;">탈퇴</th>
								<th style="color:red;">${userStatus.withedrawalUser }</th>
							</tr>
                    	</table>
                    </div>
                    <div class="content">
                    	<c:if test="${empty userList }">
                    		<c:if test="${empty param.search}">
                    			<p class="text-danger">현재 등록된 사용자가 없습니다.</p>
                    		</c:if>
                    		<c:if test="${not empty param.search}">
                    			<p class="text-info">검색된 사용자가 없습니다.</p>
                    		</c:if>
                    		
                    	</c:if>
                    	<c:if test="${not empty userList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호</th>
										<th>아이디(이메일)</th>
										<th>사용자명</th>
										<th>닉네임</th>
										<th>등록일시</th>
										<!-- <th>메일인증여부</th> -->
										<th>가입경로</th>
										<th>OS</th>
										<th>이용카페수</th>
										<th>오른층수</th>
										<th>걸음수</th>
									</tr>
									<c:forEach var="a" items="${userList }">
										<tr>
											<td>${a.userSeq }</td>
											<td>${a.userEmail }</td>
											<td>
												<%-- <a href="statUser?userSeq=${a.userSeq }">${a.userName }</a> --%>
												<a href="statPerUser?uSeq=${a.userSeq }">${a.userName }</a>
											</td>
											<td>${a.nickName }</td>
											<td>${a.userRegTimeFormat }</td>
											<td>${a.loginType }</td>
											<td>${a.deviceType }</td>
											<%-- <td>
												<c:if test="${a.authFinishFlag eq 'Y' }">
													<p class="text-primary">인증완료</p>
												</c:if>
												<c:if test="${a.authFinishFlag ne 'Y' }">
													<p class="text-danger">미인증</p>
												</c:if>
											</td> --%>
											<td>${a.cafeCnt }</td>
											<td>${a.sActAmt }</td>
											<td>${a.walkcount }</td>
											<!-- 
											<td>	
												<form name="userApprovalEdit" action="<c:url value="/userRegiEdit"/>" method="post">
													<input name="uSeq" type="hidden" value="${a.userSeq }">
													<input name="cSeq" type="hidden" value="${customer.custSeq }">
													<div class="form-group checkboax">
														<input id="aaa" name="approvalFlag" value="Y" type="checkbox" <c:if test="${a.approvalFlag eq 'Y' }">checked</c:if> data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
														<span class="toggle"></span>
													</div>
												</form>
											</td>
											<td>
												<form class="deleteForm" method="post" action="<c:url value="userDelete"/>" style="display:inline-block;">
                                                	<input name="custSeq" type="hidden" value="${customer.custSeq }"/>
                                                	<input name="userSeq" type="hidden" value="${a.userSeq }"/>
	                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
	                                                    <i class="fa fa-times"></i>
	                                                </button>
                                                </form>
											</td>
											-->
											
											
										</tr>
									</c:forEach>
								</table>
							</div>
							<script>
							
							
								
								$(function(){
									$(".deleteForm").on("submit", function(){
										var form = this;										
										swal({
						    				title : "정말로 삭제 하시겠습니까?",
						    				text : "삭제하시면 해당 사용자는 영구적으로 본 회사의 계단을 사용할 수 없으며 복구가 불가능합니다.",
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
									
									
									// https://github.com/Bttstrp/bootstrap-switch
									$("form[name=userApprovalEdit]").each(function(i,e){
										var form = e;
										var flag = $(e).find("input[name=approvalFlag]").eq(0).on('switchChange.bootstrapSwitch', function(event, state) {
										  $(form).submit();
										});
									});
									
									$("form[name=userApprovalEdit]").on("submit", function(){
										$.ajax({
							                url: this.action,
							                data : $(this).serialize(),
							                type: "post",
							                dataType: 'json',
							                success: function(data) {
							                	console.log(data);
							                	if (data.success == "true"){
							                		swal({
							            	    		title : "수정 되었습니다.",
							            	    		timer: 700,
							            	    		showConfirmButton: false,
							            	    		type: "success"
							            	    	}).catch(swal.noop);	
							                		
							                	}else{
							                		swal({
							            	    		title : "에러가 발생하였습니다.",
							            	    		showConfirmButton: true,
							            	    		type: "error"
							            	    	}).then(function(dismiss){
							            	    		location.reload();
							            	    	}).catch(swal.noop);
							                	}
							                }
							            });
										
										return false;
									});
								});
								
								function searchUser()
								{
									//$('.inputparam').val($(".dropdown-menu li p").data('value'));
									//$('.inputparamname').val($(".dropdown-menu li p").text());
									  
									 
									$('.inputStartparam').val($('input[name=startUserDate]').val());
									$('.inputEndparam').val($('input[name=endUserDate]').val());
									$("#search").submit() ;
								}
								
							</script>
						</c:if>
                    </div>
                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                </div> <!-- CARD-1 -->
                
                <c:if test="${not empty userList }">
					<button type="submit" class="btn btn-fill btn-success" onclick="download();">파일 DOWN</button>
				</c:if>
            </div>
        </div>
    </div>
    
	<form id="ReqForm">
    	<input name="search" type="hidden" value="${param.search }">
		<input name="sort" type="hidden" value="${param.sort }">
		<input name="sortName" type="hidden" value="${param.sortName }">
		<input name="reqType" type="hidden" value="excel"/>
	</form>	
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

