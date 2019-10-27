<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<script src='<c:url value="/assets/js/chartist.min.js" />'></script>
<script src='<c:url value="/assets/js/chartist-plugin-barlabels.min.js" />'></script>



<script>

	$(function(){
		
		<c:if test="${not empty userRecords }">
		// 사옹자 날짜별 기록
		(function makeUserChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성
			var chartDatas = [];
			
			<c:forEach var="r" items="${userRecords }">
				chartLabels.push("${r.actDate }");
				chartDatas.push(${r.recordAmount });
			</c:forEach>
			
			var dataViews = {
		        labels: chartLabels,
		        series: [
		        	chartDatas
		        ]
		    };
		
		    var optionsViews = {
		        seriesBarDistance: 10,
		        classNames: {
		            bar: 'ct-bar ct-azure'
		        },
		        axisX: {
		            showGrid: false
		        },
		        horizontalBars : false,
		        plugins: [
			          Chartist.plugins.ctBarLabels()
		        ]
		    };
		
		    var responsiveOptionsViews = [
		        ['screen and (max-width: 640px)', {
		            seriesBarDistance: 5,
		            axisX: {
		                labelInterpolationFnc: function (value) {
		                    return value[0];
		                }
		            }
		        }]
		    ];
		
		    Chartist.Bar('#chartViewsUser', dataViews, optionsViews, responsiveOptionsViews);
		})();
		</c:if>
		
		
	});
	
	
	function individualRankDownload() {
		event.preventDefault();
		$('form#ReqForm').attr('action', 'statUser/download');
		$('form#ReqForm').submit();
	}
	
</script>


<div class="main-content">
    <div class="container-fluid">

		<ol class="breadcrumb" style="display:none;">
  			<li><a href="#">Breadcrumb 1</a></li>
			<li><a href="#">Breadcrumb 2</a></li>
			<li class="active">Current Page</li>
		</ol>

		


        <div class="row">
            <div class="col-md-12">
                
                <div class="card">
                	<div class="header">
                        <p class="category">카페 정보</p>
                    </div>
                    <div class="content">
                    	<c:if test="${empty cafeList }">
                    		<h5 class="text-danger">가입한 카페가 없습니다.</h5>
                    	</c:if>
						<c:if test="${not empty cafeList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>카페명</th>
										<th>관리자명</th>
										<th>카페개설일시</th>
										<th>카페가입일시</th>
									</tr>
									<c:forEach var="a" items="${cafeList }">
										<tr>
											<td>${a.cafename }</td>
											<td>${a.adminname }</td>
											<td>${a.opendate }</td>
											<td>${a.regdate }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

                    </div>
                    <%-- <div class="header">
                        <p class="category">건물 정보</p>
                    </div>
                    <div class="content">
                    	<c:if test="${empty buildingList }">
                    		<h5 class="text-danger">등록된 건물이 없습니다. 건물을 등록해주세요.</h5>
                    	</c:if>
						<c:if test="${not empty buildingList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>건물명</th>
										<th>건물 코드</th>
										<th>층수</th>
										<th>층간 계단 수</th>
										<th>주소</th>
										<th>GPS 위도</th>
										<th>GPS 경도</th>
										<th>관리자명</th>
										<th>등록일시</th>
									</tr>
									<c:forEach var="a" items="${buildingList }">
										<tr>
											<td>${a.buildName }</td>
											<td>${a.buildCode }</td>
											<td>${a.buildFloorAmt }</td>
											<td>${a.buildStairAmt }</td>
											<td>${a.buildAddr }</td>
											<td>${a.latitude }</td>
											<td>${a.longitude }</td>
											<td>${a.adminName }</td>
											<td>${a.buildRegTimeFormat }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

                    </div> --%>
                </div>
                
                
                <%-- <c:if test="${isSuperAdmin eq 'Y'}">
                <div class="card">
                    <div class="header">
                        <p class="category">건물별/일자별 정보</p>
                    </div>
                    <div class="content">
                    	<c:if test="${empty buildingUserList }">
                    		<h5 class="text-danger">건몰 로그가 없습니다.</h5>
                    	</c:if>
						<c:if test="${not empty buildingUserList }">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<th>건물명</th>
										<th>건물 코드</th>
										<th>오른 층수</th>
										<th>이용일시</th>
									</tr>
									<c:forEach var="a" items="${buildingUserList }">
										<tr>
											<td>
												<a href="building?cSeq=${a.custSeq }&bSeq=${a.buildSeq }">
													${a.buildName }
												</a>	
											</td>
											<td>${a.buildCode }</td>
											<td>${a.sActAmt}</td>
											<td>${a.sActDate }</td>
											
										</tr>
									</c:forEach>
								</table>
							</div>
						</c:if>

                    </div>
                </div>
                </c:if> --%>
				
				<c:if test="${not empty userRecords }">
					
	               	<div class="card">
		                    
	                    <div class="header">
	                        <h4 class="title">${userRecords[0].userName } 개인 기록 </h4>
	                        <p class="category">${rank.startDate}  ~  ${rank.endDate}</p>
	                    </div>
		                    
	                    <div class="content">
	                    	<div id="chartViewsUser" class="ct-chart "></div>
	                    </div>
					</div> <!-- CARD-1 -->
				</c:if>
				
				
				
				
				
				
				
            </div>
        </div>
    </div>
    
    <form id="ReqForm">
		<input name="userSeq" type="hidden" value="${rank.userSeq }"/>
		<input name="custSeq" type="hidden" value="${rank.custSeq }"/>
		<input name="startDate" type="hidden" value="${rank.startDate }"/>
		<input name="endDate" type="hidden" value="${rank.endDate }"/>
		<input name="inpDeptSeq" type="hidden" value=""/>
	</form>	
						
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

