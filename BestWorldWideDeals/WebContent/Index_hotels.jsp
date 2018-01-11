<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	  
<fmt:requestEncoding value="UTF-8" />
<html>
<c:set var="loc" value="en_GB"/>
<c:if test="${!(empty param.locale)}">
   <c:set var="loc" value="${param.locale}"/>
</c:if>
<fmt:setLocale value="${loc}"/>

<head>
<fmt:bundle basename="app">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="./Style/Index_hotels.css">
<link rel="stylesheet" type="text/css" href="./Style/datepicker.css">
<script defer src="${pageContext.request.contextPath}/JavaScript/utils/datepicker.js"></script>
<title>Insert title here</title>
</head>
<body>
    <div id="header_div">
       <jsp:include page="./Jsp/Header.jsp"/>
    </div>   
   	<div id="content_wrapper">
   	   <div id="top_content">
   	       <div id="search_div">
		       <div id="search_tab">
					<div id="search_label" class="search_labels"><fmt:message key="travelingTo"/></div>
						<input type="text" id="search_input" class="search_text_inputs" />
					
					       <div class="date_room_divs date_divs">  
					           <div id="check_in_label" class="search_labels">
					                <fmt:message key="checkIn"/>
					           </div>
					             
					           <div id="from_date_input"  class="search_text_inputs date_inputs" >
					                <div id="from_date_val" class="date_vals" onclick="showCalendar('from_date_input','regular');">hello world</div>
					                <div id="small_from_date_val" class="date_vals" onclick="showCalendar('from_date_input','small');">hello world</div>
					                <i class="index_arrow_down"></i>
					           </div>
					            <i id="num_nights"><!-- span>&#10003</span> -->  1 Night Stay</i>
					   					           
					           <div id="from_calendar" class="date_callout date_border_callout" >
					           <b class="date_border_notch date_notch"></b><b class="date_notch"></b>
					           </div>
					        </div>
					        
					        
					           
						    <div class="date_room_divs date_divs to_date_div">
						       <div id="check_out_label" class="search_labels">
						            <fmt:message key="checkOut"/>
						       </div>
						       <div id="to_date_input" class="search_text_inputs date_inputs" >
						             <div id="to_date_val" class="date_vals" onclick="showCalendar('to_date_input','regular');">hello world</div>
						             <div id="small_to_date_val" class="date_vals" onclick="showCalendar('to_date_input','small');">hello world</div>
						             <i class="index_arrow_down"></i>
						       </div>
						       <div id="to_calendar" class="date_callout date_border_callout" >
					           <b class="date_border_notch date_notch"></b><b class="date_notch"></b>
					           </div>
					           
					         </div>
					         
					         <div id="small_from_calendar" class="small_date_callout small_date_border_callout" >
					           <b class="small_date_border_notch small_date_notch"></b><b class="small_date_notch"></b>
					        </div>
					         <div id="small_to_calendar" class="small_date_callout small_date_border_callout" >
					           <b class="small_date_border_notch small_date_notch"></b><b class="small_date_notch"></b>
					           </div>
					           
						     <div id="room_type_div" class="date_room_divs">
						       <div id="room_type_label" class="search_labels">
						            <fmt:message key="roomType"/>
						       </div>
						       <div class="search_text_inputs" id="room_selector" onclick="" onchange="">
						       </div>
						     </div>  
						 
                </div>
      	 </div> <!-- End of search div -->
      </div><!-- end of top_content div-->	 
	</div> <!-- End of content wrapper -->
	<jsp:include page="./Jsp/Footer.jsp"></jsp:include>

</body>
</fmt:bundle>

</html>
