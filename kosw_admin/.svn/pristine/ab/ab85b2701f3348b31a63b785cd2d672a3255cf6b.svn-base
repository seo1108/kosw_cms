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
						<p class="category">검색 조건</p>
            		</div>
            		<div class="content">
            			<form name="beaconSearchForm" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">고객사 선택</label>
								<div class="col-sm-10">
				                	<select name="custSeq" class="selectpicker" data-title="" data-style="btn-default  btn-block" data-menu-style="dropdown-blue">
				                		<option value="">고객사 전체</option>
				                		<c:forEach var="a" items="${customerAll }">
				                			<option value="${a.custSeq }" <c:if test="${beacon.custSeq eq a.custSeq }">selected</c:if> >${a.custName }</option>
				                		</c:forEach>
				                	</select>
								</div>
			            	</div>
			            	
			            	<c:if test="${not empty  stairList }">
				            	<div class="form-group">
									<label class="col-sm-2 control-label">계단 선택</label>
									<div class="col-sm-10">
					                	<select name="stairSeq" class="selectpicker" data-title="" data-style="btn-default  btn-block" data-menu-style="dropdown-blue">
					                		<option value="">계단 전체</option>
					                		<c:forEach var="a" items="${stairList }">
					                			<option value="${a.stairSeq }" <c:if test="${beacon.stairSeq eq a.stairSeq }">selected</c:if> >${a.stairName }</option>
					                		</c:forEach>
					                	</select>
									</div>
				            	</div>
			            	</c:if>
			            	
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">설치 층수</label>
                    			<div class="col-sm-10">
                    				<input name="installFloor" type="number" class="form-control"  autocomplete="off" value="${beacon.installFloor }">
                    			</div>
                    		</div>
                    		
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">비콘 제조사</label>
                    			<div class="col-sm-10">
	                    			<select name="manufacSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
	                    				<option value="">제조사 전체</option>
			                    		<c:forEach var="a" items="${beaconManufacList }">
			                    			<option value="${a.manufacSeq }" <c:if test="${beacon.manufacSeq eq a.manufacSeq }">selected</c:if> >${a.manufacName }</option>
			                    		</c:forEach>
			                    	</select>
                    			</div>
                    		</div>
                    		
                    		
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">모델명</label>
                    			<div class="col-sm-10">
                    				<input name="modelName" type="text" class="form-control"  autocomplete="off" value="${beacon.modelName }">
                    			</div>
                    		</div>
                    		
							<div class="form-group">
								<label class="col-sm-2 control-label">UUID</label>
								<div class="col-sm-10">
									<input name="beaconUUID" class="form-control" type="text" value="${beacon.beaconUUID }">
								</div>
			            	</div>
			            	<div class="form-group">
								<label class="col-sm-2 control-label">시리얼#1</label>
								<div class="col-sm-10">
									<input name="serialOne" class="form-control" type="text" value="${beacon.serialOne }">
								</div>
			            	</div>
			            	<div class="form-group">
								<label class="col-sm-2 control-label">시리얼#2</label>
								<div class="col-sm-10">
									<input name="serialTwo" class="form-control" type="text" value="${beacon.serialTwo }">
								</div>
			            	</div>
			            	<div class="form-group">
                    			<label class="col-sm-2 control-label">MAJOR 값</label>
                    			<div class="col-sm-10">
                    				<input name="majorValue" type="text" class="form-control"  autocomplete="off" value="${beacon.majorValue }">
                    			</div>
                    		</div>
                    		<div class="form-group">
                    			<label class="col-sm-2 control-label">MINOR 값</label>
                    			<div class="col-sm-10">
                    				<input name="minorValue" type="text" class="form-control"  autocomplete="off" value="${beacon.minorValue }">
                    			</div>
                    		</div>
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-wd btn-primary btn-fill">
                    				<span class="btn-label"><i class="fa fa-search"></i></span>
                                            검색
								</button>
                    		</div>
			            </form>
			            
			            
			           	<script>
			           		$(function(){
			           		 	$(document.beaconSearchForm.custSeq).on("change", function(){
			               			document.beaconSearchForm.submit();
			               		});
			           		 	
			           		 	
			           		 var beacon_validate_option = {
		                    			rules : {
		                    				installFloor : {
		                    					min : 1,
		                    					max : 300
		                    				},
		                    				modelName : {
		                    					minlength : 2,
		                    					maxlength : 30
		                    				},
		                    				serialOne : {
		                    					minlength : 2,
		                    					maxlength : 100
		                    				},
		                    				serialTwo : {
		                    					minlength : 2,
		                    					maxlength : 100
		                    				},
		                    				beaconUUID : {
		                    					minlength : 2,
		                    					maxlength : 100
		                    				},
		                    				majorValue : {
		                    					minlength : 1,
		                    					maxlength : 100
		                    				},
		                    				minorValue : {
		                    					minlength : 1,
		                    					maxlength : 100
		                    				}
		                    			},
		                    			messages : {
		                    				installFloor : {
		                    					number : "설치 층을 숫자로 입력해주세요.",
		                    					min : "설치 층을 1 이상으로 입력해주세요.",
		                    					max : "설치 층을 300 이하로 입력해주세요."
		                    				},
		                    				modelName : {
		                    					minlength : "모델명을 2자 이상으로 입력해주세요.",
		                    					maxlength : "모델명을 30자 이하로 입력해주세요."
		                    				},
		                    				serialOne : {
		                    					minlength : "시리얼 #1 을 2자 이상으로 입력해주세요.",
		                    					maxlength : "시리얼 #1 을 100자 이하로 입력해주세요."
		                    				},
		                    				serialTwo : {
		                    					minlength : "시리얼 #2 을 2자 이상으로 입력해주세요.",
		                    					maxlength : "시리얼 #2 을 100자 이하로 입력해주세요."
		                    				},
		                    				beaconUUID : {
		                    					minlength : "UUID 를 2자 이상으로 입력해주세요.",
		                    					maxlength : "UUID 를 100자 이하로 입력해주세요."
		                    				},
		                    				majorValue : {
		                    					minlength : "MAJOR 값을 2자 이상으로 입력해주세요.",
		                    					maxlength : "MAJOR 값을 100자 이하로 입력해주세요."
		                    				},
		                    				minorValue : {
		                    					minlength : "MINOR 값을 2자 이상으로 입력해주세요.",
		                    					maxlength : "MINOR 값을 100자 이하로 입력해주세요."
		                    				}
		                    			}
		                    		};
		                    	
		                		$(document.beaconSearchForm).validate(beacon_validate_option);
			           		});
			           	</script>
            		</div>
	           	</div>


				
            	<div class="card">
				    <div class="header">
						<p class="category">검색 결과</p>
           			</div>

                    <div class="content">
                    	
                    	<c:if test="${empty beaconList }">
                    		<p class="text-danger">검색된 비콘이 없습니다.</p>
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
										<th>고객사명</th>
										<th>제조사명</th>
										<th>계단명</th>
										<th>모델명</th>
										<th>시리얼#1</th>
										<th>시리얼#2</th>
										<th>UUID</th>
										<th>MAJOR 값</th>
										<th>MINOR 값</th>
										<th>설치층</th>
										<th>등록일시</th>
									</tr>
									<c:forEach var="a" items="${beaconList }">
										<tr>
											<td>${a.beaconSeq }</td>
											<td>${a.custName }</td>
											<td>${a.manufacName }</td>
											<td>${a.stairName }</td>
											<td>${a.modelName }</td>
											<td>${a.serialOne }</td>
											<td>${a.serialTwo }</td>
											<td>${a.beaconUUID }</td>
											<td>${a.majorValue }</td>
											<td>${a.minorValue }</td>
											<td>${a.installFloor }</td>
											<td>${a.beaconRegTimeFormat }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							
                    	</c:if>
                    </div>
                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                </div> <!-- CARD-1 -->
                    
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

