<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/common/template_top.jsp"  %>

<script src='<c:url value="/assets/js/chartist.min.js" />'></script>
<script src='<c:url value="/assets/js/chartist-plugin-barlabels.min.js" />'></script>



<script>

	var startDate = "${rank.startDate}";
	var endDate = "${rank.endDate}";
	var indexDate = startDate

	jQuery(document).ready(function() {
		$('select[name="selectCafe"]').change(function(e) {
			location.href="statCafeUser?cafeseq="+$(this).val();
		});
    });
		
	$(function(){
		$("#sortTable").tablesorter();
		
		// 개인별 랭킹 기록
		(function makeRankingStairChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성 (최대 10위 까지)
			var chartDatas = [];
			<c:forEach var="r" items="${ranks }" end="9">
				chartLabels.push("${r.userName }");
				chartDatas.push(${r.recordAmount });
			</c:forEach>
			
			var dataViews = {
		        labels: chartLabels,
		        series: [
		        	chartDatas
		        ]
		    };
		
		    var optionsViews = {
		        seriesBarDistance: 30,
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
		
		    Chartist.Bar('#chartViews', dataViews, optionsViews, responsiveOptionsViews);
		    

		    
		})();
		
		(function makeRankingWalkChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성 (최대 10위 까지)
			var chartWalkDatas = [];
			<c:forEach var="r" items="${ranks }" end="9">
				chartLabels.push("${r.userName }");
				chartWalkDatas.push(${r.recordWalk });
			</c:forEach>
			
			var dataViews = {
		        labels: chartLabels,
		        series: [
		        	chartWalkDatas
		        ]
		    };
		
		    var optionsViews = {
		        seriesBarDistance: 30,
		        classNames: {
		            bar: 'ct-bar ct-red'
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
		
		    Chartist.Bar('#chartWalkViews', dataViews, optionsViews, responsiveOptionsViews);
		    

		    
		})();
		
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
		        seriesBarDistance: 30,
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
		
		(function makeUserChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성
			var chartWalkDatas = [];
			
			<c:forEach var="r" items="${userRecords }">
				chartLabels.push("${r.actDate }");
				chartWalkDatas.push(${r.recordWalk });
			</c:forEach>
			
			var dataViews = {
		        labels: chartLabels,
		        series: [
		        	chartWalkDatas
		        ]
		    };
		
		    var optionsViews = {
		        seriesBarDistance: 30,
		        classNames: {
		            bar: 'ct-bar ct-red'
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
		
		    Chartist.Bar('#chartViewsWalkUser', dataViews, optionsViews, responsiveOptionsViews);
		})();
		</c:if>
		
		
		<c:if test="${not empty departRanks }">
		// 사옹자 날짜별 기록
		(function makeUserChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성
			var chartDatas = [];
			
			<c:forEach var="r" items="${departRanks }">
				chartLabels.push("${r.deptName }");
				chartDatas.push(${r.recordAmount });
				
			</c:forEach>
			
			var dataViews = {
		        labels: chartLabels,
		        series: [
		        	chartDatas
		        ]
		    };
		
		    var optionsViews = {
		        seriesBarDistance: 30,
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
		
		    Chartist.Bar('#chartViewsDepart', dataViews, optionsViews, responsiveOptionsViews);
		})();
		</c:if>
		
		
		
		
		
		
		
		
		
	    
	    // 달력
	    $('.datetimepicker').datetimepicker({
    		locale : "ko",
    		defaultDate : false,
    		useCurrent : false,
    		format : 'YYYYMMDD',
    		dayViewHeaderFormat : "YYYY MM",
    		minDate : moment().add(-2, 'year'),
    		maxDate : moment().add(1,'day'),
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
	    
	});
	
	var showDetail = function(userSeq) {
		document.searchForm.userSeq.value = userSeq;
		document.searchForm.submit();
	}
	
	$("#searchForm").on("submit", function(){
		var startDate = document.searchForm.startDate.value;
		var endDate = document.searchForm.endDate.value;
		if (startDate == ""){
			alert("조회 시작일을 입력해주세요.");
			return false;
		}
		if (endDate == ""){
			alert("조회 종료일을 입력해주세요.");
			return false;
		}
		if (startDate > endDate){
			alert("조회 종료일을 시작일보다 크게 입력해주세요.");
			return false;
		}
		return true;
	});
	 
	
	function individualRankDownload() {
		event.preventDefault();
		$('form#ReqForm').attr('action', 'statCafeUser/download');
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
                        <h4 class="title"></h4>
                    </div>
                    
                	<div class="content">
                		<form name="searchForm" method="get" class="form-horizontal">
							<input name="userSeq" type="hidden" value="${rank.userSeq }"/>
							<!--  <input name="cateseq" type="hidden" value=""/> -->
							<input name="cafeseq" type="hidden" value="${cafeseq }"/>
							<input name="cafename" type="hidden" value="${cafename }"/>
							<input name="type" type="hidden" value="${type }"/>
							
							<c:if test="${cafeSelected eq false}">
								<fieldset>
									<div class="form-group">
										<label class="col-sm-1 control-label">내 Cafe</label>
		                    			<div class="col-sm-3">
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
					                    			<div class="col-sm-2">
					                    				<select name="selectCafe" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
					                    					<option value="">Cafe를 선택해주세요.</option>
							                    			<c:forEach var="a" items="${cafeList }">
																<option value="${a.cafeseq }" <c:if test="${cafeseq eq a.cafeseq}">selected</c:if> > ${a.cafename }</option>
															</c:forEach>
								                    	</select>
					                    			</div>
											</c:otherwise>
										</c:choose>	
									
										
	                    			
										<label class="col-sm-1 control-label">날짜 선택</label>
										<div class="col-sm-7">
											<div class="row">
												<div class="col-sm-4">
													<input name="startDate" type="text" class="form-control datetimepicker" placeholder="조회 시작일자 선택해주세요" value="${rank.startDate }">
												</div>
												<label class="col-sm-1 control-label" style="text-align:center;"> ~ </label>
												<div class="col-sm-4">
													<input name="endDate" type="text" class="form-control datetimepicker" placeholder="조회 종료일자 선택해주세요" value="${rank.endDate }">
												</div>
												
												<div style="text-align:right;">
													<input type="submit" style="float:right;" class="btn btn-fill btn-primary" value="조회">
													
												</div>
											</div>
										</div>
									</div>
								</fieldset>
								<c:if test="${not empty categoryList}">
									<fieldset>
										<div class="form-group">
											<label class="col-sm-2 control-label">카테고리 선택</label>
											<div class="col-sm-4">
												<select name="cateseq" class="selectpicker" data-title="" data-style="btn-default btn-block" data-menu-style="dropdown-blue">
													<option value="">카테고리 전체</option>
			                    					<c:forEach var="d" items="${categoryList }">
														<option value="${d.cateseq }" <c:if test="${cateseq eq d.cateseq}">selected</c:if> >${d.name }</option>
													</c:forEach>
		                    					</select>
											</div>
										</div>
									</fieldset>
								</c:if>
							</c:if>
						</form>
                	</div>
                </div>
                
                <c:if test="${cafeSelected eq true}">
               
	               	<div class="card">
		                    
	                    <div class="header">
	                        <h4 class="title">개인별 랭킹</h4>
	                        <p class="category">전체 ${totalAmount } 층 오름</p>
	                    </div>
		                    
	                    <div class="content">
	                    	<c:if test="${empty ranks }">
	                    		<p class="text-danger">현재 랭킹 데이타가 없습니다.</p>
	                    	</c:if>
	                    	<c:if test="${not empty ranks }">
	                    		<div class="table-responsive">
									<table id="sortTable" class="tablesorter table">
										<thead>
										<tr>
											<th>랭킹</th>
											<th>아이디(이메일)</th>
											<th>사용자명</th>
											<th>닉네임</th>
											<th>추가정보</th>
											<th>카페명</th>
											<th>카테고리명</th>
											<th>가입경로</th>
											<th>OS</th>
											<th>총 오른 층수</th>
											<th>총 걸음수</th>
											<th>ACTION</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach var="r" items="${ranks }">
											<tr>
												<%-- <td>${r.ranking }</td> --%>
												<td>${r.order }</td>
												<td>${r.userEmail }</td>
												<td>${r.userName }</td>
												<td>${r.nickName }</td>
												<td>${r.additions }</td>
												<td>${r.cafename }</td>
												<td>${r.catename }</td>
												<td>${r.loginType }</td>
												<td>${r.deviceType }</td>
												<td>${r.recordAmount }</td>
												<td>${r.recordWalk }</td>
												<td class="tx-actions text-center">
													<button type="button" onclick="showDetail(${r.userSeq})" rel="tooltip" title="상세" class="btn b/*tn-primary btn-simple btn-icon">
	                                                    <i class="fa fa-area-chart"></i>
	                                                </button>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
									
									<button type="submit" style="margin-top:20px;margin-bottom:20px;" class="btn btn-fill btn-success" onclick="individualRankDownload();">파일 DOWN</button>
								</div>
								
								
								<c:if test="${not empty userRecords }">
						
					               	<div class="card">
						                    
					                    <div class="header">
					                        <h4 class="title">${userRecords[0].userName } 개인 기록 </h4>
					                        <p class="category">${rank.startDate}  ~  ${rank.endDate}</p>
					                    </div>
						                    
					                    <div class="content">
					                    	<div style="text-align:center;">
												<div style="width:20px;height:20px;background:#23CCEF;float:left;margin-left:10px;"></div>
												<span style="float:left;padding-left:10px;">계단수</span>
											</div>
											
					                    	<div id="chartViewsUser" class="ct-chart "></div>
					                    </div>
					                    
					                    <div class="content">
					                    	<div style="text-align:center;">
												<div style="width:20px;height:20px;background:#FB404B;float:left;margin-left:10px;"></div>
												<span style="float:left;padding-left:10px;">걸음수</span>
											</div>
											
					                    	<div id="chartViewsWalkUser" class="ct-chart "></div>
					                    </div>
									</div> <!-- CARD-1 -->
								</c:if>
								
								
		                    	<div class="header">
		                        	<h4 class="title">${cafename } 기록 </h4>
		                        	<p class="category">${rank.startDate}  ~  ${rank.endDate}</p>
		                    	</div>
		                    
								<div style="text-align:center;padding-top:10px;">
									<div style="width:20px;height:20px;background:#23CCEF;float:left;margin-left:10px;"></div>
									<span style="float:left;padding-left:10px;">계단수</span>
								</div>
								
								<div id="chartViews" class="ct-chart "></div>
								
								<div style="text-align:center;padding-bottom:10px;">
									<div style="width:20px;height:20px;background:#FB404B;float:left;margin-left:10px;"></div>
									<span style="float:left;padding-left:10px;">걸음수</span>
								</div>
								
								<div id="chartWalkViews" class="ct-chart "></div>
								
	                    	</c:if>
	                    	
	                    </div>
		                    
					</div> <!-- CARD-1 -->
					
					
					
					
					<c:if test="${not empty departRanks }">
						
		               	<div class="card">
			                    
		                    <div class="header">
		                        <h4 class="title">부서별 기록</h4>
		                        <p class="category"></p>
		                    </div>
			                    
		                    <div class="content">
		                    	<div id="chartViewsDepart" class="ct-chart "></div>
		                    </div>
						</div> <!-- CARD-1 -->
					</c:if>
				
				
				</c:if>
				
				
            </div>
        </div>
    </div>
    
    <form id="ReqForm">
    	<input name="startDate" type="hidden" value="${rank.startDate}"/>
    	<input name="endDate" type="hidden" value="${rank.endDate}"/>
    	<input name="cafeseq" type="hidden" value="${cafeseq}"/>
		<input name="cateseq" type="hidden" value="${rank.cateseq}"/>
		<input name="catename" type="hidden" value="${rank.catename}"/>
	</form>	
						
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

