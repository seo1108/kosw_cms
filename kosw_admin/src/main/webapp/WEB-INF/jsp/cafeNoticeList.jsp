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
        
        jQuery(document).ready(function() {
    		$('select[name="selectCafe"]').change(function(e) {
    			location.href="cafeNoticeList?cafeseq="+$(this).val();
    		});
        });
    	
        
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
           			contents : {
           				required : true,
           				minlength : 2
           			}
           		},
           		messages : {
           			contents : {
           				required : "내용을 입력해주세요.",
           				minlength : "내용을 2자 이상으로 입력해주세요."
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
        	
        	showDetail = function(notiseq){
        		$.get("<c:url value='cafeNoticeDetail' />",{"notiseq" : notiseq} , function(response){
        			if (response.success == "true"){
        				$("#bbsEdit").collapse("show");
        				var bbs = response.bbs;
        				document.bbsEdit.notiseq.value = bbs.notiseq;
        				document.bbsEdit.contents.value = bbs.contents;
        				
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
                    
                    	<form name="searchForm" method="get" class="form-horizontal">
							<input name="cafeseq" type="hidden" />
							
							<c:if test="${cafeSelected eq false}">
								<fieldset>
									<div class="form-group">
										<label class="col-sm-1 control-label">내 Cafe</label>
		                    			<div class="col-sm-5">
		                    				<select name="selectCafe" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
		                    					<option value="" selected>Cafe를 선택해주세요.</option>
				                    			<c:forEach var="a" items="${cafeList }">
													<option value="${a.cafeseq }"> ${a.cafename }</option>
												</c:forEach>
					                    	</select>
		                    			</div>
									</div>
								</fieldset>
							</c:if>
							
							<c:if test="${cafeSelected eq true}">
								<fieldset>
									<div class="form-group">
										<c:choose>
											<c:when test="${type eq 'category' || type eq 'individual'}">
												
											</c:when>
											<c:otherwise>
												<label class="col-sm-1 control-label">내 Cafe</label>
					                    			<div class="col-sm-5">
					                    				<select name="selectCafe" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
					                    					<option value="">Cafe를 선택해주세요.</option>
							                    			<c:forEach var="a" items="${cafeList }">
																<option value="${a.cafeseq }" <c:if test="${cafeseq eq a.cafeseq}">selected</c:if> > ${a.cafename }</option>
															</c:forEach>
								                    	</select>
					                    			</div>
											</c:otherwise>
										</c:choose>
									</div>
								</fieldset>
							</c:if>
						</form>
                    
                      
                    
                    	<c:if test="${cafeSelected eq true}">
	                		<c:if test="${empty bbsList }">
	                			<p class="text-danger">등록된 게시글이 없습니다.</p>
	                		</c:if>
	                		<c:if test="${not empty bbsList }">
								<div class="table-responsive">
									<table class="table">
										<tr>
											<th>작성자</th>
											<th>카페명</th>
											<th>내용</th>
											<th>등록일시</th>
											<th>ACTION</th>
										</tr>
										<c:forEach var="a" items="${bbsList }">
											<tr>
												<td>${a.nickname }</td>
												<td>${a.cafename }</td>
												<td>${a.contentsTrim }</td>
												<td>${a.regdate }</td>
												<td class="tx-actions text-right">
	                                                <a onclick="showDetail('${a.notiseq }');" rel="tooltip" title="수정" class="btn btn-warning btn-simple" style="display:inline-block;">
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
						</c:if>
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
                    	
                    	<form name="bbsAdd" method="POST" action="<c:url value="cafeNoticeAdd" />" class="form-horizontal">
							<input name="user_seq" type="hidden" value="${loginseq }"/>
							<input name="cafeseq" type="hidden" value="${cafeseq }"/>
							<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="contents" type="text" class="form-control"></textarea>
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
                    	
                    	<form name="bbsEdit" method="POST" action="<c:url value="cafeNoticeEdit" />" class="form-horizontal">
                    		<input name="user_seq" type="hidden" value="${loginseq }"/>
                    		<input type="hidden" name="notiseq" value="${a.notiseq }">
                    		<input type="hidden" name="cafeseq" value="${cafeseq }">
	
							<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">내용</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="contents" type="text" class="form-control"></textarea>
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

