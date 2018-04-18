<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="preload" href="${pageContext.request.contextPath}/Images/welcome/bg_1.jpeg" as="image">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Images/welcome/logo.ico"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Best World Wide Deals</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/welcome/index.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/welcome/index.js"></script>
</head>
<body>
  <div id="header">
    <div>
    </div>
  </div>
  <div id="container">
    <div id="content">
      <p class="title"> Our Website is Coming Soon </p>
       
      <p class="text_content"> 
      In <span>BestWorldWideDeals.com</span>, we are working hard to build one of the world's leading 
      meta-search websites. Our main goal is to help you find the best deals for the services
      or products you are looking for, by comparing prices from hundreds of third-party websites and apps.
      In the first phase, The website will be lunched for Hotel deals, and, 
      in next phases, we will include more categories such as Electronics, 
      Clothing, Home and Garden, and much more.
      </p>
      
      <p class="email_content">
        Enter Your Email To Get Notified<br/>
        <span>
            and receive our exclusive grand opening deals.
        </span>
      </p>
      <div id="submit" ><input type="text" id="news_email" placeholder="Enter Your Email Address..." />
      <div onclick="check_email()">Subscribe</div></div>
      
      <div id="email_err"  class="callout border_callout"> 
         <b class='border_notch notch'></b><b class='notch'></b>
         <p></p>
         
      </div>
      
      <div id="social_media">
          <ul id="social_media">
			   <li> 
			    	<a href="https://www.facebook.com/BestWorldWideDeals/"><div id="facebook_icon"></div></a>
			   </li>		
			   <li> 
			    	<a href="https://twitter.com/_BWWD_"><div id="twitter_icon"></div></a>
			   </li>	
			   <li> 
			    	<a href="https://www.instagram.com/bestworldwidedeals.5456/"><div id="instagram_icon"></div></a>
			   </li>	
		  </ul>
      </div>
    </div>
  </div>
</body>
</html>