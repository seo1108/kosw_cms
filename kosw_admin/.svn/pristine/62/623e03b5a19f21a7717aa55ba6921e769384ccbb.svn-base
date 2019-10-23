<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-12">
            	<div class="card">
                    <div class="content">
                    	
                    	<c:if test="${empty beaconManufacList }">
                    		<p class="text-danger">등록된 제조사가 없습니다.</p>
                    	</c:if>
                    	
                    	<c:if test="${not empty beaconManufacList }">
							<form name="pageForm">
		                    	<input name="p" type="hidden" value="1">
								<input name="search" type="hidden" value="${param.search }">
	                        </form>
			                        
	                        <script>
		                    	setPage = function(page){
		                    		document.pageForm.p.value=page;
		                    		document.pageForm.submit();
		                    	}
		                    </script>
		                    
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호</th>
										<th>제조사명</th>
										<th>담당자명</th>
										<th>담당자 이메일</th>
										<th>담당자 전화번호</th>
										<th>비고</th>
										<th>등록일시</th>
										<th>ACTION</th>
									</tr>
									<c:forEach var="a" items="${beaconManufacList }">
										<tr>
											<td>${a.manufacSeq }</td>
											<td>${a.manufacName }</td>
											<td>${a.manufacPostName }</td>
											<td>${a.manufacPostEmail }</td>
											<td>${a.manufacPostPhone }</td>
											<td>${a.manufacRemarks }</td>
											<td>${a.manufacRegTimeFormat }</td>
											<td class="tx-actions text-right">
												<c:url var="beaconManufacEditURL" value="/beaconManufacEdit">
													<c:param name="mSeq" value="${a.manufacSeq }"/>
												</c:url>
												<a href="${beaconManufacEditURL }" rel="tooltip" title="수정" class="btn btn-success btn-simple btn-xs" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                
                                                <c:url var="beaconManufacDeleteURL" value="/beaconManufacDelete" />
                                                <form class="deleteForm" action="${beaconManufacDeleteURL }" method="post" style="display:inline-block;">
                                                	<input name="manufacSeq" type="hidden" value="${a.manufacSeq }" />
	                                                <button type="submit" title="삭제" class="btn btn-danger btn-simple btn-xs" >
	                                                    <i class="fa fa-times"></i>
	                                                </button>
                                                </form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							
							<script>
								$(function(){
									$("form.deleteForm").on("submit", function(){
										var form = this;
										swal({
		                    				title : "정말로 삭제 하시겠습니까?",
		                    				text : "삭제하시면 해당 제조사로 등록된 비콘이 모두 삭제됩니다.",
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
							
                    	</c:if>
                    </div>
                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                </div> <!-- CARD-1 -->
                    
				
				<div class="text-center">
					<a href='${beaconManufacAddURL}' class="btn btn-primary btn-fill" >신규 제조사 등록</a>
				</div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

