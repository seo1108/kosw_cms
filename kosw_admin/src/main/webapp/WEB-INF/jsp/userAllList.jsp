<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12">
            	
				
				<form name="pageForm">
					<div class="input-group">
                    	<input name="p" type="hidden" value="1">
                    	<input name="cSeq" type="hidden" value="${customer.custSeq}">
						<input name="search"  type="hidden" value="${param.search }">
		            	<input name="sort" type="hidden" value="${param.sort }">
		            	<input name="sortName" type="hidden" value="${param.sortName }">
                	</div>
                </form>

                <script>
                	$(function(){
	                	setPage = function(page){
	                		document.pageForm.p.value=page;
	                		document.pageForm.submit();
	                	}
	                	
	                	$(document.search).show();
	                	$(document.sort).show();
                	});
                </script>

				
               	<div class="card">
               		<div class="header">
                        <p class="category">사용자 리스트</p>
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
										<th>이름</th>
										<th>이메일</th>
										<th>닉네임</th>
										<th>등록일시</th>
										<th>메일인증여부</th>
										<th>이용건물수</th>
										<th>오른층수</th>
									</tr>
									<c:forEach var="a" items="${userList }">
										<tr>
											<td>${a.userSeq }</td>
											<td>
												<%-- <a href="statUser?userSeq=${a.userSeq }">${a.userName }</a> --%>
												<a href="statPerUser?uSeq=${a.userSeq }">${a.userName }</a>
											</td>
											<td>${a.userEmail }</td>
											<td>${a.nickName }</td>
											<td>${a.userRegTimeFormat }</td>
											<td>
												<c:if test="${a.authFinishFlag eq 'Y' }">
													<p class="text-primary">인증완료</p>
												</c:if>
												<c:if test="${a.authFinishFlag ne 'Y' }">
													<p class="text-danger">미인증</p>
												</c:if>
											</td>
											<td>${a.buildCnt }</td>
											<td>${a.sActAmt }</td>
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
								
							</script>
						</c:if>
                    </div>
                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                </div> <!-- CARD-1 -->
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

