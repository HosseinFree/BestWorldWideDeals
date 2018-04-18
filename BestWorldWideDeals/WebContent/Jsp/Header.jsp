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
<fmt:bundle basename="app">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/Header.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Header.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/Images/welcome/logo.ico"/>
<title>BestWorldWideDeals</title>
</head>
<body>

<div id="header-wrapper">
  <div id="menu_wrapper">
   <a href="${pageContext.request.contextPath}/Index_hotels.jsp">
      <div id="logo-div">
      </div>
   </a>
		<div id="menu">
			<div id="small_menu_icon" class="small_menu_norm"
				onclick="small_menu_view_switch(this);toggle_small_menu()">
				<div class="bar1"></div>
				<div class="bar2"></div>
				<div class="bar3"></div>
			</div>

			<div id="large-menu">
				<ul>
										
					<c:url value="${pageContext.request.requestURL}" var="frURL">
					   <c:param name="locale" value="fr_FR"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="enURL">
					   <c:param name="locale" value="en_GB"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="esURL">
					   <c:param name="locale" value="es_ES"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="zhURL">
					   <c:param name="locale" value="zh_CH"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="itURL">
					   <c:param name="locale" value="it_IT"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="deURL">
					   <c:param name="locale" value="de_DE"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="jpURL">
					   <c:param name="locale" value="ja_JP"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="ruURL">
					   <c:param name="locale" value="ru_RU"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="koURL">
					   <c:param name="locale" value="ko_KR"/>
					</c:url>
					
					<c:url value="${pageContext.request.requestURL}" var="ptURL">
					   <c:param name="locale" value="pt_PT"/>
					</c:url>
					
					<li class="large_menu_items">
					    <div id="account_div" onclick="toggle_acc_menu();"><fmt:message key="myAccount"/></div>
						<i id="acc_arrow" class="arrow_down"></i>
					</li>
					<div id="account_menu" class="callout border_callout">
						<b class="border_notch notch"></b><b class="notch"></b>
						<ul>
							<a href="#"><li class="acc_men_items" id="acc_men_first_item"><div><fmt:message key="logIn"/></div></li></a><br/>
							<a href="#"><li class="acc_men_items"><div><fmt:message key="signUp"/></div></li></a>
						</ul>
					</div>
					
					
									    			    
					<li class="large_menu_items">
					   <div id="language_div"  onclick="toggle_lan_menu();"><fmt:message key="language"/></div>
					   <i id="lan_arrow" class="arrow_down"></i>
				    </li>
					
					<li class="large_menu_items">
					   <div id="earth_map_logo"  onclick="toggle_lan_menu();"></div>
				    </li>
				    
					
					<div id="language_menu" class="lan_callout lan_border_callout">
						<b class="lan_border_notch lan_notch"></b><b class="lan_notch"></b>
						<div class="language_menu_divs">
							<ul>
								<li class="lan_men_items" ><a href="${enURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/EN_img.png');"></div><div>English</div></a></li><br/>
								<li class="lan_men_items" ><a href="${frURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/FR_img.png');"></div><div>Français</div></a></li><br/>
								<li class="lan_men_items" ><a href="${esURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/ES_img.png');"></div><div>Español</div></a></li><br/>
								<li class="lan_men_items" ><a href="${itURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/IT_img.png');"></div><div>Italiano</div></a></li><br/>
								<li class="lan_men_items" ><a href="${koURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/KO_img.png');"></div><div>한국어</div></a></li><br/>
						    </ul>
					    </div>
					    <div class="language_menu_divs">
							<ul>
							    <li class="lan_men_items" ><a href="${deURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/DE_img.png');"></div><div>Deutsch</div></a></li><br/>
								<li class="lan_men_items" ><a href="${jpURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/JP_img.png');"></div><div>日本語</div></a></li><br/>
								<li class="lan_men_items" ><a href="${ruURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/RU_img.png');"></div><div>Pусский</div></a></li><br/>
								<li class="lan_men_items"><a href="${zhURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/ZH_img.png');"></div><div>简体中文 </div></a></li>
								<li class="lan_men_items"><a href="${ptURL}"><div class="lan_men_items_flag_icons" style="background-image: url('${pageContext.request.contextPath}/Images/Header/PT_img.png');"></div><div>Português</div></a></li>
							</ul>
						</div>	
					</div>
					
					<li class="large_menu_items"><div id="currency_div" onclick="toggle_cur_menu();">USD</div><i id="cur_arrow" class="arrow_down"></i></li>
					<li class="large_menu_items">
					   <div id="currency_map_logo"></div>
				    </li>
				    
					<div id="currency_menu" class="cur_callout cur_border_callout">
						<b class="cur_border_notch cur_notch"></b><b class="cur_notch"></b>
												
						<div class="currency_menu_divs">
							<ul>
								<li class="cur_men_items" onclick="set_value('currency','CHF')"><div>CHF - <span><fmt:message key="CHF"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','COP')"><div>COP - <span><fmt:message key="COP"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','CLP')"><div>CLP - <span><fmt:message key="CLP"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','HDK')"><div>HDK - <span><fmt:message key="HDK"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','IDR')"><div>IDR - <span><fmt:message key="IDR"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','PEN')"><div>PEN - <span><fmt:message key="PEN"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','INR')"><div>INR - <span><fmt:message key="INR"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','JPY')"><div>JPY - <span><fmt:message key="JPY"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','KRW')"><div>KRW - <span><fmt:message key="KRW"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','MYR')"><div>MYR - <span><fmt:message key="MYR"/></span></div></li>	
							  </ul>
					    </div> 
						
						<div class="currency_menu_divs">
							<ul>
								<li class="cur_men_items" onclick="set_value('currency','PHP')"><div>PHP - <span><fmt:message key="PHP"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','SGD')"><div>SGD - <span><fmt:message key="SGD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','THB')"><div>THB - <span><fmt:message key="THB"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','TWD')"><div>TWD - <span><fmt:message key="TWD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','UYU')"><div>UYU - <span><fmt:message key="UYU"/></span></div></li><br />
								<li class="cur_men_items" onclick="set_value('currency','VND')"><div>VND - <span><fmt:message key="VND"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','MXN')"><div>MXN - <span><fmt:message key="MXN"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','CZK')"><div>CZK - <span><fmt:message key="CZK"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','DKK')"><div>DKK - <span><fmt:message key="DKK"/></span></div></li><br />
								<li class="cur_men_items" onclick="set_value('currency','TRY')"><div>TRY - <span><fmt:message key="TRY"/></span></div></li>
							</ul>
						</div>
						
						<div class="currency_menu_divs">
						    <ul>
								<li class="cur_men_items" onclick="set_value('currency','CAD')"><div>CAD - <span><fmt:message key="CAD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','USD')"><div>USD - <span><fmt:message key="USD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','EUR')"><div>EUR - <span><fmt:message key="EUR"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','AUD')"><div>AUD - <span><fmt:message key="AUD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','GBP')"><div>GBP - <span><fmt:message key="GBP"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','CNY')"><div>CNY - <span><fmt:message key="CNY"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','RUB')"><div>RUB - <span><fmt:message key="RUB"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','NZD')"><div>NZD - <span><fmt:message key="NZD"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','ARS')"><div>ARS - <span><fmt:message key="ARS"/></span></div></li><br />
								<li class="cur_men_items" onclick="set_value('currency','BRL')"><div>BRL - <span><fmt:message key="BRL"/></span></div></li><br/>
								<li class="cur_men_items" onclick="set_value('currency','SEK')"><div>SEK - <span><fmt:message key="SEK"/></span></div></li>
							</ul>
						</div> 
						
					</div>
					
					
				</ul>
			</div> <!-- End of large-menu -->
		</div> <!--  End of menu div -->
	
		<div id="cover_screen">
		</div>
		<div id="small_menu">
		  <ul id="first_small_ul">
		    <li class="small_menu_items">
		      <div id="small_account_div"  onclick="toggle_small_acc_menu()">
		          <fmt:message key="myAccount"/><i id="small_acc_arrow" class="arrow_down"></i>
		      </div>
		      <div id="small_account_menu" class="small_menu_divs">
		           <ul>
		              <a href="#"><li id="small_acc_men_first_item" class="small_menu_subitems first_submenu_item"><div><fmt:message key="logIn"/></div></li></a>
		              <a href="#"><li class="small_menu_subitems last_submenu_item"><div><fmt:message key="signUp"/></div></li></a>              
		            </ul>
		      </div> 
		    </li>
		    <li class="small_menu_items">
		      <div id="small_currency_div" onclick="toggle_small_cur_menu()">
		         <span id="small_currency_val">USD</span><i id="small_cur_arrow" class="arrow_down"></i>
		      </div>
		      <div id="small_currency_menu" class="small_menu_divs">
		           <ul class="small_currency_menu_items">
		              <li class="small_menu_subitems first_submenu_item" onclick="set_value('#small_currency_val','CAD')"><div>CAD - <span><fmt:message key="CAD"/></span></div></li>
		              <li class="small_menu_subitems" onclick="set_value('currency','USD')"><div>USD - <span><fmt:message key="USD"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','EUR')"><div>EUR - <span><fmt:message key="EUR"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','AUD')"><div>AUD - <span><fmt:message key="AUD"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','GBP')"><div>GBP - <span><fmt:message key="GBP"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','CNY')"><div>CNY - <span><fmt:message key="CNY"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','RUB')"><div>RUB - <span><fmt:message key="RUB"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','NZD')"><div>NZD - <span><fmt:message key="NZD"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','ARS')"><div>ARS - <span><fmt:message key="ARS"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','BRL')"><div>BRL - <span><fmt:message key="BRL"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','CHF')"><div>CHF - <span><fmt:message key="CHF"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','COP')"><div>COP - <span><fmt:message key="COP"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','CLP')"><div>CLP - <span><fmt:message key="CLP"/></span></div></li>
				   	  <li class="small_menu_subitems" onclick="set_value('currency','HDK')"><div>HDK - <span><fmt:message key="HDK"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','IDR')"><div>IDR - <span><fmt:message key="IDR"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','PEN')"><div>PEN - <span><fmt:message key="PEN"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','INR')"><div>INR - <span><fmt:message key="INR"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','JPY')"><div>JPY - <span><fmt:message key="JPY"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','KRW')"><div>KRW - <span><fmt:message key="KRW"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','MYR')"><div>MYR - <span><fmt:message key="MYR"/></span></div></li>	
					  <li class="small_menu_subitems" onclick="set_value('currency','PHP')"><div>PHP - <span><fmt:message key="PHP"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','SGD')"><div>SGD - <span><fmt:message key="SGD"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','THB')"><div>THB - <span><fmt:message key="THB"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','TWD')"><div>TWD - <span><fmt:message key="TWD"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','UYU')"><div>UYU - <span><fmt:message key="UYU"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','VND')"><div>VND - <span><fmt:message key="VND"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','MXN')"><div>MXN - <span><fmt:message key="MXN"/></span></div></li>
				      <li class="small_menu_subitems" onclick="set_value('currency','CZK')"><div>CZK - <span><fmt:message key="CZK"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','DKK')"><div>DKK - <span><fmt:message key="DKK"/></span></div></li>
					  <li class="small_menu_subitems" onclick="set_value('currency','TRY')"><div>TRY - <span><fmt:message key="TRY"/></span></div></li>
					  <li class="small_menu_subitems last_submenu_item" onclick="set_value('currency','SEK')"><div>SEK - <span><fmt:message key="SEK"/></span></div></li>              
		           </ul>
		         </div>
		    </li>
		    <li class="small_menu_items">
		      <div id="small_language_div" onclick="toggle_small_lan_menu()">
		         <span id="small_language_val"><fmt:message key="language"/></span><i id="small_lan_arrow" class="arrow_down"></i>
		      </div>
		      <div id="small_language_menu" class="small_menu_divs">
		           <ul>
		              <li  class="small_menu_subitems first_submenu_item" onclick="set_value('#language','EN')"><a href="${enURL}"><div id="EN_div"><span>English</span></div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','FR')"><a href="${frURL}"><div id="FR_div">Français</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','ES')"><a href="${esURL}"><div id="ES_div">Español</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','IT')"><a href="${itURL}"><div id="IT_div">Italiano</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','DE')"><a href="${deURL}"><div id="DE_div">Deutsch</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','JP')"><a href="${jpURL}"><div id="JP_div">日本語</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','RU')"><a href="${ruURL}"><div id="RU_div">Pусский</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','KO')"><a href="${koURL}"><div id="KO_div">한국어</div></a></li>
		              <li class="small_menu_subitems" onclick="set_value('language','PT')"><a href="${ptURL}"><div id="PT_div">Português</div></a></li>
		              <li class="small_menu_subitems   last_submenu_item" onclick="set_value('language','ZH')"><a href="${zhURL}"><div id="ZH_div"> 简体中文</div></a></li>
		           </ul>
		      </div>
		    </li>
		   </ul> 
		</div> <!-- End of small_menu div -->
   </div> <!-- End of  menu-wrapper-->
</div><!-- End of  header-wrapper-->

</body>
</fmt:bundle>

</html>