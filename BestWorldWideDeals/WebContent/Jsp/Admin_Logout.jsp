<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Admin/Admin_Logout.css">
<%
    response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", -1);
%>
</head>

<body>
    <jsp:include page="../Admin/Header_Admin.jsp"/>
    <div id="Admin_logout_content">
      <p> You Have Been Successfully Logged Out From Admin Account. </p>
      <a href="../Admin_Login.jsp">Go To Admin Login Page  </a>
      <a href="../Index_hotels.jsp">Go To Home Page  </a>
    </div>
    <jsp:include page="../Admin/Footer_Admin.jsp"/>
</body>
</html>