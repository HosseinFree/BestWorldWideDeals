<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Panel</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Images/welcome/logo.ico"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/Admin/Index_Admin.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Admin/Header_Admin.css">
</head>
<body id="admin_header_body">
  <div id="header-wrapper">
     <div id="logo_wrapper">
       <a href="${pageContext.request.contextPath}/Index_hotels.jsp">
	      <div id="logo-div" class="header_divs">
	      </div>
	   </a>   
	   <div id="admin_menu_wrapper" class="header_divs">
	      	      <c:if test="${not empty param.active_page}">
			         <c:url value="Index_Admin.jsp" var="home_url">
			            <c:param name="active_page" value="home"/>
			         </c:url> 
			         <c:url value="Index_Admin.jsp" var="partner_url">
			            <c:param name="active_page" value="partner"/>
			         </c:url> 
			         
			         <c:if test="${param.active_page eq 'home'}">
			            <a href='<c:out value="${home_url}"/>'> <div class="admin_menu_items admin_menu_active_item">Home</div></a>
			         </c:if>
			         <c:if test="${param.active_page ne 'home'}">
			             <a href='<c:out value="${home_url}"/>'> <div class="admin_menu_items">Home</div></a>
			         </c:if>
			         
			         <c:if test="${param.active_page eq 'partner'}">
			             <a href='<c:out value="${partner_url}"/>'><div class="admin_menu_items admin_menu_active_item">Partner</div></a>
			         </c:if>
			         <c:if test="${param.active_page ne 'partner'}">
			             <a href='<c:out value="${partner_url}"/>'><div class="admin_menu_items">Partner</div></a>
			         </c:if>
			      </c:if>
	   </div>
	   <c:if test="${userlogedin}">
	      <a href="${pageContext.request.contextPath}/handle_admin_logout">
		   <div id="admin_logout_div">
		      <p>Logout</p>
		   </div>
		  </a>  
	   </c:if>
	 </div>   
  </div>
</body>
</html>