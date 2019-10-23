<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
        	
        	$('.datetimepicker').datetimepicker({
        		locale : "ko",
        		defaultDate : false,
        		useCurrent : false,
        		format : 'YYYY-MM-DD',
        		dayViewHeaderFormat : "YYYY MM",
        		minDate : moment(),
        		maxDate : moment().add(5, 'year'),
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
        	var pushOption = {
           		rules : {
           			pushTitle : {
           				required : true,
           				minlength : 2,
           				maxlength : 15
           			},
           			pushContent : {
           				required : true,
           				minlength : 2,
           				maxlength : 30
           			}
           		},
           		messages : {
           			pushTitle : {
           				required : "제목을 입력해주세요.",
           				minlength : "제목을 2자 이상으로 입력해주세요.",
           				maxlength : "제목을 15자 이하로 입력해주세요."
           			},
           			pushContent : {
           				required : "내용을 입력해주세요.",
           				minlength : "내용을 2자 이상으로 입력해주세요.",
           				maxlength : "내용을 30자 이하로 입력해주세요."
           			}
           		}
           	}
        	
        	$(document.pushAdd).validate(pushOption);
        	$(document.pushEdit).validate(pushOption);

        	showBssAdd = function(){
        		$("#pushAdd").collapse("show");
        		setTimeout(function(){
        			location.href = "#pushAdd";	
        		}, 300);
        	}
        	
        	showPushEdit = function(){
        		$("#pushEdit").collapse("show");
        		setTimeout(function(){
        			location.href = "#pushEdit";	
        		}, 300);
        	}
        	
        	$(document.pushEdit.custSeq).on("changed.bs.select", function(){
        		pushEditCustSeqChanged(this.value);
        	});
        	
        	$(document.pushAdd).on("submit", function(){
        		try {
        			
        			if ($(document.pushAdd.now).bootstrapSwitch("state") == true){
        				document.pushAdd.reserveTime.value = "";
        			}else{
        				var reserveTime = document.pushAdd.yyyyMMdd.value + document.pushAdd.hh.value + document.pushAdd.mm.value;
                		reserveTime = reserveTime.replace(/-/g, "")
                		if (reserveTime.length == 12){
                			document.pushAdd.reserveTime.value = reserveTime;	
                		}else{
                			document.pushAdd.reserveTime.value = "";
                			alert("즉시 발송을 하시거나 발송 예약 일시를 입력해주십시요.");
                			return false;
                		}	
        			}
        				
        		}catch(e){
        			console.log(e);
        			return false;
        		}
        	});
        	
        	$(document.pushEdit).on("submit", function(){
        		try {
        			if ($(document.pushEdit.now).bootstrapSwitch("state") == true){
        				document.pushEdit.reserveTime.value = "";
        			}else{
        				var reserveTime = document.pushEdit.yyyyMMdd.value + document.pushEdit.hh.value + document.pushEdit.mm.value;
                		reserveTime = reserveTime.replace(/-/g, "")
                		if (reserveTime.length == 12){
                			document.pushEdit.reserveTime.value = reserveTime;	
                		}else{
                			document.pushEdit.reserveTime.value = "";
                			alert("즉시 발송을 하시거나 발송 예약 일시를 입력해주십시요.");
                			return false;
                		}	
        			}
        				
        		}catch(e){
        			console.log(e);
        			return false;
        		}
        		
        	});
        	
        	
        	showDetail = function(pushSeq, title, content, reserveTime, custSeq, buildSeq){
        		
        		document.pushEdit.pushSeq.value = pushSeq;
				document.pushEdit.pushTitle.value = title;
				document.pushEdit.pushContent.value = content;
				
				document.pushEdit.reserveTime.value = reserveTime;
				$(document.pushEdit.yyyyMMdd).data("DateTimePicker").date(moment(reserveTime, "YYYYMMDD"));
				$(document.pushEdit.hh).selectpicker("val", moment(reserveTime, "YYYYMMDHHhmm").format("HH"));
				$(document.pushEdit.mm).selectpicker("val", moment(reserveTime, "YYYYMMDDHHmm").format("mm"));
				
				
				$(document.pushEdit.custSeq).selectpicker("val", custSeq);
				
				if (custSeq){
					pushEditCustSeqChanged(custSeq, function(){
						$(document.pushEdit.buildSeq).selectpicker("val", buildSeq);
					});
				}
				
				
				showPushEdit();
        	}
        	
        	
        	
        	function pushEditCustSeqChanged(custSeq, succssFunc){
				$.get("<c:url value='buildingSelectOption' />",{"custSeq" : custSeq} , function(response){
					console.log(response);
        			if (response.success == "true"){
						var buildingList =  response.buildingList;
						$(document.pushEdit.buildSeq).find("option").not("[value='']").remove();
						$.each(buildingList, function(i,e){
							var option = $("<option></option>");
							option.text(e.buildName);
							option.val(e.buildSeq); 
							$(document.pushEdit.buildSeq).append(option);
						});
						$(document.pushEdit.buildSeq).selectpicker('refresh');
        			
						if (succssFunc){
							succssFunc.call();
						}
        			}
        			
        		}).fail(function(e){
        			console.log(e);
        		});
			}
        	
        	
        	
        });
        </script>


        <div class="row">
            <div class="col-md-12">
                
				
            	<div class="card">
            		<div class="header">
                        <h4 class="title"></h4>
                        <p class="category">푸쉬메세지 리스트</p>
                    </div>
                    <div class="content">
                		<c:if test="${empty pushList }">
                			<p class="text-danger">등록된 푸쉬 메세지가 없습니다.</p>
                		</c:if>
                		<c:if test="${not empty pushList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>내용</th>
										<th>대상</th>
										<th>예약일자</th>
										<th>발송여부</th>
										<th>관리자</th>
										<th>등록일시</th>
										<th>ACTION</th>
									</tr>
									<c:forEach var="a" items="${pushList }">
										<tr>
											<td>${a.pushSeq }</td>
											<td>${a.pushTitle }</td>
											<td>${a.pushContent }</td>
											<td>
												<c:if test="${empty a.custName }">
													전체 대상
												</c:if>
												<c:if test="${not empty a.custName and empty a.buildName}">
													${a.custName } 전체
												</c:if>
												<c:if test="${not empty a.custName and not empty a.buildName}">
													${a.custName }-${a.buildName }
												</c:if>
											</td>
											<td>
												<c:if test="${not empty a.reserveTimeFormat }">
													${a.reserveTimeFormat }
												</c:if>
												<c:if test="${empty a.reserveTimeFormat }">
													<p class="text-primary">즉시발송</p>
												</c:if>
											</td>
											<td>
												<c:if test="${'Y' eq a.sentFlag }">
													<p class="text-primary">발송됨</p>
												</c:if>
												<c:if test="${'Y' ne a.sentFlag }">
													<p>대기</p>
												</c:if>
											</td>
											<td>${a.adminName }</td>
											<td>${a.pushRegTimeFormat }</td>
											<td class="tx-actions text-right">
											
												<c:if test="${a.sentFlag ne 'Y' }">
	                                                <a onclick="showDetail('${a.pushSeq}', '${a.pushTitle}', '${a.pushContent}', '${a.reserveTime}', '${a.custSeq}', '${a.buildSeq}');" rel="tooltip" title="수정" class="btn btn-warning btn-simple" style="display:inline-block;">
	                                                    <i class="fa fa-edit"></i>
	                                                </a>
                                               	</c:if>
                                                
                                                <form class="deleteForm" method="post" action="<c:url value="pushDelete"/>" style="display:inline-block;">
                                               		<input name="pushSeq" type="hidden" value="${a.pushSeq }"/>
	                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
	                                                    <i class="fa fa-times"></i>
	                                                </button>
                                                </form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
		                    <%@ include file="/WEB-INF/jsp/common/pagenation.jsp" %>
                		</c:if>
                		
                		<div class="text-center">
							<a href='javascript:showBssAdd();' class="btn btn-primary btn-fill" >신규 푸쉬 메세지 등록</a>
						</div>
            		</div>
					
                </div> <!-- CARD-1 -->



				<script>
					$(function(){
						
						$(document.pushAdd.custSeq).on("changed.bs.select", function(){
							$.get("<c:url value='buildingSelectOption' />",{"custSeq" : this.value} , function(response){
								console.log(response);
			        			if (response.success == "true"){
									var buildingList =  response.buildingList;
									$(document.pushAdd.buildSeq).find("option").not("[value='']").remove();
									$.each(buildingList, function(i,e){
										var option = $("<option></option>");
										option.text(e.buildName);
										option.val(e.buildSeq); 
										$(document.pushAdd.buildSeq).append(option);
									});
									$(document.pushAdd.buildSeq).selectpicker('refresh');
			        			
			        			}
			        			
			        		}).fail(function(e){
			        			console.log(e);
			        		});
						});
						
						
						
					});
				</script>

                <div id="pushAdd" class="card card-collapse collapse">
                    <div class="header">
                    	<div class="row">
	                        <div class="col-sm-8">
                    			<p class="category">푸쉬 메세지 등록</p>
                    		</div>
                    		<div class="col-sm-4">
                    			<a data-target="#pushAdd"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
                    		</div>
                   		</div>
                    </div>
                    <div class="content">
                    	
                    	<form name="pushAdd" method="POST" action="<c:url value="pushAdd" />" class="form-horizontal">
							<input type="hidden" name="pushType" value="N">
							<c:if test="${not empty cafeList }">
								<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">Cafe 선택</label>
		                    			<div class="col-sm-10">
		                    				<select name="custSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
		                    					<c:forEach var="a" items="${cafeList }">
				                    				<option value="${a.cafeseq }">${a.cafename }</option>
				                    			</c:forEach>
					                    	</select>
		                    			</div>
		                    		</div>
		                    		
	                    		</fieldset>
							</c:if>
							
							<c:if test="${not empty cafeAll }">
								<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">Cafe 선택</label>
		                    			<div class="col-sm-10">
		                    				<select name="custSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
		                    					<option value="">전체 카페 대상</option>
		                    					<c:forEach var="a" items="${cafeAll }">
				                    				<option value="${a.cafeseq }">${a.cafename }</option>
				                    			</c:forEach>
					                    	</select>
		                    			</div>
		                    		</div>
	                    		</fieldset>
							</c:if>
							
							
							<c:if test="${not empty customerAll }">
								<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">회사 선택</label>
		                    			<div class="col-sm-10">
		                    				<select name="custSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
		                    					<option value="">전체 회사 대상</option>
				                    			<c:forEach var="a" items="${customerAll }">
				                    				<option value="${a.custSeq }">${a.custName }</option>
				                    			</c:forEach>
					                    	</select>
		                    			</div>
		                    		</div>
	                    		</fieldset>
							</c:if>
							
							<%-- <fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">건물 선택</label>
	                    			<div class="col-sm-10">
	                    				<select name="buildSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
	                    					<option value="">건물 전체 대상</option>
			                    			<c:forEach var="a" items="${buildingList }">
			                    				<option value="${a.buildSeq }">${a.buildName }</option>
			                    			</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset> --%>
	                    		
                    		
                    		
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제목</label>
	                    			<div class="col-sm-10">
	                    				<input name="pushTitle" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>


                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="pushContent" type="text" class="form-control"></textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">예약발송일시</label>
	                    			<div class="col-sm-4">
										<input name="yyyyMMdd" type="text" class="form-control datetimepicker" placeholder="예약 날짜를 선택해주세요" value="">
	                    			</div>
	                    			<div class="col-sm-3">
	                    				<select name="hh" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
											<c:forEach var="i" begin="9" end="22">
												<option value="<fmt:formatNumber type = "number" minIntegerDigits="2" value = "${i}" />"><fmt:formatNumber type = "number" minIntegerDigits="2"  value = "${i}" /></option>
											</c:forEach>
										</select>
	                    			</div>
	                    			<div class="col-sm-3">
	                    				<select name="mm" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
											<option value="00">00</option>
											<option value="30">30</option>
										</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
                    			<div class="form-group">
                    				<label class="col-sm-2 control-label">즉시발송</label>
	                    			<div class="col-sm-10">
	                    				<input name="now" type="checkbox"  value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
	                    			</div>
                    			</div>
                    		</fieldset>
                    		
                    		
                    		<input type="hidden" name="reserveTime">
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
                    		</div>
                    	</form>
                    </div>
                </div>


                <div id="pushEdit" class="card card-collapse collapse">
                    <div class="header">
                    	<div class="row">
	                        <div class="col-sm-8">
                    			<p class="category">게시글 수정</p>
                    		</div>
                    		<div class="col-sm-4">
                    			<a data-target="#pushEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
                    		</div>
                   		</div>
                    </div>
                    <div class="content">
                    	
                    	<form name="pushEdit" method="POST" action="<c:url value="pushEdit" />" class="form-horizontal">
                    		<input type="hidden" name="pushSeq">
                    		<input type="hidden" name="pushType" value="N">
	
							<c:if test="${not empty customerAll }">
								<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">회사 선택</label>
		                    			<div class="col-sm-10">
		                    				<select name="custSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
		                    					<option value="">전체 회사 대상</option>
				                    			<c:forEach var="a" items="${customerAll }">
				                    				<option value="${a.custSeq }">${a.custName }</option>
				                    			</c:forEach>
					                    	</select>
		                    			</div>
		                    		</div>
	                    		</fieldset>
							</c:if>
							
							<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">건물 선택</label>
	                    			<div class="col-sm-10">
	                    				<select name="buildSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
	                    					<option value="">건물 전체 대상</option>
			                    			<c:forEach var="a" items="${buildingList }">
			                    				<option value="${a.buildSeq }">${a.buildName }</option>
			                    			</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
	                    		
                    		
                    		
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제목</label>
	                    			<div class="col-sm-10">
	                    				<input name="pushTitle" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="pushContent" type="text" class="form-control"></textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>


                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">예약발송일시</label>
	                    			<div class="col-sm-4">
										<input name="yyyyMMdd" type="text" class="form-control datetimepicker" placeholder="예약 날짜를 선택해주세요" value="">
	                    			</div>
	                    			<div class="col-sm-3">
	                    				<select name="hh" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
											<c:forEach var="i" begin="9" end="22">
												<option value="<fmt:formatNumber type = "number" minIntegerDigits="2" value = "${i}" />"><fmt:formatNumber type = "number" minIntegerDigits="2"  value = "${i}" /></option>
											</c:forEach>
										</select>
	                    			</div>
	                    			<div class="col-sm-3">
	                    				<select name="mm" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
											<option value="00">00</option>
											<option value="30">30</option>
										</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
                    			<div class="form-group">
                    				<label class="col-sm-2 control-label">즉시발송</label>
	                    			<div class="col-sm-10">
	                    				<input name="now" type="checkbox"  value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
	                    			</div>
                    			</div>
                    		</fieldset>
                    		
                    		
                    		<input type="hidden" name="reserveTime">

                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-danger btn-fill btn-wd">수정</button>
                    		</div>
                    		
                    	</form>
                    </div>
                </div>



				
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

