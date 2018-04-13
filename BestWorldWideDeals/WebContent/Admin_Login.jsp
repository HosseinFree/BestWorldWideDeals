<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/JavaScript/Admin/Admin_Login.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Admin/Admin_Login.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%
    response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", -1);
%>	    
</head>

<body>
      <jsp:include page="./Admin/Header_Admin.jsp"/>
	        
      <div id="Admin_login_content">
        <div id="Admin_logindiv">
           <p id="Admin_loginmessage">Administrator  Login</p>
           <div id="Admin_topline"> </div>
              <form id="Admin_loginform" action="handle_admin_login" method="Post" onsubmit="return check_form()"> 
	             <div>
	                 <input type="text" id="Admin_un" name="username" class="Admin_login_Inputs" maxlength="40" placeholder="Enter Your Username...">
	                 <input type="password" id="Admin_pass" name="pass" class="Admin_login_Inputs" maxlength="15" placeholder="Enter Your Password...">
	                 <br>
	                 
	                 <c:if test="${ admin_login_error eq 'not_exist'}">
	                     <div id="Admin_error"> <p>Admin information you entered does not exist in our system.</p> </div>
	                 </c:if> 
	                 
	                 <c:if test="${ admin_login_error eq 'system_error'}">
	                     <div id="Admin_error"> <p>Sorry, we are having some technical difficulty at the moment. Please try again later.</p> </div>
	                 </c:if> 
	                 
	                 <c:if test="${ empty admin_login_error}">
	                     <div id="Admin_error" class="Admin_error_deactive"></div>
	                 </c:if> 
	                 
	                 <button type="submit" id="Admin_submitbutton">Login</button>
	             </div>                
	          </form>     
          </div> 
      </div> <!-- End of content-->
      
      <jsp:include page="./Admin/Footer_Admin.jsp"/> 
 </body>
 
</html>