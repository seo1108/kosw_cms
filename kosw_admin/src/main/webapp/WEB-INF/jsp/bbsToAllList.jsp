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
        	/* $(document.search).show(); */
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
           				maxlength : 1000
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
           				maxlength : "내용을 1000자 이하로 입력해주세요."
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
        				
        				showBssEdit();
        				
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
											<td>전체</td>
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
	                    				<textarea name="content" type="text" class="form-control" rows="10"></textarea>
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
                    		<input type="hidden" name="bbsSeq" value="${a.bbsSeq}">
	
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
	                    				<textarea name="content" type="text" class="form-control" rows="10"></textarea>
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

