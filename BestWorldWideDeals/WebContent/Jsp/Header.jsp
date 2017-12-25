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
            <div id="currency_div" onclick="toggle_cur_menu();">
               CAD<i id="cur_arrow" class="arrow_down"></i>
            </div>
         </li>
         <div id="currency_menu" class="cur_callout cur_border_callout" ><b class="cur_border_notch cur_notch"></b><b class="cur_notch"></b>
           <ul>
              <li class="cur_men_items"><div>CAD - <span>Canadian Dollar</span></div></li><br/>
              <li class="cur_men_items"><div>USD - <span>US Dollar</span></div></li><br/>
              <li class="cur_men_items"><div>EUR - <span>Euro</span></div></li><br/>
              <li class="cur_men_items"><div>AUD - <span>Australian Dollar</span></div></li><br/>
              <li class="cur_men_items"><div>GBP - <span>British Pound</span></div></li><br/>
              <li><div>CNY - <span>Chinese Yuan</span></div></li>             
            </ul>
         </div>
         <li>
            <div id="language_div" onclick="toggle_lan_menu();">
               EN<i id="lan_arrow" class="arrow_down"></i>
            </div>
         </li>
         <div id="language_menu" class="lan_callout lan_border_callout" ><b class="lan_border_notch lan_notch"></b><b class="lan_notch"></b>
           <ul>
              <li class="lan_men_items"><div><span>EN</span> <img src="${pageContext.request.contextPath}/Images/Header/EN_img.png"></div></li><br/>
              <li class="lan_men_items"><div>FR <img src="${pageContext.request.contextPath}/Images/Header/FR_img.png"></div></li><br/>
              <li class="lan_men_items"><div>ES <img src="${pageContext.request.contextPath}/Images/Header/ES_img.png"></div></li><br/>
              <li><div>ZH <img src="${pageContext.request.contextPath}/Images/Header/ZH_img.png"></div></li>             
            </ul>
         </div>
         <li >
            <div id="account_div" onclick="toggle_acc_menu();">
               My Account<i id="acc_arrow" class="arrow_down"></i>
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