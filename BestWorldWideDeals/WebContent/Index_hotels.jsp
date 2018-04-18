<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>  
<fmt:requestEncoding value="UTF-8" />
<html>
<c:set var="loc" value="en_GB"/>
<c:set var="lan_val" value="EN"/>
<c:if test="${!(empty param.locale)}">
   <c:set var="loc" value="${param.locale}"/>
</c:if>

<fmt:setLocale value="${loc}"/>

<head>
<fmt:bundle basename="app">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="./Style/Index_hotels/Index_hotels.css">
<link rel="stylesheet" type="text/css" href="./Style/Index_hotels/Index_hotels_datepicker.css">
<script defer src="${pageContext.request.contextPath}/JavaScript/index_hotels/datepicker.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/index_hotels/google_place.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/index_hotels/Index_hotels.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/index_hotels/room_type.js"></script>

</head>
<body>
    <div id="header_div">
       <jsp:include page="./Jsp/Header.jsp"/>
    </div>   
    <input type="hidden" value="${loc}" id="language_val"/>
   	<div id="content_wrapper">
   	   <input type="hidden" id="hidd_content"/>
   	   
   	   <!--  Values of search form to be stored -->
   	   <input type="hidden" id="destination_coord"/>
   	   <input type="hidden" id="room_type_inp"/>
   	   <input type="hidden" id="from_date_inp"/>
   	   <input type="hidden" id="to_date_inp"/>
   	   <input type="hidden" id="num_of_rooms"/>
   	   
   	   
   	   <!-- ###############################  Start Top content ################################################## -->
   	   <div id="top_content">
   	       <div id="search_div">
		       <div id="search_tab">
					<div id="search_label" class="search_labels"><fmt:message key="travelingTo"/></div>
						<input type="text" id="search_input" class="search_text_inputs" onkeyup="initAutocomplete()" placeholder="<fmt:message key="travelSearchInput"/>"/>
					      <div id="date_room_wrapper">
						       <div class="date_room_divs date_divs">  
						           <div id="check_in_label" class="search_labels">
						                <fmt:message key="checkIn"/>
						           </div>
						             
						           <div id="from_date_input"  class="search_text_inputs date_inputs" >
						                <div id="from_date_val" class="date_vals" onclick="showCalendar('from_date_input','regular');"></div>
						                <div id="small_from_date_val" class="date_vals" onclick="showCalendar('from_date_input','small');"></div>
						                <i class="index_arrow_down"></i>
						           </div>
						            <i id="num_nights"><!-- span>&#10003</span> -->  1 <fmt:message key="nightStay"/></i>
						   					           
						           <div id="from_calendar" class="date_callout date_border_callout " >
						           <b class="date_border_notch date_notch  from_date_border_notch from_date_notch"></b><b class="date_notch from_date_notch"></b>
						           </div>
						        </div>
						       			           
							    <div class="date_room_divs date_divs to_date_div">
							       <div id="check_out_label" class="search_labels">
							            <fmt:message key="checkOut"/>
							       </div>
							       <div id="to_date_input" class="search_text_inputs date_inputs" >
							             <div id="to_date_val" class="date_vals" onclick="showCalendar('to_date_input','regular');"></div>
							             <div id="small_to_date_val" class="date_vals" onclick="showCalendar('to_date_input','small');"></div>
							             <i class="index_arrow_down"></i>
							       </div>
							       <div id="to_calendar" class="date_callout date_border_callout" >
						           <b class="to_date_border_notch to_date_notch date_border_notch date_notch"></b><b class="to_date_notch date_notch"></b>
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
							       <div class="search_text_inputs" id="room_selector" onclick="toggle_room_type_menu();">
							                <div id="room_type_val"><fmt:message key="doubleRoom"/></div>
							                 <i class="index_arrow_down"></i>
							       </div>
							 <div id="room_selector_menu" onclick=""  class="room_callout room_border_callout">
						         <b class="room_border_notch room_notch"></b><b class="room_notch"></b>
						        					         				         
						         <div id="single_div" class="room_type_menu_divs" onclick="replace_room_selector_val('./Images/Index_hotels/single.png',this.firstChild.innerHTML);deactivate_all_multiple_rooms();update_room_type_val('1')"><i><fmt:message key="signleRoom"/></i></div>
						         <div id="double_div" class="room_type_menu_divs" onclick="replace_room_selector_val('./Images/Index_hotels/double.png',this.firstChild.innerHTML);deactivate_all_multiple_rooms();update_room_type_val('2')"><i><fmt:message key="doubleRoom"/></i></div>
						         <div id="family_div" class="room_type_menu_divs" onclick="activate_multiple_room('family','3')"><i><fmt:message key="familyRoom"/></i></div>
						         <div id="multiple_div" class="room_type_menu_divs" onclick="activate_multiple_room('multiple','4')"><i><fmt:message key="multipleRoom"/></i></div>
						    </div>
							 
						    </div> <!-- ######################################## -->
						    
						    <div id="rooms_info_menu" class="room_info_callout room_info_border_callout">
						          <b class="room_info_border_notch room_info_notch"></b><b class="room_info_notch"></b>
						          <div id="rooms_info">
								          <div class="rooms_info_divs" id="room1_info">
										     <table>
										        <tr>
										              <td class="room_num_search_label"><fmt:message key="room"/> 1</td>
										        </tr>
										        <tr>
										           <td>
										              <div class="rooms_info_inner_divs adult_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="adults"/></div>
											              <select id="room1_adult" class="counters_div"> 
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div class="rooms_info_inner_divs children_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="children"/></div>
											              <select id="room1_children" class="counters_div" onchange="handle_change_child_num(this)">
											                <option selected>0</option>  
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div id="room1_children_age_div" class="rooms_info_inner_divs children_age_div">
										                      <div class="room_search_labels children_age_labels" id="room1_children_age_label"><fmt:message key="childrenAge"/></div>
				                                              <select id="room1_child1" class="counters_div child_ages" > 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option><option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>
															  <select id="room1_child2" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>		
															  <select id="room1_child3" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>			
															  <select id="room1_child4" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>							              
										              </div>
										              
										            </td>
										        </tr>      						     
										     </table>
										  </div><!--  End of room1_info -->
										  <div class="rooms_info_divs" id="room2_info">
										     <table>
										        <tr>
										              <td class="room_num_search_label"><fmt:message key="room"/> 2</td>
										        </tr>
										        <tr>
										           <td>
										              <div class="rooms_info_inner_divs adult_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="adults"/></div>
											              <select id="room2_adult" class="counters_div"> 
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div class="rooms_info_inner_divs children_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="children"/></div>
											              <select id="room2_children" class="counters_div" onchange="handle_change_child_num(this)">
											                <option selected>0</option>  
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div id="room2_children_age_div" class="rooms_info_inner_divs children_age_div">
										                      <div class="room_search_labels children_age_labels" id="room2_children_age_label"><fmt:message key="childrenAge"/></div>
				                                              <select id="room2_child1" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option><option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>
															  <select id="room2_child2" class="counters_div child_ages" > 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>		
															  <select id="room2_child3" class="counters_div child_ages" > 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>			
															  <select id="room2_child4" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>							              
										              </div>
										            </td>
										        </tr>      						     
										     </table>
								          </div> <!--  End of room2_info -->
										  <div class="rooms_info_divs" id="room3_info">
										     <table>
										        <tr>
										              <td class="room_num_search_label"><fmt:message key="room"/> 3</td>
										        </tr>
										        <tr>
										           <td>
										              <div class="rooms_info_inner_divs adult_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="adults"/></div>
											              <select id="room3_adult" class="counters_div"> 
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div class="rooms_info_inner_divs children_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="children"/></div>
											              <select id="room3_children" class="counters_div" onchange="handle_change_child_num(this)">
											                <option selected>0</option> 
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div id="room3_children_age_div" class="rooms_info_inner_divs children_age_div">
										                      <div class="room_search_labels children_age_labels" id="room3_children_age_label"><fmt:message key="childrenAge"/></div>
				                                              <select id="room3_child1" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option><option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>
															  <select id="room3_child2" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>		
															  <select id="room3_child3" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>			
															  <select id="room3_child4" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>							              
										              </div>
										            </td>
										        </tr>      						     
										     </table>
										  </div> <!--  End of room3_info -->
										  <div class="rooms_info_divs" id="room4_info">
										     <table>
										        <tr>
										              <td class="room_num_search_label"><fmt:message key="room"/> 4</td>
										        </tr>
										        <tr>
										           <td>
										              <div class="rooms_info_inner_divs adult_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="adults"/></div>
											              <select id="room4_adult" class="counters_div">
											                <option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div class="rooms_info_inner_divs children_div adult_children_div">
											              <div class="room_search_labels"><fmt:message key="children"/></div>
											              <select id="room4_children" class="counters_div" onchange="handle_change_child_num(this)">
											                <option selected>0</option> 
															<option>1</option>
														    <option>2</option>
															<option>3</option>
														    <option>4</option>
													      </select>
										              </div>
										              
										              <div id="room4_children_age_div" class="rooms_info_inner_divs children_age_div">
										                      <div class="room_search_labels children_age_labels" id="room4_children_age_label"><fmt:message key="childrenAge"/></div>
				                                              <select id="room4_child1" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option><option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>
															  <select id="room4_child2" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>		
															  <select id="room4_child3" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>			
															  <select id="room4_child4" class="counters_div child_ages"> 
															      <option selected>0</option><option>1</option><option>2</option><option>3</option>
															      <option>4</option> <option>5</option><option>6</option><option>7</option><option>8</option>
															      <option>9</option><option>10</option><option>11</option><option>12</option>
															      <option>13</option><option>14</option><option>15</option><option>16</option>
															      <option>17</option>
															  </select>							              
										              </div>
										            </td>
										        </tr>      						     
										     </table>
								          </div> <!--  End of room4_info -->  
										  <div id="add_rem_div">
										    <table>
										      <tr>
										        <td id="add_butt_td"> 
										    		<button id="add_butt" class="add_rem_butts" onclick="activate_next_room()">
										      			<fmt:message key="add"/>
										    		</button>
										    	</td>
										    	<td id="rem_butt_td" align="center">	
												    <button id="rem_butt" class="add_rem_butts" onclick="deactivate_last_room()">
												      <fmt:message key="remove"/>
												    </button>
												</td>
												<td>
												    <button id="family_confirm_butt" class="add_rem_butts confirm_butt">
										              <fmt:message key="confirm"/>
										            </button>
										            <button id="multiple_confirm_butt" class="add_rem_butts confirm_butt">
										              <fmt:message key="confirm"/>
										            </button>
										        </td>
										       </tr>
										    </table>
								          </div>
								          <div id="room_close" onclick="close_room_info()"> 
										        Close
										  </div>
										          
									</div> <!-- End of rooms_info -->
							</div>	<!-- End of rooms_info_menu -->
							</div>  
							
							<table id="test_table">
							  <tr>
							     <td>
							     </td>
							  </tr>
							</table>
							<button id="search_button" onclick="search_hotels()">
							   <fmt:message key="search"/>
							</button>
			  </div>	<!-- End of search_tab -->	
		 </div> <!-- End of search div -->
      </div><!-- end of top_content div-->
      <!-- ##################################### End Top content ############################################### -->	 
	</div> <!-- End of content wrapper -->
	<jsp:include page="./Jsp/Footer.jsp"></jsp:include>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZKLZfJQ-iqPcr4Cu5SWBSHRtaSWbS8pM&&language=${fn:split(loc,'_')[0]}&region=${fn:split(loc,'_')[1]}&libraries=places" async defer></script>
</body>
</fmt:bundle>

</html>
