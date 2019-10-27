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
        	var bbsOption = {
           		rules : {
           			title : {
           				required : true,
           				minlength : 2,
           				maxlength : 100
           			},
           			content : {
           				required : true,
           				minlength : 2,
           				maxlength : 3000
           			},
           			expireDate : {
           				required : true
           			}
           		},
           		messages : {
           			title : {
           				required : "제목을 입력해주세요.",
           				minlength : "제목을 2자 이상으로 입력해주세요.",
           				maxlength : "제목을 100자 이하로 입력해주세요."
           			},
           			content : {
           				required : "내용을 입력해주세요.",
           				minlength : "내용을 2자 이상으로 입력해주세요.",
           				maxlength : "내용을 3000자 이하로 입력해주세요."
           			},
           			expireDate : {
           				required : "만료일자를 선택해주세요."
           			}
           		}
           	}
        	
        	$(document.bbsAdd).validate(bbsOption);
        	$(document.bbsEdit).validate(bbsOption);

        	showBssAdd = function(){
        		$("#bbsAdd").collapse("show");
        		setTimeout(function(){
        			location.href = "#bbsAdd";	
        		}, 300);
        	}
        	
        	showBssEdit = function(){
        		$("#bbsEdit").collapse("show");
        		setTimeout(function(){
        			location.href = "#bbsEdit";	
        		}, 300);
        	}
        	
        	showDetail = function(bbsSeq){
        		$.get("<c:url value='bbsDetail' />",{"bbsSeq" : bbsSeq} , function(response){
        			if (response.success == "true"){
        				$("#bbsEdit").collapse("show");
        				var bbs = response.bbs;
        				document.bbsEdit.bbsSeq.value = bbs.bbsSeq;
        				document.bbsEdit.title.value = bbs.title;
        				document.bbsEdit.content.value = bbs.content;
        				$(document.bbsEdit.bbsType).selectpicker("val", bbs.bbsType);
						$(document.bbsEdit.expireDate).data("DateTimePicker").date(moment(bbs.expireDate, "YYYYMMDD"));
        				
        				$(document.bbsEdit.custSeq).selectpicker("val", bbs.custSeq);
        				
        				if (bbs.custSeq){
        					bbsEditCustSeqChanged(bbs.custSeq, function(){
        						$(document.bbsEdit.buildSeq).selectpicker("val", bbs.buildSeq);
        					});
        				}
        				
        				if (bbs.buildSeq){
        					bbsEditBuildSeqChanged(bbs.buildSeq, function(){
        						$(document.bbsEdit.stairSeq).selectpicker("val", bbs.stairSeq);		
        					});
        				}
        				
        				showBssEdit();
        				
        			}
        			
        		}).fail(function(e){
        			console.log(e);
        		});
        	}
        	
        	
        	$(document.bbsEdit.custSeq).on("changed.bs.select", function(){
        		bbsEditCustSeqChanged(this.value);
        	});
        	$(document.bbsEdit.buildSeq).on("changed.bs.select", function(){
        		bbsEditBuildSeqChanged(this.value);
        	});
        	
        	function bbsEditCustSeqChanged(custSeq, succssFunc){
				$.get("<c:url value='buildingSelectOption' />",{"custSeq" : custSeq} , function(response){
					console.log(response);
        			if (response.success == "true"){
						var buildingList =  response.buildingList;
						$(document.bbsEdit.buildSeq).find("option").not("[value='']").remove();
						$(document.bbsEdit.stairSeq).find("option").not("[value='']").remove();
						$.each(buildingList, function(i,e){
							var option = $("<option></option>");
							option.text(e.buildName);
							option.val(e.buildSeq); 
							$(document.bbsEdit.buildSeq).append(option);
						});
						$(document.bbsEdit.buildSeq).selectpicker('refresh');
						$(document.bbsEdit.stairSeq).selectpicker('refresh');
        			
						if (succssFunc){
							succssFunc.call();
						}
        			}
        			
        		}).fail(function(e){
        			console.log(e);
        		});
			}
        	
        	
        	function bbsEditBuildSeqChanged(buildSeq, successFunc){
				$.get("<c:url value='stairSelectOption' />",{"buildSeq" : this.value} , function(response){
					console.log(response);
        			if (response.success == "true"){
						var buildingStairList =  response.buildingStairList;
						
						$(document.bbsEdit.stairSeq).find("option").not("[value='']").remove();
						$.each(buildingStairList, function(i,e){
							var option = $("<option></option>");
							option.text(e.stairName);
							option.val(e.stairSeq); 
							$(document.bbsEdit.stairSeq).append(option);
						});
						$(document.bbsEdit.stairSeq).selectpicker('refresh');
        			
						if (successFunc){
							successFunc.call();
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
                        <p class="category">게시글 리스트</p>
                    </div>
                    <div class="content">
                		<c:if test="${empty bbsList }">
                			<p class="text-danger">등록된 게시글이 없습니다.</p>
                		</c:if>
                		<c:if test="${not empty bbsList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>번호</th>
										<th>제목</th>
										<th>내용</th>
										<th>대상</th>
										<th>타입</th>
										<th>만료일자</th>
										<th>관리자</th>
										<th>등록일시</th>
										<th>ACTION</th>
									</tr>
									<c:forEach var="a" items="${bbsList }">
										<tr>
											<td>${a.bbsSeq }</td>
											<td>${a.title }</td>
											<td>${a.contentTrim }</td>
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
												<c:if test="${'N' eq a.bbsType }">
													<p class="text-primary">공지</p>
												</c:if>
												<c:if test="${'E' eq a.bbsType }">
													<p class="text-danger">이벤트</p>
												</c:if>
											</td>
											<td>${a.expireDateFormat }</td>
											<td>${a.adminName }</td>
											<td>${a.bbsRegTimeFormat }</td>
											<td class="tx-actions text-right">
                                                <a onclick="showDetail('${a.bbsSeq }');" rel="tooltip" title="수정" class="btn btn-warning btn-simple" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                
                                                <form class="deleteForm" method="post" action="<c:url value="bbsDelete"/>" style="display:inline-block;">
                                                	<input name="bbsSeq" type="hidden" value="${a.bbsSeq }"/>
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
							<a href='javascript:showBssAdd();' class="btn btn-primary btn-fill" >신규 게시글 등록</a>
						</div>
            		</div>
					
                </div> <!-- CARD-1 -->



				<script>
					$(function(){
						
						$(document.bbsAdd.custSeq).on("changed.bs.select", function(){
							$.get("<c:url value='buildingSelectOption' />",{"custSeq" : this.value} , function(response){
								console.log(response);
			        			if (response.success == "true"){
									var buildingList =  response.buildingList;
									$(document.bbsAdd.buildSeq).find("option").not("[value='']").remove();
									$(document.bbsAdd.stairSeq).find("option").not("[value='']").remove();
									$.each(buildingList, function(i,e){
										var option = $("<option></option>");
										option.text(e.buildName);
										option.val(e.buildSeq); 
										$(document.bbsAdd.buildSeq).append(option);
									});
									$(document.bbsAdd.buildSeq).selectpicker('refresh');
									$(document.bbsAdd.stairSeq).selectpicker('refresh');
			        			
			        			}
			        			
			        		}).fail(function(e){
			        			console.log(e);
			        		});
						});
						
						$(document.bbsAdd.buildSeq).on("changed.bs.select", function(){
							$.get("<c:url value='stairSelectOption' />",{"buildSeq" : this.value} , function(response){
								console.log(response);
			        			if (response.success == "true"){
									var buildingStairList =  response.buildingStairList;
									
									$(document.bbsAdd.stairSeq).find("option").not("[value='']").remove();
									$.each(buildingStairList, function(i,e){
										var option = $("<option></option>");
										option.text(e.stairName);
										option.val(e.stairSeq); 
										$(document.bbsAdd.stairSeq).append(option);
									});
									$(document.bbsAdd.stairSeq).selectpicker('refresh');
			        			
			        			}
			        			
			        		}).fail(function(e){
			        			console.log(e);
			        		});
						});
						
						
					});
				</script>

                <div id="bbsAdd" class="card card-collapse collapse">
                    <div class="header">
                    	<div class="row">
	                        <div class="col-sm-8">
                    			<p class="category">게시글 등록</p>
                    		</div>
                    		<div class="col-sm-4">
                    			<a data-target="#bbsAdd"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
                    		</div>
                   		</div>
                    </div>
                    <div class="content">
                    	
                    	<form name="bbsAdd" method="POST" action="<c:url value="bbsAdd" />" class="form-horizontal">

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
	                    		
                    		
                    		<fieldset style="display:none;">
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">계단 선택</label>
	                    			<div class="col-sm-10">
	                    				<select name="stairSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
	                    					<option value="">계단 전체 대상</option>
			                    			<c:forEach var="a" items="${buildingStairList }">
			                    				<option value="${a.stairSeq }">${a.stairName }</option>
			                    			</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제목</label>
	                    			<div class="col-sm-10">
	                    				<input name="title" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>


                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="content" type="text" class="form-control"></textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">게시글 타입</label>
	                    			<div class="col-sm-4">
	                    				<select name="bbsType" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
			                    			<option value="N" >공지사항</option>
			                    			<option value="E" >이벤트</option>
				                    	</select>
	                    			</div>
									<input name="pushFlag" type="hidden"  value="Y"/>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">만료일</label>
	                    			<div class="col-sm-10">
										<input name="expireDate" type="text" class="form-control datetimepicker" placeholder="만료 날짜를 선택해주세요" value="">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
                    		</div>
                    	</form>
                    </div>
                </div>


                <div id="bbsEdit" class="card card-collapse collapse">
                    <div class="header">
                    	<div class="row">
	                        <div class="col-sm-8">
                    			<p class="category">게시글 수정</p>
                    		</div>
                    		<div class="col-sm-4">
                    			<a data-target="#bbsEdit"  data-toggle="collapse" aria-hidden="true" class="close" style="opacity:1.0;">×</a>
                    		</div>
                   		</div>
                    </div>
                    <div class="content">
                    	
                    	<form name="bbsEdit" method="POST" action="<c:url value="bbsEdit" />" class="form-horizontal">
                    		<input type="hidden" name="bbsSeq">
	
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
	                    		
                    		
                    		<fieldset style="display:none;">
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">계단 선택</label>
	                    			<div class="col-sm-10">
	                    				<select name="stairSeq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
	                    					<option value="">계단 전체 대상</option>
			                    			<c:forEach var="a" items="${buildingStairList }">
			                    				<option value="${a.stairSeq }">${a.stairName }</option>
			                    			</c:forEach>
				                    	</select>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제목</label>
	                    			<div class="col-sm-10">
	                    				<input name="title" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="content" type="text" class="form-control"></textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">게시글 타입</label>
	                    			<div class="col-sm-4">
	                    				<select name="bbsType" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
			                    			<option value="N" >공지사항</option>
			                    			<option value="E" >이벤트</option>
				                    	</select>
	                    			</div>
	                    			<input name="pushFlag" type="hidden"  value="Y"/>
	                    		</div>
                    		</fieldset>

                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">만료일</label>
	                    			<div class="col-sm-10">
										<input name="expireDate" type="text" class="form-control datetimepicker" placeholder="만료 날짜를 선택해주세요" value="">
	                    			</div>
	                    		</div>
                    		</fieldset>

                    		
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

