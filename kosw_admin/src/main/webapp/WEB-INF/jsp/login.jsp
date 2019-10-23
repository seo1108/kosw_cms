<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<%@ include file="/WEB-INF/jsp/common/head.jsp"  %>
<%@ include file="/WEB-INF/jsp/common/script.jsp"  %>
<body>

	<nav class="navbar navbar-transparent navbar-absolute">
	    <div class="container">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
	                <span class="sr-only"><!-- Toggle navigation --></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="../dashboard.html"><!-- Light Bootstrap Dashboard PRO --></a>
	        </div>
	        <div class="collapse navbar-collapse">
	
	            <ul class="nav navbar-nav navbar-right">
	                <li>
	                   <a href="register.html">
	                        <!-- Register -->
	                    </a>
	                </li>
	            </ul>
	        </div>
	    </div>
	</nav>
	
	<div class="wrapper wrapper-full-page">
	    <div class="full-page login-page" data-color="orange" data-image="../../assets/img/full-screen-image-1.jpg">
	
	    <!--   you can change the color of the filter page using: data-color="blue | azure | green | orange | red | purple" -->
	        <div class="content">
	            <div class="container">
	                <div class="row">
	                    <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
	                    	<c:url value="/login" var="loginUrl"/>
	                        <form:form action="${loginUrl }" method="post">
	                        <!--   if you want to have the card without animation please remove the ".card-hidden" class   -->
	                            <div class="card card-hidden">
	                                <div class="header text-center">계단왕 로그인</div>
	                                <div class="content">
	                                	<c:if test="${param.error != null}">
											<p class="text-danger">
												${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} 
											</p>
										</c:if>
										<c:if test="${param.logout != null}">
											<p class="text-primary">
												로그아웃 되었습니다.
											</p>
										</c:if>
	                                    <div class="form-group">
	                                        <label>이메일</label>
	                                        <input name="username" type="email" placeholder="Enter email" class="form-control">
	                                    </div>
	                                    <div class="form-group">
	                                        <label>비밀번호</label>
	                                        <input name="password" type="password" placeholder="Password" class="form-control">
	                                    </div>
	                                    <!-- <div class="form-group">
	                                        <label class="checkbox">
	                                            <input type="checkbox" data-toggle="checkbox" value="">
	                                            Subscribe to newsletter
	                                        </label>
	                                    </div> -->
	                                </div>
	                                <div class="footer text-center">
	                                    <button type="submit" class="btn btn-fill btn-warning btn-wd">Login</button>
	                                </div>
	                            </div>
	                        </form:form>
	
	                    </div>
	                </div>
	            </div>
	        </div>
	
	    	<footer class="footer footer-transparent">
	            <div class="container">
	                <nav class="pull-left">
	                    <!-- <ul>
	                        <li>
	                            <a href="#">
	                                Home
	                            </a>
	                        </li>
	                        <li>
	                            <a href="#">
	                                Company
	                            </a>
	                        </li>
	                        <li>
	                            <a href="#">
	                                Portfolio
	                            </a>
	                        </li>
	                        <li>
	                            <a href="#">
	                               Blog
	                            </a>
	                        </li>
	                    </ul> -->
	                </nav>
	                <p class="copyright pull-right">
	                    <!-- &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.creative-tim.com">Creative Tim</a>, made with love for a better web -->
	                </p>
	            </div>
	        </footer>
	
	    </div>
	</div>
</body>

<script type="text/javascript">
    $().ready(function(){
       lbd.checkFullPageBackgroundImage();

       setTimeout(function(){
           // after 1000 ms we add the class animated to the login/register card
           $('.card').removeClass('card-hidden');
       }, 700)
   });
</script>


<% 
	String message = (String)request.getAttribute("message");
	if (message != null){ 
%>
	<script>
		window.onload = function() {
			alert("<%=message %>");
		};
	</script>
<% }%>
    
</html>

