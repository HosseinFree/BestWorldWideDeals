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
			<div id="small_menu_icon" class="small_menu_norm"
				onclick="small_menu_view_switch(this);toggle_small_menu()">
				<div class="bar1"></div>
				<div class="bar2"></div>
				<div class="bar3"></div>
			</div>

			<div id="large-menu">
				<ul>
					<li class="large_menu_items">
						<div id="currency_div" onclick="toggle_cur_menu();">USD</div>
						<i id="cur_arrow" class="arrow_down"></i>
					</li>
					<div id="currency_menu" class="cur_callout cur_border_callout">
						<b class="cur_border_notch cur_notch"></b><b class="cur_notch"></b>
						<ul>
							<li class="cur_men_items" onclick="set_value('currency','CAD')"><div>
									CAD - <span>Canadian Dollar</span>
								</div></li>
							<br />
							<li class="cur_men_items" onclick="set_value('currency','USD')"><div>
									USD - <span>US Dollar</span>
								</div></li>
							<br />
							<li class="cur_men_items" onclick="set_value('currency','EUR')"><div>
									EUR - <span>Euro</span>
								</div></li>
							<br />
							<li class="cur_men_items" onclick="set_value('currency','AUD')"><div>
									AUD - <span>Australian Dollar</span>
								</div></li>
							<br />
							<li class="cur_men_items" onclick="set_value('currency','GBP')"><div>
									GBP - <span>British Pound</span>
								</div></li>
							<br />
							<li><div onclick="set_value('currency','CNY')">
									CNY - <span>Chinese Yuan</span>
								</div></li>
						</ul>
					</div>
					<li class="large_menu_items">
						<div id="language_div" onclick="toggle_lan_menu();">EN</div>
						<i id="lan_arrow" class="arrow_down"></i>
					</li>
					<div id="language_menu" class="lan_callout lan_border_callout">
						<b class="lan_border_notch lan_notch"></b><b class="lan_notch"></b>
						<ul>
							<li class="lan_men_items" onclick="set_value('language','EN')"><div>
									<span>EN</span> <img
										src="${pageContext.request.contextPath}/Images/Header/EN_img.png">
								</div></li>
							<br />
							<li class="lan_men_items" onclick="set_value('language','FR')"><div>
									FR <img
										src="${pageContext.request.contextPath}/Images/Header/FR_img.png">
								</div></li>
							<br />
							<li class="lan_men_items" onclick="set_value('language','ES')"><div>
									ES <img
										src="${pageContext.request.contextPath}/Images/Header/ES_img.png">
								</div></li>
							<br />
							<li><div onclick="set_value('language','ZH')">
									ZH <img
										src="${pageContext.request.contextPath}/Images/Header/ZH_img.png">
								</div></li>
						</ul>
					</div>
					<li class="large_menu_items">
						<div id="account_div" onclick="toggle_acc_menu();">My
							Account</div>
						<i id="acc_arrow" class="arrow_down"></i>
					</li>
					<div id="account_menu" class="callout border_callout">
						<b class="border_notch notch"></b><b class="notch"></b>
						<ul>
							<a href="#"><li class="acc_men_items" id="acc_men_first_item"><div>Login</div></li></a>
							<br />
							<a href="#"><li class="acc_men_items"><div>Sign up</div></li></a>
						</ul>
					</div>
				</ul>
			</div> <!-- End of large-menu -->
		</div> <!--  End of menu div -->
	</div><!-- End of  header-wrapper-->
<div id="cover_screen">
</div>
<div id="small_menu">
  <ul id="first_small_ul">
    <li class="small_menu_items">
      <div id="small_account_div"  onclick="toggle_small_acc_menu()">
          My Account<i id="small_acc_arrow" class="arrow_down"></i>
      </div>
      <div id="small_account_menu" class="small_menu_divs">
           <ul>
              <a href="#"><li id="small_acc_men_first_item" class="small_menu_subitems first_submenu_item"><div>Login</div></li></a>
              <a href="#"><li class="small_menu_subitems last_submenu_item"><div>Sign up</div></li></a>              
            </ul>
      </div> 
    </li>
    <li class="small_menu_items">
      <div id="small_currency_div" onclick="toggle_small_cur_menu()">
         <span id="small_currency_val">USD</span><i id="small_cur_arrow" class="arrow_down"></i>
      </div>
      <div id="small_currency_menu" class="small_menu_divs">
           <ul class="small_currency_menu_items">
              <li class="small_menu_subitems first_submenu_item" onclick="set_value('#small_currency_val','CAD')"><div>CAD - <span>Canadian Dollar</span></div></li>
              <li class="small_menu_subitems" onclick="set_value('currency','USD')"><div>USD - <span>US Dollar</span></div></li>
              <li class="small_menu_subitems" onclick="set_value('currency','EUR')"><div>EUR - <span>Euro</span></div></li>
              <li class="small_menu_subitems" onclick="set_value('currency','AUD')"><div>AUD - <span>Australian Dollar</span></div></li>
              <li class="small_menu_subitems" onclick="set_value('currency','GBP')"><div>GBP - <span>British Pound</span></div></li>
              <li class="small_menu_subitems last_submenu_item"  onclick="set_value('currency','CNY')"><div>CNY - <span>Chinese Yuan</span></div></li> 
           </ul>
         </div>
    </li>
    <li class="small_menu_items">
      <div id="small_language_div" onclick="toggle_small_lan_menu()">
         <span id="small_language_val">EN</span><i id="small_lan_arrow" class="arrow_down"></i>
      </div>
      <div id="small_language_menu" class="small_menu_divs">
           <ul>
              <li  class="small_menu_subitems first_submenu_item" onclick="set_value('#language','EN')"><div id="EN_div"><span>EN</span></div></li>
              <li class="small_menu_subitems" onclick="set_value('language','FR')"><div id="FR_div">FR</div></li>
              <li class="small_menu_subitems" onclick="set_value('language','ES')"><div id="ES_div">ES</div></li>
              <li class="small_menu_subitems   last_submenu_item" onclick="set_value('language','ZH')"><div id="ZH_div">ZH</div></li>
           </ul>
      </div>
    </li>
   </ul> 
</div> <!-- End of small_menu div -->
</body>
</html>