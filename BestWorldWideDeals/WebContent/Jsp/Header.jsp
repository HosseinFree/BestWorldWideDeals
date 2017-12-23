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
<script defer src="${pageContext.request.contextPath}/JavaScript/Header.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Header.css">

</head>
<body>

<div id="header-wrapper">
   <div id="logo-div">
   </div>
   <div id="menu">
     <div id="large-menu">
       <ul>
         <li>
            <div id="currency_div">
               CAD<i class="arrow_down"></i>
            </div>
         </li>
         <li>
            <div id="language_div">
               EN<i class="arrow_down"></i>
            </div>
         </li>
         <li >
            <div id="account_div" onclick="toggle_acc_menu();">
               My Account<i class="arrow_down"></i>
            </div> 
         </li>
         <div id="account_menu" class="callout border_callout" ><b class="border_notch notch"></b><b class="notch"></b>
           <ul>
              <a href="#"><li class="acc_men_items" id="acc_men_first_item"><div>Login</div></li></a><br/>
              <a href="#"><li class="acc_men_items"><div>Sign up</div></li></a>              
            </ul>
         </div>
       </ul>
     </div>
   </div>
</div>

</body>
</html>