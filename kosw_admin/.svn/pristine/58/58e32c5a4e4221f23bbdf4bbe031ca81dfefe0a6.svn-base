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
                    <div class="content">
                    	
                    	<form name="beaconManufacEdit" method="POST" action="<c:url value="/beaconManufacEdit" />" class="form-horizontal">
                    		<input name="manufacSeq" value="${beaconManufac.manufacSeq }" type="hidden" >
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제조사명</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacName" value="${beaconManufac.manufacName }" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 이름</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostName" value="${beaconManufac.manufacPostName }" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 이메일</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostEmail" value="${beaconManufac.manufacPostEmail }" type="email" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 전화번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostPhone" value="${beaconManufac.manufacPostPhone }" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비고</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="manufacRemarks" class="form-control">${beaconManufac.manufacRemarks }</textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">수정</button>
                    		</div>
                    		
                    	</form>
                    </div>
                </div>
                
                <script>
                    (function($) {
                		$(document.customerAdd).validate({
                			rules : {
                				custName : {
                					required : true,
                					minlength : 3,
                					maxlength : 10
                				},
                				postName : {
                					required : true,
                					minlength : 3,
                					maxlength : 10
                				},
                				postEmail : {
                					required : true,
                					email : true,
                				},
                				postPhone : {
                					required : true,
                					minlength : 8,
                					maxlength : 15
                				},
                				custRemarks : {
                					maxlength : 100
                				}
                			},
                			messages : {
                				custName : {
                					required : "고객사명을 입력해주세요.",
                					minlength : "고객사명을 3자리 이상 입력해주세요.",
                					maxlength : "고객사명을 10자리 미만으로 입력해주세요.",
                				},
                				postEmail : {
                					required : "담당자 이메일을 입력해주세요.",
                					email : "담당자 이메일의 형식을 확인해주세요."
                				},
                				postName : {
                					required : "담당자명을 입력해주세요.",
                					minlength : "담당자명을 3자리 이상 입력해주세요.",
                					maxlength : "담당자명을 10자리 미만으로 입력해주세요.",
                				},
                				postPhone : {
                					required : "담당자 전화번호를 입력해주세요.",
                					minlength : "담당자 전화번호를 8자리 이상 입력해주세요.",
                					maxlength : "담당자 전화번호를 15자리 미만으로 입력해주세요.",
                				},
                				custRemarks : {
                					maxlength : "비고를 100자 미만으로 입력해주세요."	
                				}
                			}
                		});
                    })(jQuery);
                </script>
	                    
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

