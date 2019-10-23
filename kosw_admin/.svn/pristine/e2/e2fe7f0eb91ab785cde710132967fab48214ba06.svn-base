<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<style>
	table.chracter-table td div.uploadBox{
		dispaly : block;
		min-height : 150px;
		max-height : 200px;
		background: #eee;
	}

	table.chracter-table td div.uploadBox.ex{
		min-height : 200px;
		max-height : 300px;
	}
	
	table.chracter-table td div.uploadBox img{
		width : 100%;
		min-height : 150px;
		max-height : 200px;
	}
</style>

<div class="main-content">
    <div class="container-fluid">
    	<ol class="breadcrumb">
  			<li><a href="${characterListURL }">캐릭터 리스트</a></li>
			<li class="active">${title }</li>
		</ol>
		
		<div class="card card-plain">
			<p class="category">수정할 캐릭터 위치에 이미지파일을 끓어 놓으세요.</p>
			<p class=""><strong>등록할 이미지 파일의 파일명은 반드시 영문이어야 합니다.</strong></p>
		</div>

        <div class="row">
        	<div class="col-md-12">
                <div class="card">
                	<div class="content">
                    	<form name="characterEdit" method="post" action="<c:url value="/characterEdit"/>" enctype="multipart/form-data" >
                    		<input name="charCode" type="hidden" value="${character.charCode }">
			
							<div class="row">
								<div class="col-sm-5">
									<label class="control-label">캐릭터명</label>
									<div id="characterNameDiv" class="form-group">
										<input name="charName" type="text" class="form-control" value="${character.charName }">
										<span class="text-danger" style="display:none;">동일한 캐릭터명이 있습니다. 다른 캐릭터명을 입력해주세요.</span>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-4">
									<label class="control-label">성별</label>
									<div class="form-group">
										<div class="form-check form-check-radio">
											  <input <c:if test="${character.charGender eq 'M' }">checked</c:if> class="form-check-input" type="radio" name="charGender" id="charGender1" value="M">
										         <span class="form-check-sign"></span>
											 	<label for="charGender1">남성</label>
											 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
											 <input <c:if test="${character.charGender eq 'W' }">checked</c:if> class="form-check-input" type="radio" name="charGender" id="charGender2" value="W">
										         <span class="form-check-sign"></span>
											 	<label for="charGender2">여성</label>
										</div>
									</div>
								</div>
								<div class="col-sm-5">
									<label class="control-label">사용여부</label>
									<div class="form-group">
										<input name="charActiveFlag" value="Y" type="checkbox" <c:if test="${character.charActiveFlag eq 'Y' }">checked</c:if> data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
									</div>
								</div>
							</div>
						</form>
                    </div>
                </div>
            </div>
			
            <div class="col-md-12">

            	<div class="card">
                    <div class="header">
                    	<h4 class="title">계단 미포함 캐릭터 이미지</h4>
                        <p class="category">권장 이미지 사이즈 상반신 (가로 216px, 세로 216px), 전신 (가로 408px, 세로 408px)</p>
                        <p class="category"><strong>등록할 이미지 파일의 파일명은 반드시 영문이어야 합니다.</strong></p>
					</div>
                    <div class="content">
                    	<table class="table table-bigboy table-bordered chracter-table">
						    <thead>
						        <tr>
						            <th class="text-center" width="16.6%"></th>
						            <th class="text-center" width="41.7%">상반신</th>
						            <th class="text-center" width="41.7%">전신</th>
						        </tr>
						    </thead>
						    <tbody>
						    	<tr>
					    			<td>기본 저지</td>
				    				<c:set var="idx" value="0" />
					            	<c:set var="charSeq" value="${characterList[idx].charSeq }" />
					            	<c:url var="characterUrl" value="/characterFile" />
					            	<c:set var="characterUrl" value="${characterUrl}/${charSeq }" />
						            <td>
						            	<div class="uploadBox ex" data-seq="${charSeq }">
						            		<img class="chracterImg" src="${characterUrl }"/>
						            	</div>
						            </td>
					            	<c:set var="idx" value="1" />
					            	<c:set var="charSeq" value="${characterList[idx].charSeq }" />
					            	<c:url var="characterUrl" value="/characterFile" />
					            	<c:set var="characterUrl" value="${characterUrl}/${charSeq }" />
						            <td>
						            	<div class="uploadBox ex" data-seq="${charSeq }">
						            		<img class="chracterImg" src="${characterUrl }"/>
						            	</div>
						            </td>
						    	</tr>
						    </tbody>
						</table>
                    </div>
                </div>


                <div class="card">
                    <div class="header">
                    	<h4 class="title">계단 포함 캐릭터 이미지</h4>
                        <p class="category">권장 이미지 사이즈 가로 1080px, 세로 780px</p>
					</div>
                    <div class="content">
                    	<table class="table table-bigboy table-bordered chracter-table">
						    <thead>
						        <tr>
						            <th class="text-center"></th>
						            <c:forEach var="b" items="${body}">
						            	<th class="text-center" width="16.6%%">${b}단계</th>
						            </c:forEach>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach var="j" items="${jersey}" varStatus="st2">
						    		<tr>
							            <td>
											<c:if test="${j eq 'default'}">
												기본 저지
											</c:if>
					                    	<c:if test="${j eq 'gold'}">
												골드 저지
											</c:if>
											<c:if test="${j eq 'green'}">
												그린 저지
											</c:if>
											<c:if test="${j eq 'red'}">
												레드닷 저지
											</c:if>
							            </td>

							            <c:forEach var="b" items="${body}" varStatus="st3">
							            	<c:set var="idx" value="${2 + st2.index*5 + st3.index }" />
							            	<c:set var="charSeq" value="${characterList[idx].charSeq }" />
							            	<c:url var="characterUrl" value="/characterFile" />
							            	<c:set var="characterUrl" value="${characterUrl}/${charSeq }" />
							            	
								            <td>
								            	<div class="uploadBox" data-seq="${charSeq }">
								            		<img class="chracterImg" src="${characterUrl }"/>
								            	</div>
								            </td>
							            </c:forEach>
							        </tr>
						    	</c:forEach>
						    </tbody>
						</table>
                    </div>
                </div>
            </div>

            <div class="text-center">
            	<button id="submit" type="button" class="btn btn-info btn-fill" style="margin-bottom:15px;">수정</button>
			</div>

        </div>
    </div>
</div>


<script>
	
	
	//IMAGE drag & drop
	$(function(){
		
		// DISABLE DEFAULT SUBMIT
		$(document.characterEdit).on("submit", function(){
			return false;
		});
		
		// CHAR NAME VALIDATE
/* 		var charNameLength = 0;
		$(document.characterEdit.charName).on("keyup", function(){
			if (this.value.length > 1){
				if (charNameLength == this.value.length){
					return false;
				}
				charNameLength = this.value.length;
				var v = this.value;
				
				$.ajax({
	                url: '<c:url value="/characterNameCheck"/>',
	                data : {"name":v},
	                type: "post",
	                dataType: 'json',
	                success: function(data) {
	                	if (data.ok == "true"){
	                		$("#characterNameDiv").removeClass("has-error");
	                		$("#characterNameDiv").children("span").hide();
	                	}else{
	                		$("#characterNameDiv").addClass("has-error");
	                		$("#characterNameDiv").children("span").show();
	                	}
	                }
	            });
				
			}
		}); */
		
		
		
		var isAdvancedUpload = function() {
	        var div = document.createElement('div');
	        return (('draggable'in div) || ('ondragstart'in div && 'ondrop'in div)) && 'FormData'in window && 'FileReader'in window;
	    }();
	    
	    function addEventHandler(obj, evt, handler) {
	    	if (obj.addEventListener) {
	    	    // W3C method
	    	    obj.addEventListener(evt, handler, false);
			} else if (obj.attachEvent) {
	    	    // IE method.
	    	    obj.attachEvent('on' + evt, handler);
			} else {
	    	    // Old school method.
	    	    obj['on' + evt] = handler;
			}
		}
	    
	    if (isAdvancedUpload){
			
			$(".uploadBox").on('drag dragstart dragend dragover dragenter dragleave drop', function(e){
				e.preventDefault();
	            e.stopPropagation();
			}).on('dragover dragenter', function(){
				
	        }).on('dragleave dragend drop', function() {
	
	        }).on('drop', function(e) {
	        	var $div = $(this);
	        	var seq = $div.data("seq");
	        	
	        	var droppedFiles = e.originalEvent.dataTransfer.files;
	            if (droppedFiles){
	            	var file = droppedFiles[0];
	            	var reader = new FileReader();
	            	reader.readAsDataURL(file);
	            	addEventHandler(reader, 'loadend', function(e) {
	            		var src = this.result;
	            		
	            		var img = document.createElement("img");
	            		img.className = "chracterImg";
	            		img.src = src;
	            		img.file = file;
	            		img.seq = seq;
	            		
	            		$div.children("img").remove();
	            		$div.append(img);
	            	});
	            }
	        });
    	}else {
    		alert("최신 버전의 브라우져로 실행해주세요.");
    	}
	    
	    
	    $("#submit").on("click", function(){
	    	
	    	swal({
	    		title : "업로드 중입니다.",
	    		text : "잠시만 기다려 주세요.",
	    		showConfirmButton: false
	    	}).catch(swal.noop);
	    	
	    	
	    	var charName = document.characterEdit.charName.value;
	    	if (charName.length < 2){
	    		swal({
           			title : "캐릭터명을 입력해주세요.",
       	    		type: "error"
       	    	}).catch(swal.noop);
	    		return false;
	    	}
	    	
	    	if ($("#characterNameDiv").hasClass("has-error")){
	    		swal({
           			title : "중복된 캐릭터명이 있습니다. 다른 캐릭터명을 입력해주세요.",
       	    		type: "error"
       	    	}).catch(swal.noop);
	    		return false;
	    	}
	    	
	    	if (charName.length > 20){
	    		swal({
           			title : "캐릭터명을 20 미만으로 입력해주세요.",
       	    		type: "error"
       	    	}).catch(swal.noop);
	    		return false;
	    	}
	    	
	    	/* var charGender =  document.characterEdit.charGender.value;
	    	if (charGender.length == 0){
	    		alert("성별을 선택해주세요.");
	    		return false;
	    	} */
	    	
	    	var chracterImgs = $(".chracter-table").find("img.chracterImg");
	    	
	    	var $form = $(document.characterEdit);
	    	var formData = new FormData($form.get(0));
	    	
	    	
	    	chracterImgs.each(function(){
	    		if (this.file && this.seq){
	    			formData.append("editCharSeqs", this.seq);
	    			formData.append("characterFiles", this.file);
	    		}
	    	});
	    	
	    	$.ajax({
                url: $form.attr('action'),
                type: $form.attr('method'),
                data: formData,
                dataType: 'json',
                cache: false,
                contentType: false,
                processData: false,
                complete: function() {
					
                },
                success: function(data) {
                	if (data.success == "true"){
                		swal({
            	    		title : "정상 수정 되었습니다.",
            	    		timer: 1000,
            	    		showConfirmButton: false,
            	    		type: "success"
            	    	}).catch(swal.noop);	
                	}else{
                		var message = data.message;
                		swal({
                			title : "서버 에러가 발생하였습니다.",
                			text : message,
            	    		type: "error"
            	    	}).catch(swal.noop);
                	}
                },
                error: function() {
                	swal({
        	    		title : "서버 에러가 발생하였습니다.",
        	    		timer: 1000,
        	    		type: "error"
        	    	}).catch(swal.noop);
                }
            });
	    	
	    	
	    	 
	    	
	    });
	    
	    
	});
</script>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

