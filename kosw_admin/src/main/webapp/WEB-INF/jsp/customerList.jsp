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
				<input name="search"  type="hidden" value="${param.search }">
            	<input name="sort" type="hidden" value="${param.sort }">
            	<input name="sortName" type="hidden" value="${param.sortName }">
            	<input name="startDate" type="hidden" value="${param.startDate }"/>
				<input name="endDate" type="hidden" value="${param.endDate }"/>
				<input name="reqType" type="hidden" value=""/>
        	</div>
        </form>

        <script>
        $(function(){
        	$(document.search).show();
        	$(document.sort).show();
        	setPage = function(page){
        		document.pageForm.p.value=page;
        		document.pageForm.submit();
        	}
        	
        	$("#searchForm").on("submit", function(){
        		var startDate = document.searchForm.startDate.value;
        		var endDate = document.searchForm.endDate.value;
        		if (startDate == ""){
        			alert("조회 시작일을 입력해주세요.");
        			return false;
        		}
        		if (endDate == ""){
        			alert("조회 종료일을 입력해주세요.");
        			return false;
        		}
        		if (startDate > endDate){
        			alert("조회 종료일을 시작일보다 크게 입력해주세요.");
        			return false;
        		}
        		return true;
        	});
        	
        	
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
        
        function customerListDownload() {
	    	event.preventDefault();
			$('form#ReqForm').attr('action', 'customerList/download');
			$('form#ReqForm').submit();
		}      
        </script>


        <div class="row">
            <div class="col-md-12">
            	
            	
            	<div class="card">
                	<div class="header">
                        <h4 class="title"></h4>
                    </div>
                    
                	<div class="content">
                		<form name="searchForm" method="get" class="form-horizontal">
							<input name="p" type="hidden" value="1">
            				<input name="search" type="hidden" value="${param.search }">
            				<input name="sort" type="hidden" value="${param.sort }">
            				<input name="sortName" type="hidden" value="${param.sortName }">
							<fieldset>
								<div class="form-group">
									<label class="col-sm-2 control-label">조회 날짜 선택</label>
									<div class="col-sm-10">
										<div class="row">
											<label class="col-sm-1 control-label text-right">FROM:</label>
											<div class="col-sm-3">
												<input name="startDate" type="text" class="form-control datetimepicker" placeholder="조회 시작일자 선택해주세요" value="${startDate}">
											</div>
											<label class="col-sm-1 control-label text-right">TO:</label>
											<div class="col-sm-3">
												<input name="endDate" type="text" class="form-control datetimepicker" placeholder="조회 종료일자 선택해주세요" value="${endDate}">
											</div>
											
											<div class="col-sm-4 text-right">
												<input type="submit" style="float:left;" class="btn btn-fill btn-primary" value="조회">
												<c:if test="${not empty customerList }">
												<button type="submit" style="float:left;margin-left:10px;" class="btn btn-fill btn-success" onclick="customerListDownload();">파일 DOWN</button>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</form>
                	</div>
                </div>
                
                
				<c:if test="${not empty customerList }">
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
										<th>고객사명</th>
										<th>담당자명</th>
										<th>담당자 이메일</th>
										<th>담당자 전화번호</th>
										<th>관리자 수</th>
										<th>비고</th>
										<th>승인</th>
										<th>등록일시</th>
										<th>회원수</th>
										<th>ACTION</th>
										<th></th>
									</tr>
									<c:forEach var="a" items="${customerList }">
										<tr>
											<td>${a.custSeq }</td>
											<td>
												<a href="<c:url value="/customerOne" />?cSeq=${a.custSeq }">${a.custName }</a>
											</td>
											<td>${a.postName }</td>
											<td>${a.postEmail }</td>
											<td>${a.postPhone }</td>
											<td>${a.adminCount }</td>
											<td>${a.custRemarks }</td>
											<td>	
												<form name="custApprovalEdit" action="<c:url value="/custApprovalEdit"/>" method="post">
													<input name="cSeq" type="hidden" value="${a.custSeq }">
													<div class="form-group checkboax">
														<input id="aaa" name="approvalFlag" value="Y" type="checkbox" <c:if test="${a.approval_flag eq 'Y' }">checked</c:if> data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
														<span class="toggle"></span>
													</div>
												</form>
											</td>
											<td>${a.custRegTimeFormat }</td>
											<td>${a.userCount }</td>
											<td class="tx-actions text-right">
											<!-- 
												<c:url var="customerBeaconListURL" value="/beaconList">
													<c:param name="cSeq" value="${a.custSeq }"/>
												</c:url>
												<a href="${customerBeaconListURL }" rel="tooltip" title="비콘" class="btn btn-danger btn-simple">
                                                    <i class="pe-7s-signal"></i>
                                                </a>
                                             -->
                                                <c:url var="customerUserListURL" value="/userList">
													<c:param name="cSeq" value="${a.custSeq }"/>
												</c:url>
												<a href="${customerUserListURL }" rel="tooltip" title="사용자" class="btn btn-primary btn-simple">
                                                    <i class="pe-7s-users"></i>
                                                </a>
                                                
                                                <c:if test="${user.superAdmin eq 'true'}">
	                                                <c:url var="statURL" value="/statUser">
														<c:param name="custSeq" value="${a.custSeq }"/>
													</c:url>
													<a href="${statURL }" rel="tooltip" title="통계" class="btn btn-primary btn-simple">
	                                                    <i class="fa fa-bar-chart"></i>
	                                                </a>                                                
                                                </c:if>
											</td>
											<td>
												<form class="deleteForm" method="post" action="<c:url value="deleteAdminCustomer"/>" style="display:inline-block;">
                                                	<input name="custSeq" type="hidden" value="${a.custSeq }"/>
                                                	<input name="adminSeq" type="hidden" value="${a.adminSeq }"/>
                                                	<input name="custName" type="hidden" value="${a.custName }"/>
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
    
    <form id="ReqForm">
    	<input name="search" type="hidden" value="${param.search }">
		<input name="sort" type="hidden" value="${param.sort }">
		<input name="startDate" type="hidden" value="${startDate }"/>
		<input name="endDate" type="hidden" value="${endDate }"/>
		<input name="reqType" type="hidden" value="excel"/>
	</form>	
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

