<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src='<c:url value="/assets/js/chartist.min.js" />'></script>
<script src='<c:url value="/assets/js/chartist-plugin-barlabels.min.js" />'></script>

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
	
	<div id="chartViewsUserStair" class="ct-chart "></div>
	</div>
	
	<div class="content">
		<div style="text-align:center;">
			<div style="width:20px;height:20px;background:#FB404B;float:left;margin-left:10px;"></div>
		<span style="float:left;padding-left:10px;">걸음수</span>
	</div>
	<div id="chartViewsUserWalk" class="ct-chart "></div>
	</div>
</div> <!-- CARD-1 -->

							
<script>
(function makeUserStairChart(){
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
        seriesBarDistance: 20,
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

    Chartist.Bar('#chartViewsUserStair', dataViews, optionsViews, responsiveOptionsViews);
})();

(function makeUserWalkChart(){
	// 차트 x 축 라벨 구성
	var chartLabels1 = [];
	
	// 차트 데이타 구성
	var chartWalkDatas = [];
	
	<c:forEach var="r" items="${userRecords }">
	chartLabels1.push("${r.actDate }");
		chartWalkDatas.push(${r.recordWalk });
	</c:forEach>
	
	var dataViews = {
        labels: chartLabels1,
        series: [
        	chartWalkDatas
        ]
    };

    var optionsViews = {
        seriesBarDistance: 20,
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

    Chartist.Bar('#chartViewsUserWalk', dataViews, optionsViews, responsiveOptionsViews);
})();
</script>