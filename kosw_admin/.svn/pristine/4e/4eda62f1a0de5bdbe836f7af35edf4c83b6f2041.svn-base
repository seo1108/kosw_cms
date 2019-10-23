<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!-- FOOTER -->
        <%@ include file="/WEB-INF/jsp/common/footer.jsp"  %>
    </div>   <!-- main-panel -->
</div>   <!-- wrapper --> 

<% 
	String script = (String)request.getAttribute("script");
	if (script != null){
%>
		<script><%=script %></script>
<%
	}
%>


<% 
	String message = (String)request.getAttribute("message");
	if (message != null){ 
%>
	<script>
			alert("<%=message %> ");
	</script>
<% }%>

</body>


    
</html>