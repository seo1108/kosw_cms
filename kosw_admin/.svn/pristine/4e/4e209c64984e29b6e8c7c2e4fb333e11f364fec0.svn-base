<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

		<!-- <ol class="breadcrumb"> -->
  			<!-- <li><a href="#">Breadcrumb 1</a></li>
			<li><a href="#">Breadcrumb 2</a></li> -->
			<!-- <li class="active">비콘 등록</li> -->
		<!-- </ol> -->

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
                        <p class="category">계단 리스트</p>
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
								<p class="category text-danger">1.비콘 등록시 각 건물 별로 UUID와 Major값은 동일하여야 합니다.</p>
								<p class="category text-danger">2.설치 층은 실제 설치한 층을 나타내며, minor 는 최저층을 기준으로 1부터 시작하여야 합니다.</p>
								<p class="category text-danger">3.설치 층과 Minor 값은 다른 비콘과 서로 중첩 되지 않도록 꼼꼼하게 입력해주세요. </p>
								<p class="category text-danger">4.고도는 m 기준이며 실제 판별기준으로 작용하므로 신중하게 측정하여 입력하셔야 합니다.</p>
							</div>
							<div class="col-sm-4">
								<div class="pull-right">
									<a onclick="beaconAddShow('', '${beacon.stairSeq}', '', '', '', '', '','','','' );" rel="tooltip" title="등록" class="btn btn-primary btn-simple">
										<i class="fa fa-plus"></i>신규등록
	                                </a>
								</div>
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
						})
					</script>

                    <div class="content">
                    	<c:if test="${empty beaconList }">
                    		<p class="text-danger">등록된 비콘이 없습니다.</p>
                    	</c:if>
                    	
                    	<c:if test="${not empty beaconList }">
		                    
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
										<th>고도(m)</th>
										<th>등록일시</th>
										<th>ACTION</th>
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
											<td><input style="width:80px;" data-seq="${a.beaconSeq }" class="form-control godoInput" type="number" value="${a.godo }"></td>
											<td>${a.beaconRegTimeFormat }</td>
											<td class="tx-actions text-right">
                                                
                                                <a onclick="beaconAddShow('${a.manufacSeq}', '${a.stairSeq}', '${a.modelName}', '${a.serialOne}', '${a.serialTwo}', '${a.serialTwo}', '${a.beaconUUID }','${a.majorValue }','${a.minorValue }','${a.installFloor }','${a.godo }' );"  rel="tooltip" title="등록" class="btn btn-primary btn-simple" style="display:inline-block;">
                                                    <i class="fa fa-plus"></i>카피등록
                                                </a>
                                                
                                                <a onclick="beaconEditShow('${a.beaconSeq}','${a.manufacSeq}', '${a.stairSeq}', '${a.modelName}', '${a.serialOne}', '${a.serialTwo}', '${a.serialTwo}', '${a.beaconUUID }','${a.majorValue }','${a.minorValue }','${a.installFloor }','${a.godo }');"  rel="tooltip" title="수정" class="btn btn-warning btn-simple" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>수정
                                                </a>
                                                
                                                <form class="deleteForm" method="post" action="<c:url value='/beaconDelete'/>" style="display:inline-block;">
                                                	<input name="custSeq" type="hidden" value="${beacon.custSeq }"/>
                                                	<input name="buildSeq" type="hidden" value="${a.buildSeq }"/>
                                                	<input name="stairSeq" type="hidden" value="${a.stairSeq }"/>
                                                	<input name="beaconSeq" type="hidden" value="${a.beaconSeq }"/>
	                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
	                                                    <i class="fa fa-times"></i>삭제
	                                                </button>
                                                </form>
                                            </td>
										</tr>
									</c:forEach>
								</table>
							</div>
							
                    	</c:if>
                    </div>
                </div> <!-- CARD-1 -->

				
                <div id="beaconAdd" class="card card-collapse collapse">
                    <div class="header">
                    	<p class="category">비콘 등록</p>
                    </div>
                    <div class="content">
                    	<c:url var="beaconAdd" value="/beaconAdd"/>
                    	<form name="beaconAdd" method="POST" action="${beaconAdd }" class="form-horizontal">
                    		<input name="custSeq" type="hidden" value="${beacon.custSeq }"/>
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">계단 선택</label>
	                    			<div class="col-sm-10">
		                    			<select id="stairSeq1" name="stairSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
				                    		<c:forEach var="a" items="${buildingStairList }">
				                    			<option value="${a.stairSeq }" >${a.stairName } - ${a.buildFloorAmt }</option>
				                    		</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비콘 제조사</label>
	                    			<div class="col-sm-10">
		                    			<select name="manufacSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
				                    		<c:forEach var="a" items="${beaconManufacList }">
				                    			<option value="${a.manufacSeq }" >${a.manufacName }</option>
				                    		</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">모델명</label>
	                    			<div class="col-sm-10">
	                    				<input name="modelName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">시리얼#1</label>
	                    			<div class="col-sm-10">
	                    				<input name="serialOne" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">시리얼#2</label>
	                    			<div class="col-sm-10">
	                    				<input name="serialTwo" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">UUID</label>
	                    			<div class="col-sm-10">
	                    				<input name="beaconUUID" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">MAJOR 값</label>
	                    			<div class="col-sm-10">
	                    				<input name="majorValue" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">MINOR 값</label>
	                    			<div class="col-sm-10">
	                    				<input name="minorValue" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">설치 층수</label>
	                    			<div class="col-sm-10">
	                    				<input name="installFloor" type="number" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">고도(m)</label>
	                    			<div class="col-sm-10">
	                    				<input name="godo" type="number" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
                    		</div>
                    		
                    	</form>
                    </div>
                </div>
                
                
                <div id="beaconEdit" class="card card-collapse collapse">
                    <div class="header">
                    	<p class="category">비콘 수정</p>
                    </div>
                    <div class="content">
                    	<c:url var="beaconEditUrl" value="/beaconEdit"/>
                    	<form name="beaconEdit" method="POST" action="${beaconEditUrl }" class="form-horizontal">
                    		<input name="custSeq" type="hidden" value="${beacon.custSeq }"/>
                    		<input name="beaconSeq" type="hidden" value=""/>
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">계단 선택</label>
	                    			<div class="col-sm-10">
		                    			<select name="stairSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
				                    		<c:forEach var="a" items="${buildingStairList }">
				                    			<option value="${a.stairSeq }" >${a.stairName } (총 층수 : ${a.buildFloorAmt })</option>
				                    		</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비콘 제조사</label>
	                    			<div class="col-sm-10">
		                    			<select name="manufacSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
				                    		<c:forEach var="a" items="${beaconManufacList }">
				                    			<option value="${a.manufacSeq }" >${a.manufacName }</option>
				                    		</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">모델명</label>
	                    			<div class="col-sm-10">
	                    				<input name="modelName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">시리얼#1</label>
	                    			<div class="col-sm-10">
	                    				<input name="serialOne" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">시리얼#2</label>
	                    			<div class="col-sm-10">
	                    				<input name="serialTwo" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">UUID</label>
	                    			<div class="col-sm-10">
	                    				<input name="beaconUUID" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">MAJOR 값</label>
	                    			<div class="col-sm-10">
	                    				<input name="majorValue" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">MINOR 값</label>
	                    			<div class="col-sm-10">
	                    				<input name="minorValue" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">설치 층수</label>
	                    			<div class="col-sm-10">
	                    				<input name="installFloor" type="number" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">고도(M)</label>
	                    			<div class="col-sm-10">
	                    				<input name="godo" type="number" class="form-control"  autocomplete="off">
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
                   		beaconAddShow = function(manufacSeq, stairSeq, modelName, serialOne, serialTwo, serialTwo, beaconUUID ,majorValue ,minorValue ,installFloor,godo){
                   			$(document.beaconAdd.manufacSeq).selectpicker("val", manufacSeq);
                   			$(document.beaconAdd.stairSeq).selectpicker("val", stairSeq);
                   			document.beaconAdd.modelName.value=modelName;
                   			document.beaconAdd.serialOne.value=serialOne;
                   			document.beaconAdd.serialTwo.value=serialTwo;
                   			document.beaconAdd.serialTwo.value=serialTwo;
                   			document.beaconAdd.beaconUUID.value=beaconUUID;
                   			document.beaconAdd.majorValue.value=majorValue;
                   			document.beaconAdd.minorValue.value=minorValue;
                   			document.beaconAdd.installFloor.value=installFloor;	
                   			document.beaconAdd.godo.value=godo;	
                   			
                   			$("#beaconAdd").collapse("show");
                   			$("#beaconEdit").collapse("hide");
                   			
                   			/* if (beaconUUID != ''){
                   				$(document.beaconAdd.beaconUUID).parent("div").addClass("has-error");
    	                		$(document.beaconAdd.beaconUUID).next("span").show();
                   			}else{
                   				$(document.beaconAdd.beaconUUID).parent("div").removeClass("has-error");
    	                		$(document.beaconAdd.beaconUUID).next("span").hide();
                   			} */
                   			
                   		}

                   		beaconEditShow = function(beaconSeq, manufacSeq, stairSeq, modelName, serialOne, serialTwo, serialTwo, beaconUUID ,majorValue ,minorValue ,installFloor, godo){
                   			document.beaconEdit.beaconSeq.value=beaconSeq;
                   			$(document.beaconAdd.manufacSeq).selectpicker("val", manufacSeq);
                   			$(document.beaconAdd.stairSeq).selectpicker("val", stairSeq);
                   			document.beaconEdit.modelName.value=modelName;
                   			document.beaconEdit.serialOne.value=serialOne;
                   			document.beaconEdit.serialTwo.value=serialTwo;
                   			document.beaconEdit.serialTwo.value=serialTwo;
                   			document.beaconEdit.beaconUUID.value=beaconUUID;
                   			document.beaconEdit.majorValue.value=majorValue;
                   			document.beaconEdit.minorValue.value=minorValue;
                   			document.beaconEdit.installFloor.value=installFloor;
                   			document.beaconEdit.godo.value=godo;
                   			
                   			$("#beaconEdit").collapse("show");
                   			$("#beaconAdd").collapse("hide");
                   			
                   			/* $(document.beaconEdit.beaconUUID).parent("div").removeClass("has-error");
	                		$(document.beaconEdit.beaconUUID).next("span").hide(); */
	                		
                   			
                   		}
                    	
                    	
                    	var beacon_validate_option = {
                    			rules : {
                    				stairSeq : {
                    					required : true
                    				},
                    				installFloor : {
                    					required : true,
                    					number : true,
                    					min : 1,
                    					max : 300
                    				},
                    				manufacSeq : {
                    					required : true,
                    				},
                    				modelName : {
                    					required : true,
                    					minlength : 2,
                    					maxlength : 30
                    				},
                    				serialOne : {
                    					required : true,
                    					minlength : 2,
                    					maxlength : 100
                    				},
                    				serialTwo : {
                    					required : true,
                    					minlength : 2,
                    					maxlength : 100
                    				},
                    				beaconUUID : {
                    					required : true,
                    					minlength : 2,
                    					maxlength : 100
                    				},
                    				majorValue : {
                    					required : true,
                    					minlength : 1,
                    					maxlength : 100
                    				},
                    				minorValue : {
                    					required : true,
                    					minlength : 1,
                    					maxlength : 100
                    				}
                    			},
                    			messages : {
                    				stairSeq : {
                    					required : "계단을 선택해주세요."
                    				},
                    				installFloor : {
                    					required : "설치 층을 입력해주세요.",
                    					number : "설치 층을 숫자로 입력해주세요.",
                    					min : "설치 층을 1 이상으로 입력해주세요.",
                    					max : "설치 층을 300 이하로 입력해주세요."
                    				},
                    				manufacSeq : {
                    					required : "제조사를 선택해주세요.",
                    				},
                    				modelName : {
                    					required : "모델명을 입력해주세요.",
                    					minlength : "모델명을 2자 이상으로 입력해주세요.",
                    					maxlength : "모델명을 30자 이하로 입력해주세요."
                    				},
                    				serialOne : {
                    					required : "시리얼 #1 을 입력해주세요.",
                    					minlength : "시리얼 #1 을 2자 이상으로 입력해주세요.",
                    					maxlength : "시리얼 #1 을 100자 이하로 입력해주세요."
                    				},
                    				serialTwo : {
                    					required : "시리얼 #2 을 입력해주세요.",
                    					minlength : "시리얼 #2 을 2자 이상으로 입력해주세요.",
                    					maxlength : "시리얼 #2 을 100자 이하로 입력해주세요."
                    				},
                    				beaconUUID : {
                    					required : "UUID 를 입력해주세요.",
                    					minlength : "UUID 를 2자 이상으로 입력해주세요.",
                    					maxlength : "UUID 를 100자 이하로 입력해주세요."
                    				},
                    				majorValue : {
                    					required : "MAJOR 값을 입력해주세요.",
                    					minlength : "MAJOR 값을 2자 이상으로 입력해주세요.",
                    					maxlength : "MAJOR 값을 100자 이하로 입력해주세요."
                    				},
                    				minorValue : {
                    					required : "MINOR 값을 입력해주세요.",
                    					minlength : "MINOR 값을 2자 이상으로 입력해주세요.",
                    					maxlength : "MINOR 값을 100자 이하로 입력해주세요."
                    				}
                    			}
                    		};
                    	
                		$(document.beaconAdd).validate(beacon_validate_option);
                		$(document.beaconEdit).validate(beacon_validate_option);
                		
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
                		
                		// UUID 중복체크
                		/*
                		function checkUUID(input){
                			return true;
                 			if (input.value.length < 3){
                				return false;
                			}
        					$.ajax({
            	                url: '<c:url value="/beaconUuidCheck"/>',
            	                data : {"uuid":input.value},
            	                type: "post",
            	                dataType: 'json',
            	                success: function(data) {
            	                	if (data.ok == "true"){
            	                		$(input).parent("div").removeClass("has-error");
            	                		$(input).next("span").hide();
            	                	}else{
            	                		$(input).parent("div").addClass("has-error");
            	                		$(input).next("span").show();
            	                	}
            	                }
            	            }); 
        				}*/
                		
                		
                		var checkUuidAddLength = 0;
                		var checkUuidEditLength = 0;
                		/* $(document.beaconAdd.beaconUUID).on("keyup", function(){
                			var input = this;
                			if (this.value.length > 1){
                				if (checkUuidAddLength == this.value.length){
                					return false;
                				}
                				checkUuidAddLength = this.value.length;
                				checkUUID(input);
                			}
                		}); */
                		
                		/* $(document.beaconEdit.beaconUUID).on("keyup", function(){
                			var input = this;
                			if (this.value.length > 1){
                				if (checkUuidEditLength == this.value.length){
                					return false;
                				}
                				checkUuidEditLength = this.value.length;
                				checkUUID(input);
                			}
                		}); */
                		
                		// 층, 계단 중복 체크
                		/*
                		function checkFloor(input, floor, stair){
                			if (floor.length == 0){
                				return false;
                			}
                			if (stair.length == 0){
                				return false;
                			}
                			
        					$.ajax({
            	                url: '<c:url value="/beaconFloorCheck"/>',
            	                data : {"floor":floor , "stair":stair},
            	                type: "post",
            	                dataType: 'json',
            	                success: function(data) {
            	                	if (data.ok == "true"){
            	                		$(input).parent("div").removeClass("has-error");
            	                		$(input).next("span").hide();
            	                	}else{
            	                		$(input).parent("div").addClass("has-error");
            	                		$(input).next("span").show();
            	                	}
            	                }
            	            });
        				} 
                		*/
                		
                		
                		$(document.beaconEdit.installFloor).on("keyup", function(){
                			if (this.value == ""){
                				return false;
                			}
                			if (isNaN(this.value)){
                				return false;
                			}
							var input = document.beaconEdit.installFloor;
							var installFloor = input.value;
							var stairSeq = document.beaconEdit.stairSeq.value;
							//checkFloor(input, installFloor, stairSeq);
							
                		});
                		
                		$(document.beaconEdit.stairSeq).on("changed.bs.select", function(){
                			var input = document.beaconEdit.installFloor;
                			var installFloor = input.value;
                			var stairSeq = document.beaconEdit.stairSeq.value;
                			//checkFloor(input, installFloor, stairSeq);
                		});
                		
                		
                		$(document.beaconAdd.installFloor).on("keyup", function(){
                			if (this.value == ""){
                				return false;
                			}
                			if (isNaN(this.value)){
                				return false;
                			}
							var input = document.beaconAdd.installFloor;
							var installFloor = input.value;
							var stairSeq = document.beaconAdd.stairSeq.value;
							//checkFloor(input, installFloor, stairSeq);
							
                		});
                		
                		$(document.beaconAdd.stairSeq).on("changed.bs.select", function(){
                			var input = document.beaconAdd.installFloor;
                			var installFloor = input.value;
                			var stairSeq = document.beaconAdd.stairSeq.value;
                			//checkFloor(input, installFloor, stairSeq);
                		});
                		
                   	});
                    	
                </script>
	                    
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

