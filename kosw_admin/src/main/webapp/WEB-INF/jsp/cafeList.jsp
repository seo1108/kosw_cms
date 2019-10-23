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
			<li class="active">Current Page</li>
		</ol>

		<form name="pageForm">
			<div class="input-group">
            	<input name="p" type="hidden" value="1">
            	<input name="search" type="hidden" value="">
        	</div>
        </form>

        <script>
        $(function(){
        	$(document.search).show();
        	setPage = function(page){
        		document.pageForm.p.value=page;
        		document.pageForm.submit();
        	}
        	
        	
        	$(".deleteForm").on("submit", function(){
				var form = this;										
				swal({
    				title : "정말로 삭제 하시겠습니까?",
    				text : "삭제하시면 해당 고객사의 모든 관리자는 삭제되며, 해당 고객사의 계단을 등록한 사용자는 등록한 계단을 더 이상 사용할 수 없습니다.",
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
	    		$("form[name=custApprovalEdit]").each(function(i,e){
	    			var form = e;
	    			var flag = $(e).find("input[name=approvalFlag]").eq(0).on('switchChange.bootstrapSwitch', function(event, state) {
	    			  $(form).submit();
	    			});
	    		});
	    		
	    		$("form[name=custApprovalEdit]").on("submit", function(){
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


        <div class="row">
            <div class="col-md-12">
                
				<c:if test="${not empty cafeList }">
                	<div class="card">
	                    
	                    <div class="header">
	                        <h4 class="title"></h4>
	                        <p class="category">Cafe 리스트</p>
	                    </div>
	                    
	                    <div class="content">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호</th>
										<th>카페명</th>
										<th>담당자명</th>
										<th>회원 수</th>
										<th>가입시 승인여부</th>
										<th>카페오픈일</th>
										<th></th>
									</tr>
									<c:forEach var="a" items="${cafeList }">
										<tr>
											<td>${a.cafeseq }</td>
											<td>
												<a href="<c:url value="/customerOne" />?cSeq=${a.cafeseq }">${a.cafename }</a>
											</td>
											<td>${a.adminname }</td>
											<td>${a.usercount }</td>
											<td>	
												<form name="custApprovalEdit" action="<c:url value="/custApprovalEdit"/>" method="post">
													<input name="cSeq" type="hidden" value="${a.cafeseq }">
													<div class="form-group checkboax">
														<input id="aaa" name="approvalFlag" value="Y" type="checkbox" <c:if test="${a.confirm eq 'Y' }">checked</c:if> data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
														<span class="toggle"></span>
													</div>
												</form>
											</td>
											<td>${a.opendate }</td>
											
											<td>
												<form class="deleteForm" method="post" action="<c:url value="deleteAdminCustomer"/>" style="display:inline-block;">
                                                	<%-- <input name="custSeq" type="hidden" value="${a.custSeq }"/>
                                                	<input name="adminSeq" type="hidden" value="${a.adminSeq }"/>
                                                	<input name="custName" type="hidden" value="${a.custName }"/> --%>
	                                                <button type="submit" class="btn btn-sm btn-danger btn-fill">
	                                                    삭제
	                                                </button>
                                                </form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
	                    </div>
	                  	                    
	                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                    </div> <!-- CARD-1 -->
                    
                     
				</c:if>
				<!-- 
				<div class="text-center">
					<a href='<c:url value="customerAdd" />' class="btn btn-primary btn-fill" >신규 고객사 등록</a>
				</div>
				 -->
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

