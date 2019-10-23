<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<div class="main-content">
    <div class="container-fluid">

		<div class="card card-plain" style="display:none;">
			<h4 class="title">캐릭터 리스트</h4>
			<p class="category"></p>
		</div>
		
        <div class="row">
            <div class="col-md-12">
            	<div class="card">
                    <div class="content">
                    	<c:if test="${empty characterList }">
                    		<p class="text-danger">등록된 캐릭터가 없습니다.</p>
                    	</c:if>
                    	<c:if test="${not empty characterList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>THUMB</th>
										<th>캐릭터명</th>
										<th>사용자수</th>
										<th>등록인</th>
										<th>등록일시</th>
										<th>기본캐릭터</th>
										<th>사용여부</th>
										<th>ACTION</th>
									</tr>
									<c:forEach var="a" items="${characterList }">
										<c:url var="characterUrl" value="/characterFile" />
										<c:set var="characterUrl" value="${characterUrl}/${a.charSeq }" />
										<c:url var="characterEdit" value="/characterEdit" >
											<c:param name="code" value="${a.charCode }"/>
										</c:url>
										<tr>
											<td class="text-center">
												<img src="${characterUrl }" style="height:80px;"/>
											</td>
											<td>
												${a.charName }
											</td>
											<td>${a.useCount }</td>
											<td>${a.adminName }</td>
											<td>${a.charRegTimeFormat }</td>
											<td>
												<c:if test="${a.charDefaultYn eq 'Y'}"><p class="text-primary">기본캐릭터</p></c:if>
												<c:if test="${a.charDefaultYn eq 'N'}"><p class="">추가캐릭터</p></c:if>
											</td>
											<td>
												<c:if test="${a.charActiveFlag eq 'Y'}"><p class="text-primary">사용</p></c:if>
												<c:if test="${a.charActiveFlag eq 'N'}"><p class="text-danger">비사용</p></c:if>
											</td>
											
											<td class="tx-actions text-right">
                                                <a href="${characterEdit }" rel="tooltip" title="수정" class="btn btn-warning btn-simple" style="display:inline-block;">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <form class="deleteForm" method="post" action="<c:url value="characterDelete"/>" style="display:inline-block;">
                                                	<input name="charCode" type="hidden" value="${a.charCode }"/>
	                                                <button type="submit" rel="tooltip" title="삭제" class="btn btn-danger btn-simple btn-icon remove">
	                                                    <i class="fa fa-times"></i>
	                                                </button>
                                                </form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>
                    </div>
                    
                </div> <!-- CARD-1 -->
                
                <script>
                	$(function(){
                		$("form.deleteForm").on("submit", function(){
                			var form = this;
                			swal({
                				title : "정말로 삭제 하시겠습니까?",
                				text : "해당 캐릭터를 사용 중인 사용자는 기본 캐릭터가 표시됩니다.",
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
                	})
                </script>
                
			
				<div class="text-center">
					<a href='<c:url value="characterAdd" />' class="btn btn-primary btn-fill" >신규 캐릭터 등록</a>
				</div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

