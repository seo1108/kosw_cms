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
                    	<form name="beaconManufacAdd" method="POST" action="${beaconManufacAddURL }" class="form-horizontal">
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">제조사명</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacName" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 이름</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostName" type="text" class="form-control"  autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 이메일</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostEmail" type="email" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">담당자 전화번호</label>
	                    			<div class="col-sm-10">
	                    				<input name="manufacPostPhone" type="text" class="form-control" autocomplete="off">
	                    			</div>
	                    		</div>
                    		</fieldset>
                    		
                    		<fieldset>
	                    		<div class="form-group">
	                    			<label class="col-sm-2 control-label">비고</label>
	                    			<div class="col-sm-10">
	                    				<textarea name="manufacRemarks" class="form-control"></textarea>
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
                    $(function(){
                		$(document.beaconManufacAdd).validate({
                			rules : {
                				manufacName : {
                					required : true,
                					minlength : 3,
                					maxlength : 15
                				},
                				manufacPostName : {
                					minlength : 3,
                					maxlength : 15
                				},
                				manufacPostEmail : {
                					email : true,
                				},
                				manufacPostPhone : {
                					minlength : 8,
                					maxlength : 15
                				},
                				manufacRemarks : {
                					maxlength : 200
                				}
                			},
                			messages : {
                				manufacName : {
                					required : "제조사명을 입력해주세요.",
                					minlength : "제조사 명을 3자 이상 입력해주세요.",
                					maxlength : "제조사 명을 15자 이하로 입력해주세요."
                				},
                				manufacPostName : {
                					minlength : "제조사 담당자명을 3자 이상 입력해주세요.",
                					maxlength : "제조사 담당자명을 15자 이하로 입력해주세요."
                				},
                				manufacPostEmail : {
                					email : "담당자 이메일 형식을 확인해주세요.",
                				},
                				manufacPostPhone : {
                					minlength : "담당자 전화번호를 8자 이상으로 입력해주세요.",
                					maxlength : "담당자 전화번호를 15자 이하로 입력해주세요.",
                				},
                				manufacRemarks : {
                					maxlength : "비고를 200자 이하로 입력해주세요."
                				}
                			}
                		});
                    });
                </script>
	                    
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

