<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
		<div class="navbar-minimize">
			<button id="minimizeSidebar" class="btn btn-warning btn-fill btn-round btn-icon">
				<i class="fa fa-ellipsis-v visible-on-sidebar-regular"></i>
				<i class="fa fa-navicon visible-on-sidebar-mini"></i>
			</button>
		</div>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">${title }</a>
        </div>
        <div class="collapse navbar-collapse">

            <form  id = "search" name="search" class="navbar-form navbar-left navbar-search-form" role="search" style="display:none;">
                <div class="input-group">
                	<input class ="form-control inputparam"  name="sort" type="hidden"  value="${param.sort}">
                    <input class ="form-control inputparamname"  name="sortName" type="hidden"  value="${param.sortName}">
                    <span class="input-group-addon"><i class="fa fa-search"></i></span>
                    <input name="cSeq" type="hidden" value="${customer.custSeq}">
                    <input name="type" type="hidden" value="${param.type}">
                    <input name="search" type="text" value="${param.search }" class="form-control" placeholder="Search...">
                    <c:if test="${not empty  param.bSeq}">
                    <input name="bSeq" type="hidden" value="${param.bSeq}">
                    </c:if>
                    
                    <input class ="form-control inputStartparam" name="startSearchDate" type="hidden"  value="${param.startSearchDate}">
                    <input class ="form-control inputEndparam"  name="endSearchDate" type="hidden"  value="${param.endSearchDate}">
                    
                </div>
            </form>
            <c:if test="${not empty sortList }">
            <form name="sort" class="navbar-form navbar-left navbar-search-form" role="search" style="display:none;">
                    <div class="dropdown">
					  <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    
					  <c:if test="${not empty  param.sortName}">
					      ${param.sortName}
					  </c:if>
					  <c:if test="${empty  param.sortName}">
					      Sort...
					  </c:if>
					  	<span class="caret"></span>
					  </button>
                    
					  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
					 <c:forEach var="a" items="${sortList }">
					    <li class = "list-group-item"><p data-value="${a.sortField}" style="font-size:12px;width:150px;cursor:pointer;">${a.sortName}</p></li>
					 </c:forEach>
					  </ul>
					</div>
            </form>
            </c:if>
            <script>
	            $(".dropdown-menu li p").click(function(){
	              $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
				  $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
				  $('.inputparam').val($(this).data('value'));
				  $('.inputparamname').val($(this).text());
				  
				 
				  $('.inputStartparam').val($('input[name=startUserDate]').val());
				  $('.inputEndparam').val($('input[name=endUserDate]').val());
				  
				  $("#search").submit() ;
				  //$(param)
				});
            </script>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown dropdown-with-icons">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-list"></i>
                        <p class="hidden-md hidden-lg">
							More
							<b class="caret"></b>
						</p>
                    </a>
                    <ul class="dropdown-menu dropdown-with-icons">
                        <li>
                            <a href="<c:url value="/logout" />" class="text-danger">
                                <i class="pe-7s-close-circle"></i>
                                Log out
                            </a>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </div>
</nav>
