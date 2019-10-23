<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<script src='<c:url value="/assets/js/chartist.min.js" />'></script>
<script src='<c:url value="/assets/js/chartist-plugin-barlabels.min.js" />'></script>


<div class="main-content">
    <div class="container-fluid">
		
		<form name="pageForm">
			<div class="input-group">
              	<input name="cSeq" type="hidden" value="${param.cSeq}">
				<input name="bSeq" type="hidden" value="${param.bSeq}">
          		<input name="sort" type="hidden" value="${sort}">
            </div>
        </form>
        
        <script>
        	$(document.sort).show();
        	$(document.search).hide();
        	
        	function individualRankDownload() {
    	    	event.preventDefault();
    			$('form#ReqForm').attr('action', 'building/download');
    			$('form#ReqForm').submit();
    		}      
       </script>
		
		
			
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
							    <c:if test="${not empty customerList }">
								<c:forEach var="a" items="${customerList }">
								<tr>
									<td>${a.custSeq }</td>
									<td>${a.custName }</td>
									<td>${a.postName }</td>
									<td>${a.postEmail }</td>
									<td>${a.postPhone }</td>
									<td>${a.custRemarks }</td>
									<td>${a.custRegTimeFormat }</td>
								</tr>
								</c:forEach>
								</c:if>
							    <c:if test="${empty customerList }">
								<tr>
									<td>${customer.custSeq }</td>
									<td>${customer.custName }</td>
									<td>${customer.postName }</td>
									<td>${customer.postEmail }</td>
									<td>${customer.postPhone }</td>
									<td>${customer.custRemarks }</td>
									<td>${customer.custRegTimeFormat }</td>
								</tr>
								</c:if>
						</table>
					</div>	
				</div>

                <div class="card">
                    <div class="header">
                        <p class="category">건물 정보</p>
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
                	<div class="header">
                        <h4 class="title"></h4>
                    </div>
                    
                	<div class="content">
                		
                	</div>
                </div>

				<div class="card">
	                    
                    <div class="header">
                    	<div style="float:left;">
                        <h4 class="title">개인별 랭킹</h4>
                        </div>
                        <div style="float:right;">
	                        <form name="searchForm" method="get" class="form-horizontal">
								<input name="cSeq" type="hidden" value="${param.cSeq}">
								<input name="bSeq" type="hidden" value="${param.bSeq}">
	          					<input name="sort" type="hidden" value="${sort}">
	          					<c:if test="${not empty ranks }">
								<button type="submit" style="float:left;margin-left:10px;" class="btn btn-fill btn-success" onclick="individualRankDownload();">파일 DOWN</button>
								</c:if>
							</form>
						</div>
                       
                    </div>
	                  
                    <div class="content">
                    	<p class="category" style="padding-top:20px;padding-bottom:10px;">전체 ${totalAmount } 층 오름</p>   
                    	<c:if test="${empty ranks }">
                    		<p class="text-danger">현재 랭킹 데이타가 없습니다.</p>
                    	</c:if>
                    	<c:if test="${not empty ranks }">
                    		<div class="table-responsive">
								<table class="table">
									<tr>
										<th>랭킹</th>
										<th>이름</th>
										<th>닉네임</th>
										<th>회사명</th>
										<th>부서명</th>
										<th>총 오른 층수</th>
										<th>ACTION</th>
									</tr>
									<c:forEach var="r" items="${ranks }">
										<tr>
											<td>${r.ranking }</td>
											<td>${r.userName }</td>
											<td>${r.nickName }</td>
											<td>${r.custName }</td>
											<td>${r.deptName }</td>
											<td>${r.recordAmount }</td>
											<td class="tx-actions text-center">
                                                <a href="statUser?userSeq=${r.userSeq }"><button type="button"rel="tooltip" title="상세" class="btn btn-primary btn-simple btn-icon">
                                                    <i class="fa fa-area-chart"></i>
                                                </button></a>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							
						 <!-- <div id="chartViews" class="ct-chart "></div> -->
							
                    	</c:if>
                    	
                    </div>
	                    
				</div> <!-- CARD-1 -->
<!--

                <div class="card">
                    <div class="header">
                        <div class="row">
							<div class="col-sm-8">
								<p class="category">계단 리스트</p>
							</div>
							<div class="col-sm-4">
								<form name="bSelectForm">
									<input type="hidden" name="cSeq" value="${param.cSeq }"/>
									<div>
				                    	<select name="bSeq" class="selectpicker" data-title="" data-style="btn-info btn-fill btn-block" data-menu-style="dropdown-blue">
				                    		<option value="">전체 건물 계단</option>
				                    		<c:forEach var="a" items="${buildingList }">
				                    			<option value="${a.buildSeq }" <c:if test="${param.bSeq eq  a.buildSeq}">selected</c:if>>${a.buildName }</option>
				                    		</c:forEach>
				                    	</select>
		                        	</div>
		                        </form>
		                       	<script>
		                       		$(function(){
		                       		 	$(document.bSelectForm.bSeq).on("change", function(){
			                       			document.bSelectForm.submit();
			                       		});
		                       		});
		                       	</script>
	                        </div>
                    	</div>
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
										<th>ACTION</th>
									</tr>
									<c:forEach var="a" items="${stairList }">
										<tr>
											<td>${a.buildName }</td>
											<td>${a.stairName }</td>
											<td>${a.stairRegTimeFormat }</td>
											<td class="tx-actions text-right">
											
												<c:url var="customerBeaconListURL" value="/beaconList">
													<c:param name="cSeq" value="${a.custSeq }"/>
													<c:param name="bSeq" value="${a.buildSeq }"/>
													<c:param name="sSeq" value="${a.stairSeq }"/>
												</c:url>
												<a href="${customerBeaconListURL }" rel="tooltip" title="비콘" class="btn btn-danger btn-simple">
                                                    <i class="pe-7s-signal"></i>
                                                </a>
                                                
                                                <a onclick="stairEditShow('${a.stairSeq}', '${a.buildSeq}', '${a.stairName}');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <form class="deleteForm" method="post" action="<c:url value='/stairDelete'/>" style="display:inline-block;">
                                                	<input name="stairSeq" type="hidden" value="${a.stairSeq }"/>
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
	                    	<a data-target="#stairAdd"  data-toggle="collapse">
                        		신규 계단 등록<b class="caret"></b>
                    		</a>
	                    </div>

                    </div>
                </div>
-->                
                <script>
            	$(function(){
            		
            		stairEditShow = function(stairSeq, buildSeq, stairName){
                		document.stairEdit.stairSeq.value = stairSeq;
                		document.stairEdit.buildSeq.value = buildSeq;
                		document.stairEdit.stairName.value = stairName;
                		
                		$("#stairEdit").collapse("show");
                	}
            		
            		
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
                
                <div id="stairEdit" class="card card-collapse collapse">
                    <div class="header">
                    	<div class="row">
                    		<div class="col-sm-8">
                    			<p class="category">계단 수정</p>
                    		</div>
                    		<div class="col-sm-4">
                    			<a data-target="#stairEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
                    		</div>
                    	</div>
                    </div>
                    <div class="content">
                    	<c:url var="stairEdit" value="/stairEdit"/>
                    	<form name="stairEdit" method="POST" action="${stairEdit }" class="form-horizontal">
                    		<input type="hidden" name="stairSeq" value="">
                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">건물 선택</label>
                    			<div class="col-sm-10">
                    				<select name="buildSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
                    					<c:forEach var="a" items="${buildingList }">
											<option value="${a.buildSeq }" >${a.buildName }</option>
										</c:forEach>
                    				</select>
                    			</div>
                    		</div>
                    		</fieldset>

                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">계단 명</label>
                    			<div class="col-sm-10">
                    				<input name="stairName" type="text" class="form-control" autocomplete="off">
                    			</div>
                    		</div>
                    		</fieldset>
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-danger btn-fill btn-wd">수정</button>
                    		</div>
                    		
                    	</form>
                    </div>
                </div>

                <div id="stairAdd" class="card card-collapse collapse">
                    <div class="header">
                    	<p class="category">신규 계단 등록</p>
                    </div>
                    <div class="content">
                    	<c:url var="stairAdd" value="/stairAdd"/>
                    	<form name="stairAdd" method="POST" action="${stairAdd }" class="form-horizontal">
                    		<input type="hidden" name="custSeq" value="${customer.custSeq }">
                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">건물 선택</label>
                    			<div class="col-sm-10">
                    				<select name="buildSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
                    					<c:forEach var="a" items="${buildingList }">
											<option value="${a.buildSeq }" <c:if test="${param.bSeq eq a.buildSeq}">selected</c:if> >${a.buildName }</option>
										</c:forEach>
                    				</select>
                    			</div>
                    		</div>
                    		</fieldset>

                    		<fieldset>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">계단 명</label>
                    			<div class="col-sm-10">
                    				<input name="stairName" type="text" class="form-control" autocomplete="off">
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
                		$(document.stairAdd).validate({
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
    
    <form id="ReqForm">
    	<input name="cSeq" type="hidden" value="${param.cSeq}">
		<input name="bSeq" type="hidden" value="${param.bSeq}">
   		<input name="sort" type="hidden" value="${param.sort}">
	</form>	
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

