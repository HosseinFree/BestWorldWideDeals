<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Admin/Index_Admin.css">
<%
    response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", -1);
%>

</head>

<body>
 
	 <c:if test="${not empty param.active_page}">
	     <jsp:include page="./Header_Admin.jsp">
	  		<jsp:param value="${param.active_page}" name="active_page"/>
	     </jsp:include>
	  </c:if>
	  
	  <c:if test="${empty param.active_page}">
	     <jsp:include page="./Header_Admin.jsp">
	  		<jsp:param value="home" name="active_page"/>
	     </jsp:include>
	  </c:if>
	   
	  <div id="main_content">
	       <c:if test="${not empty param.active_page}">
	           <c:if test="${param.active_page eq 'partner'}">
		          <jsp:include page="./Admin_Partners.jsp"/>
		       </c:if> 
	       </c:if>
	  </div>
	  
	  <jsp:include page="./Footer_Admin.jsp"/>
  
</body>
</html>