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
            		<div class="header">
                        <p class="category">회사 정보</p>
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
                    <div class="header">
                        <div class="row">
							<div class="col-sm-8">
								<p class="category">계단 리스트</p>
							</div>
							<div class="col-sm-4">
								<form name="bSelectForm">
									<input type="hidden" name="cSeq" value="${beacon.custSeq }"/>
									<div>
				                    	<select name="bSeq" class="selectpicker" data-title="" data-style="btn-info btn-fill btn-block" data-menu-style="dropdown-blue">
				                    		<option value="">전체 건물 계단</option>
				                    		<c:forEach var="a" items="${buildingList }">
				                    			<option value="${a.buildSeq }" <c:if test="${beacon.buildSeq eq  a.buildSeq}">selected</c:if>>${a.buildName }</option>
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
                    	<c:if test="${empty buildingStairList }">
                    		<h5 class="text-danger">등록된 계단이 없습니다. 계단을 등록해주세요.</h5>
                    	</c:if>
                    	
						<c:if test="${not empty buildingStairList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>건물명</th>
										<th>계단명</th>
										<th>계단 등록일시</th>
									</tr>
									<c:forEach var="a" items="${buildingStairList }">
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
					    <div class="row">
							<div class="col-sm-8">
								<h4 class="title">비콘 리스트</h4>
								<p class="category text-danger">1.비콘을 수정하거나 추가하시려면 비콘등록 메뉴를 이용해주세요.</p>
								<p class="category text-danger">2.테이블의 비콘 고도를 변경하시면 바로 적용됩니다.</p>
								<p class="category text-danger">3.고도는 m 기준이며 실제 판별기준으로 작용하므로 신중하게 입력하셔야 합니다.</p>
							</div>
							<div class="col-sm-4">
								<form name="sSelectForm">
									<input type="hidden" name="cSeq" value="${beacon.custSeq }"/>
									<input type="hidden" name="bSeq" value="${beacon.buildSeq }"/>
									<div>
					                	<select name="sSeq" class="selectpicker" data-title="" data-style="btn-info btn-fill btn-block" data-menu-style="dropdown-blue">
					                		<option value="">전체 계단</option>
					                		<c:forEach var="a" items="${buildingStairList }">
					                			<option value="${a.stairSeq }" <c:if test="${beacon.stairSeq eq  a.stairSeq}">selected</c:if>>${a.stairName }</option>
					                		</c:forEach>
					                	</select>
					            	</div>
					            </form>
					           	<script>
					           		$(function(){
					           		 	$(document.sSelectForm.bSeq).on("change", function(){
					               			document.bSelectForm.submit();
					               		});
					           		});
					           	</script>
					        </div>
						</div>
					</div>


				<!-- 비콘 리스트에서 고도 변경시 바로 적용 -->
					<script>
						$(function(){
							$(".godoInput").on("keyup", function(){
								if (this.value == ""){
									return;
								}
								if (isNaN(this.value)){
									return;
								} 
								
								console.log(this.value);
								var beaconSeq = $(this).data("seq");
								var godo = this.value;
								
								$.ajax({
	            	                url: '<c:url value="/godoEdit"/>',
	            	                data : {"beaconSeq":beaconSeq, "godo":godo},
	            	                type: "post",
	            	                dataType: 'json',
	            	                success: function(data) {
	            	                	if (data.ok == "false"){
	            	                		swal({
	            	                   			title : "서버 에러가 발생하였습니다.",
	            	               	    		type: "error"
	            	               	    	}).catch(swal.noop);
	            	                	}
	            	                }
	            	            }); 
							});
							
							
							/* 비콘 활성화 / 비활성화 */
							$(".beaconEnableInput").on('switchChange.bootstrapSwitch', function(event, state) {
								var form = $(this).parents("form.beaconEnableForm");
								form = form[0];
								$.ajax({
	            	                url: form.action,
	            	                data : $(form).serialize(),
	            	                type: form.method,
	            	                dataType: 'json',
	            	                success: function(data) {
	            	                	if (data.ok == "false"){
	            	                		swal({
	            	                   			title : "서버 에러가 발생하였습니다.",
	            	               	    		type: "error"
	            	               	    	}).catch(swal.noop);
	            	                	}
	            	                }
	            	            });
								return false;
							});
							
						})
					</script>


                    <div class="content">
                    	
                    	<c:if test="${empty beaconList }">
                    		<p class="text-danger">등록된 비콘이 없습니다.</p>
                    	</c:if>
                    	
                    	<c:if test="${not empty beaconList }">
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
										<th>계단명</th>
										<th>모델명</th>
										<th>시리얼#1</th>
										<th>시리얼#2</th>
										<th>UUID</th>
										<th>MAJOR 값</th>
										<th>MINOR 값</th>
										<th>설치층</th>
										<c:if test="${user.superAdmin eq 'true'}">
											<th>활성화</th>
										</c:if>
										<th>고도(M)</th>
										<th>등록일시</th>
									</tr>
									<c:forEach var="a" items="${beaconList }">
										<tr>
											<td>${a.beaconSeq }</td>
											<td>${a.manufacName }</td>
											<td>${a.stairName }</td>
											<td>${a.modelName }</td>
											<td>${a.serialOne }</td>
											<td>${a.serialTwo }</td>
											<td>${a.beaconUUID }</td>
											<td>${a.majorValue }</td>
											<td>${a.minorValue }</td>
											<td>${a.installFloor }</td>
											<c:if test="${user.superAdmin eq 'true'}">
												<td>
													<form class="beaconEnableForm" action="<c:url value="/beaconEnable"/>" method="post">
														<input name="beaconSeq" type="hidden" value="${a.beaconSeq }">
														<input class="beaconEnableInput" name="enabled" value="Y" type="checkbox" <c:if test="${a.enabled eq 'Y' }">checked</c:if> data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
													</form>
												</td>
											</c:if>
											<td><input style="width:80px;" data-seq="${a.beaconSeq }" class="form-control godoInput" type="number" value="${a.godo }"></td>
											<td>${a.beaconRegTimeFormat }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							
                    	</c:if>
                    </div>
                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                </div> <!-- CARD-1 -->
                    
				<div class="text-center">
					<form method="get" action="${beaconAddURL }">
						<input name="cSeq" type="hidden" value="${beacon.custSeq }">
						<input name="bSeq" type="hidden" value="${beacon.buildSeq }">
						<input name="sSeq" type="hidden" value="${beacon.stairSeq }">
						<button type="submit" class="btn btn-primary btn-fill" >비콘 등록/수정</button>
					</form>
				</div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

