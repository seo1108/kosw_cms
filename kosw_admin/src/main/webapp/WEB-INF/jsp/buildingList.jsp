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
            	<input name="search" type="hidden" value="${param.search }">
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
        
        function buildingListDownload() {
	    	event.preventDefault();
			$('form#ReqForm').attr('action', 'buildingList/download');
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
												<c:if test="${not empty buildingList }">
												<button type="submit" style="float:left;margin-left:10px;" class="btn btn-fill btn-success" onclick="buildingListDownload();">파일 DOWN</button>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</form>
                	</div>
                </div>
                
                
                
                
				<c:if test="${not empty buildingList }">
                	<div class="card">
	                    
	                    <div class="header">
	                        <h4 class="title"></h4>
	                        <p class="category">건물 리스트</p>
	                    </div>
	                    
	                    <div class="content">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호 </th>
										<th>건물명</th>
										<th>주소</th>
										<th>위도</th>
										<th>경도</th>
										<th>층수</th>
										<th>층간계단</th>
										<th>등록일시</th>
										<th>오른층수</th>
										<th>이용날짜</th>
										<th>ACTION</th>
										<th></th>
									</tr>
									<c:forEach var="a" items="${buildingList }">
										<tr>
											<td>${a.buildSeq }</td>
											<td>
												<c:url value="/building" var="buildingUrl">
													<c:param name="cSeq" value="${customer.custSeq }" />
													<c:param name="bSeq" value="${a.buildSeq }" />
												</c:url>
												<a href="${buildingUrl }">${a.buildName }</a>
											</td>
											<td>${a.buildAddr }</td>
											<td>${a.latitude }</td>
											<td>${a.longitude }</td>
											<td>${a.buildFloorAmt }</td>
											<td>${a.buildStairAmt }</td>
											<td>${a.buildRegTimeFormat }</td>
											<td>${a.sActAmt }</td>
											<td>${a.sActDateFormat }</td>
											<td class="tx-actions text-right">
                                                <a onclick="buildingEditShow('${a.buildSeq}', '${customer.custSeq}', '${a.buildName}' , '${a.buildFloorAmt}', '${a.buildStairAmt}', '${a.buildAddr}', '${a.latitude}', '${a.longitude}', '${a.placeId}');"  rel="tooltip" title="수정" class="btn btn-primary btn-simple btn-icon edit" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                            </td>
											<td>
                                                <form class="deleteForm" method="post" action="<c:url value='/buildingDelete'/>" style="display:inline-block;">
                                                	<input name="buildSeq" type="hidden" value="${a.buildSeq }"/>
                                                	<input name="custSeq" type="hidden" value="${customer.custSeq }">
                                                 <input name="returnUrl" type="hidden" value="redirect:buildingList">
                                                	
	                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
	                                                    <i class="fa fa-times"></i>
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
				    <div class="text-center">
					    <c:if test="${user.superAdmin eq 'true'}">
					     	<a data-target="#buildingAdd"  data-toggle="collapse">
					        		신규 건물 등록<b class="caret"></b>
					    		</a>
					    	</c:if>
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
		                   		<input name="returnUrl" type="hidden" value="redirect:buildingList">
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
		                    			<label class="col-sm-2 control-label">구글 건물 ID</label>
		                    			<div class="col-sm-10">
		                    				<input name="placeId" type="text" class="form-control" autocomplete="off">
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
		                    		<input name="returnUrl" type="hidden" value="redirect:buildingList">
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
		                    			<label class="col-sm-2 control-label">구글 건물 ID</label>
		                    			<div class="col-sm-10">
		                    				<input name="placeId" type="text" class="form-control" autocomplete="off">
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
		                    	buildingEditShow = function(buildSeq, custSeq, buildName , buildFloorAmt, buildStairAmt, buildAddr, latitude, longitude,placeId){
		                    		document.buildingEdit.buildSeq.value = buildSeq;
		                    		document.buildingEdit.custSeq.value = custSeq;
		                    		document.buildingEdit.buildName.value = buildName;
		                    		document.buildingEdit.buildFloorAmt.value = buildFloorAmt;
		                    		document.buildingEdit.buildStairAmt.value = buildStairAmt;
		                    		document.buildingEdit.buildAddr.value = buildAddr;
		                    		document.buildingEdit.latitude.value = latitude;
		                    		document.buildingEdit.longitude.value = longitude;
		                    		document.buildingEdit.placeId.value = placeId;
		                    		
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

