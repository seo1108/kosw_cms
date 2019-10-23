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
        	var option = {
              		rules : {
              			appVersion : {
              				required : true,
              				minlength : 2,
              				maxlength : 15,
              				regex : /^[\d]{1,2}\.[\d]{1,2}\.[\d]{1,2}$/
              			},
              			updateDesc : {
              				required : true,
              				minlength : 2,
              				maxlength : 3000
              			}
              		},
              		messages : {
              			appVersion : {
              				required : "앱 버전을 입력해주세요.",
              				minlength : "제목을 2자 이상으로 입력해주세요.",
              				maxlength : "제목을 15자 이하로 입력해주세요.",
              				regex : "버전 형식을 #.#.# 로 입력해주세요. 예: 1.1.5"
              			},
              			updateDesc : {
              				required : "내용을 입력해주세요.",
              				minlength : "내용을 2자 이상으로 입력해주세요.",
              				maxlength : "내용을 3000자 이하로 입력해주세요."
              			}
              		}
              	}
           	
           	$(document.verAdd_I).validate(option);
        	$(document.verAdd_A).validate(option);
        	
        });
        </script>


        <div class="row">
            <div class="col-md-12">
                <c:forEach var="v" items="${vers }">
                	<c:if test="${v.appType eq 'A' }">
                		<c:set var="device" value="안드로이드"/>
                	</c:if>
                	<c:if test="${v.appType eq 'I' }">
                		<c:set var="device" value="아이폰"/>
                	</c:if>
                	
					                	
                	<div class="card">
	                    <div class="header">
	                    	<div class="row">
		                        <div class="col-sm-8">
	                    			<p class="category">${device }</p>
	                    		</div>
	                   		</div>
	                    </div>
	                    <div class="content">
	                    	
	                    	<form name="verAdd_${v.appType }" method="POST" class="form-horizontal">
	                    		<input type="hidden" name="appType" value="${v.appType }">
	                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">버전</label>
		                    			<div class="col-sm-10">
		                    				<input name="appVersion" type="text" class="form-control"  autocomplete="off"  value="${v.appVersion }">
		                    			</div>
		                    		</div>
	                    		</fieldset>
	
	
	                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">업데이트내용</label>
		                    			<div class="col-sm-10">
		                    				<textarea name="updateDesc" class="form-control" rows="5">${v.updateDesc }</textarea>
		                    			</div>
		                    		</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
	                    			<div class="form-group">
	                    				<label class="col-sm-2 control-label">강제 업데이트</label>
		                    			<div class="col-sm-10">
		                    				<input name="now" type="checkbox"  <c:if test="${v.forceKill eq 'Y' }">checked</c:if> value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
											<span class="toggle"></span>
		                    			</div>
	                    			</div>
	                    		</fieldset>
	                    		
	                    		<fieldset>
		                    		<div class="form-group">
		                    			<label class="col-sm-2 control-label">작성</label>
		                    			<div class="col-sm-4">
		                    				<input name="" type="text" readonly class="form-control"    value="${v.adminName }">
		                    			</div>
		                    			
		                    			<label class="col-sm-2 control-label">등록일시</label>
		                    			<div class="col-sm-4">
		                    				<input name="" type="text" readonly class="form-control"    value="${v.verRegTimeFormat }">
		                    			</div>
		                    		</div>
	                    		</fieldset>
	                    		
	                    		<div class="footer text-center">
	                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
	                    		</div>
	                    	</form>
	                    </div>
	                </div>
                </c:forEach>

                


				
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

