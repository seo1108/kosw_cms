<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--   Core JS Files  -->
    <script src='<c:url value="/assets/js/jquery.min.js" />' type="text/javascript"></script>
	<script src='<c:url value="/assets/js/bootstrap.min.js" />' type="text/javascript"></script>
	<script src='<c:url value="/assets/js/perfect-scrollbar.jquery.min.js" />' type="text/javascript"></script>
	<!--  Forms Validations Plugin -->
	<script src='<c:url value="/assets/js/jquery.validate.min.js" />'></script>
	<!--  Plugin for Date Time Picker and Full Calendar Plugin-->
	<script src='<c:url value="/assets/js/moment.min.js" />'></script>
	
	<script src='<c:url value="/js/moment-with-locales.min.js" />'></script>
	
    <!--  Date Time Picker Plugin is included in this js file -->
    <script src='<c:url value="/assets/js/bootstrap-datetimepicker.min.js" />'></script>
    <!--  Select Picker Plugin -->
    <script src='<c:url value="/assets/js/bootstrap-selectpicker.js" />'></script>
	<!--  Checkbox, Radio, Switch and Tags Input Plugins -->
	<script src='<c:url value="/assets/js/bootstrap-switch-tags.min.js" />'></script>
	<!--  Charts Plugin -->
	<script src='<c:url value="/assets/js/chartist.min.js" />'></script>
    <!--  Notifications Plugin    -->
    <script src='<c:url value="/assets/js/bootstrap-notify.js" />'></script>
    <!-- Sweet Alert 2 plugin -->
	<script src='<c:url value="/assets/js/sweetalert2.js" />'></script>
    <!-- Vector Map plugin -->
	<script src='<c:url value="/assets/js/jquery-jvectormap.js" />'></script>
	<!-- Wizard Plugin    -->
    <script src='<c:url value="/assets/js/jquery.bootstrap.wizard.min.js" />'></script>
	<!--  Bootstrap Table Plugin    -->
	<script src='<c:url value="/assets/js/bootstrap-table.js" />'></script>
	<!--  Plugin for DataTables.net  -->
	<script src='<c:url value="/assets/js/jquery.datatables.js" />'></script>
    <!--  Full Calendar Plugin    -->
    <script src='<c:url value="/assets/js/fullcalendar.min.js" />'></script>
    <!-- Light Bootstrap Dashboard Core javascript and methods -->
	<script src='<c:url value="/assets/js/light-bootstrap-dashboard.js?v=1.4.1" />' ></script>
	<!-- Light Bootstrap Dashboard DEMO methods, don't include it in your project! -->
	<script src='<c:url value="/assets/js/demo.js" />'></script>
	
	<script src='<c:url value="/assets/js/jquery.tablesorter.min.js" />'></script>
	<script src='<c:url value="/assets/js/jquery.tablesorter.widgets.js" />'></script>
	
	<script>
	(function($) {
		/* JQUERY VALIDATOR CUSTOM */
		jQuery.validator.addMethod("regex", function(value, element, regexp) {
	                    		  return this.optional(element) || regexp.test(value);
	                    	}, "입력 형식을 확인해주세요.");
		
		/* SELECT BAR */
		if($(".selectpicker").length != 0){
				$(".selectpicker").selectpicker();	
		}
	})(jQuery);
	</script>