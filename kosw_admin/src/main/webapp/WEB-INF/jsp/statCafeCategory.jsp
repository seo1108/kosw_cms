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
	var custSeq = "${rank.custSeq}";
	
	jQuery(document).ready(function() {
		$('select[name="selectCafe"]').change(function(e) {
			location.href="statCafeCategory?cafeseq="+$(this).val();
		});
    });

	$(function(){
		
		// 회사별 랭킹
		(function makeCompanyChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성
			var chartDatas = [];
			
			<c:forEach var="r" items="${companyRanks }" end="9">
				chartLabels.push("${r.custName }");
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
		
		    Chartist.Bar('#chartViewCompany', dataViews, optionsViews, responsiveOptionsViews);
		})();
		
		// 부서별 랭킹 기록
		(function makeDepartChart(){
			// 차트 x 축 라벨 구성
			var chartLabels = [];
			
			// 차트 데이타 구성 (최대 10위 까지)
			var chartDatas = [];
			<c:forEach var="r" items="${departRanks }" end="9">
				chartLabels.push("${r.cafename }" + "-"+ "${r.catename }");
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
		
		    Chartist.Bar('#chartViewDepart', dataViews, optionsViews, responsiveOptionsViews);
		})();
		
		
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
	
	
	$(document.searchForm).on("submit", function(){
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
                				<%-- <input name="custSeq" type="hidden" value="${rank.custSeq }"/> --%>
                				<input name="cafeseq" type="hidden" value="${cafeseq }"/>
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
												<div class="col-sm-2">
													<input name="startDate" type="text" class="form-control datetimepicker" placeholder="조회 시작일자 선택해주세요" value="${rank.startDate }">
												</div>
												<label class="col-sm-1 control-label" style="text-align:center;"> ~ </label>
												<div class="col-sm-2">
													<input name="endDate" type="text" class="form-control datetimepicker" placeholder="조회 종료일자 선택해주세요" value="${rank.endDate }">
												</div>
												
												<div class="col-sm-3 text-right">
													<input type="submit" style="float:right;" class="btn btn-fill btn-primary" value="조회">
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</c:if>
								
						</form>
                	</div>
                </div>
                
                <c:if test="${cafeSelected eq true}">
	   <%--              <div class="card">
	                    <div class="header">
	                        <h4 class="title"></h4>
	                        <p class="category">회사별 랭킹</p>
	                    </div>
		                    
	                    <div class="content">
	                    	<c:if test="${empty companyRanks }">
	                    		<p class="text-danger">현재 랭킹 데이타가 없습니다.</p>
	                    	</c:if>
	                    	<c:if test="${not empty companyRanks }">
	                    		<div class="table-responsive">
									<table class="table">
										<tr>
											<th>랭킹</th>
											<th>회사명</th>
											<th>총 오른 층수</th>
											<c:if test="${user.superAdmin eq 'true'}">
												<th>ACTION</th>
											</c:if>
										</tr>
										<c:forEach var="r" items="${companyRanks }">
											<tr>
												<td>${r.ranking }</td>
												<td>${r.custName }</td>
												<td>${r.recordAmount }</td>
												<c:if test="${user.superAdmin eq 'true'}">
													<td class="tx-actions text-center">
														<c:url value='/statUser' var="rankDetailUrl">
															<c:param name="custSeq" value="${r.custSeq }"/>
															<c:param name="startDate" value="${rank.startDate }"/>
															<c:param name="endDate" value="${rank.endDate }"/>
														</c:url>
		                                                <a type="button" href="${rankDetailUrl }" rel="tooltip" title="개인별 랭킹" class="btn btn-primary btn-simple btn-icon">
		                                                    <i class="fa fa-area-chart"></i>
		                                                </a>
													</td>
												</c:if>
											</tr>
										</c:forEach>
									</table>
								</div>
								
								<div id="chartViewCompany" class="ct-chart "></div>
								
	                    	</c:if>
	                    	
	                    </div>
		                    
					</div> <!-- CARD-1 -->
		 --%>			
					
					
					 <div class="card">
	                    <div class="header">
	                        <h4 class="title"></h4>
	                        <p class="category">카테고리별 랭킹</p>
	                    </div>
		                    
	                    <div class="content">
	                    	<c:if test="${empty departRanks }">
	                    		<p class="text-danger">현재 랭킹 데이타가 없습니다.</p>
	                    	</c:if>
	                    	<c:if test="${not empty departRanks }">
	                    		<div class="table-responsive">
									<table class="table">
										<tr>
											<th>랭킹</th>
											<th>카페명</th>
											<th>카테고리명</th>
											<th>총 오른 층수</th>
										</tr>
										<c:forEach var="r" items="${departRanks }">
											<tr>
												<td>${r.ranking }</td>
												<td>${r.cafename }</td>
												<td>${r.catename }</td>
												<td>${r.recordAmount }</td>
											</tr>
										</c:forEach>
									</table>
								</div>
								
								<div id="chartViewDepart" class="ct-chart "></div>
								
	                    	</c:if>
	                    	
	                    </div>
		                    
					</div> <!-- CARD-1 -->
				</c:if>
				
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/template_bot.jsp"  %>

