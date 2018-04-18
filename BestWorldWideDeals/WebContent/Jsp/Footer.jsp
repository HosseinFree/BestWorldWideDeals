<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	    
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="loc" value="en_GB"/>
<c:if test="${!(empty param.locale)}">
   <c:set var="loc" value="${param.locale}"/>
</c:if>
<fmt:setLocale value="${loc}"/>
<head>
<fmt:bundle basename="app">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/Footer.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Footer.css">
</head>
<body>

<div id="footer_wrapper">
  <div id="footer_top_div">
    <ul id="footer_top_div_list">
     <li  id="footer_top_div_firstDiv">
		    <p><fmt:message key="connectWithUs"/></p>		
			<ul id="social_media">
			    <li> 
			    	<a href=""><div id="facebook_icon"></div></a>
				</li>		
				<li> 
			    	<a href=""><div id="twitter_icon"></div></a>
				</li>	
				<li> 
			    	<a href=""><div id="linkedin_icon"></div></a>
				</li>	
				<li> 
			    	<a href=""><div id="youtube_icon"></div></a>
				</li>	
				<li> 
			    	<a href=""><div id="instagram_icon"></div></a>
				</li>	
							
			</ul>
	  </li>
	  <li id="footer_top_div_secondDiv">
	    <div id="news_div">
		    <p><fmt:message key="joinOurNewsletter"/></p>
		    <div id="submit"><input type="text" id="news_email" placeholder="<fmt:message key="enterYourEmailAddress"/>" />
		    <div><fmt:message key="submit"/></div></div>
		</div>   
	  </li>
    </ul> 
  </div>
  
  <div id="footer_bottom_div">
       <div align="center">
		   <ul id="footermenu">
			  <li><a href=""><fmt:message key="help"/></a></li>
			  <li><a href=""><fmt:message key="about"/></a></li>
			  <li><a href=""><fmt:message key="disclaimer"/></a></li>
			  <li><a href=""><fmt:message key="contactUs"/></a></li>
			  <li><a href=""><fmt:message key="termsofUse"/></a></li>
			  <li><a href=""><fmt:message key="privacyPolicy"/></a></li>
			  <li><a href="Admin_Login.jsp"><fmt:message key="admin"/></a></li>
			  <li><span onclick="create_report()"><fmt:message key="reportaProblem"/></span></li>
		   </ul>
		   <p>&#169;
			  Copyright 2018 BestWorldWideDeals.com, All rights reserved.</p>
	    </div>
  
  </div>
</div>
</body>
</fmt:bundle>
</html>