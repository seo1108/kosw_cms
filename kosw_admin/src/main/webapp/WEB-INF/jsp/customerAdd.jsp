<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

<!-- 		<ol class="breadcrumb">
			<li class="active">고객사 등록</li>
		</ol> -->

        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="header">
                        <legend>신규 고객사 등록</legend>
                    </div>
                    <div class="content">
                    	<c:url var="customerAdd" value="/customerAdd"/>
                    	<form name="customerAdd" method="POST" action="${customerAdd }" class="form-horizontal">
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">고객사명</label>
	                    			<div class="col-sm-10">
	                    				<input name="custName" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자명</label>
	                    			<div class="col-sm-10">
	                    				<input name="postName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 이메일</label>
	                    			<div class="col-sm-10">
	                    				<input name="postEmail" type="email" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 전화번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="postPhone" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
                    			<div class="form-group">
                    				<label class="col-sm-2 control-label">사용자 자동승인</label>
	                    			<div class="col-sm-10">
	                    				<input name="userAutoConfirmFlag" type="checkbox" value="Y" data-toggle="switch" data-on-color="info" data-off-color="info" data-on-text="ON" data-off-text="OFF"/>
										<span class="toggle"></span>
	                    			</div>
                    			</div>
                    		</fieldset>
	                    		
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비고</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="custRemarks" class="form-control"></textarea>
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<div class="footer text-center">
                    			<button type="submit" class="btn btn-info btn-fill btn-wd">등록</button>
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

