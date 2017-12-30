<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
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
		    <p>Connect With Us</p>		
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
		    <p>Join Our Newsletter</p>
		    <div id="submit"><input type="text" id="news_email" placeholder="Enter Your Email Address" /><div>Submit</div></div>
		</div>   
	  </li>
    </ul> 
  </div>
  
  <div id="footer_bottom_div">
       <div align="center">
		   <ul id="footermenu">
			  <li><a href="">Help</a></li>
			  <li><a href="">About</a></li>
			  <li><a href="">Disclaimer</a></li>
			  <li><a href="">Contact Us</a></li>
			  <li><a href="">Terms of Use</a></li>
			  <li><a href="">Privacy Policy</a></li>
			  <li><span onclick="create_report()">Report a Problem</span></li>
		   </ul>
		   <p>&#169;
			  Copyright 2018 BestWorldWideDeals.com, All rights reserved.</p>
	    </div>
  
  </div>
  
</div>

</body>
</html>