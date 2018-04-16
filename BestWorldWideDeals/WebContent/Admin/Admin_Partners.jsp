<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/Admin/Admin_Partners.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script defer src="${pageContext.request.contextPath}/JavaScript/Admin/Admin_Partners.js"></script>
</head>
<body>
   <div id="partners_menu_wrapper">
      <ul id="partners_menu">
         <li id="partners_menu_add_item" onclick="toggle_divs(this)">
	         <div class="partners_menu_items">
	            Add Partner
	         </div>
         </li>
         <li id="partners_menu_list_item" onclick="toggle_divs(this);">
             <div class="partners_menu_items" >
	            List Partners
	         </div>
         </li>
         <li id="partners_menu_edit_item" onclick="toggle_divs(this);">
             <div class="partners_menu_items">
	            Edit Partner
	         </div>
         </li>
        </ul>
   </div>
   <div id="partners_main_content">
		      <div class="partners_add_div">
		              <div class="partners_add_div_items" id="partners_add_title"> Partner Info </div>
			          <div class="partners_add_div_items"> 
			              <p>Partner Name</p>
			              <input type="text" id="partner_name" class="partners_add_form_inps"/>
			          </div>
			          <div class="partners_add_div_items"> 
			             <p>Partner Category</p>
			             <select id="partners_category" class="partners_add_form_inps  partners_add_form_select">
			                 <optgroup label="Accommodation">
			                    <option value="Accommodation_Hotel" selected>Hotel</option>
			                    <option value="Accommodation_Vacation-Rental">Vacation Rental</option>
			                    <option value="Accommodation_Hostel">Hostel</option>
			                    <option value="Accommodation_Multi">Multi</option>
			                 </optgroup>  
			             </select>
			        </div>
			        
			        <div class="partners_add_div_items"> 
			             <p>Data Format</p>
				         <select id="partners_data_format" class="partners_add_form_inps  partners_add_form_select">
				                 <option value="Json" selected>Json</option>
				                 <option value="Xml">Xml</option>
				         </select>  
				    </div>
				    
				    <div class="partners_add_div_items"> 
			             <p>Supported Languages</p>
			             <select id="partners_languages_select" class="partners_add_form_inps  partners_add_form_select" onchange="add_language('add')">
			                <option value="none">Choose a language</option>
					        <option value="EN">EN - English</option> <option value="FR">FR - French</option> <option value="ES">ES - Spanish</option>
						    <option value="ZH">ZH - Chinese</option> <option value="IT">IT - Italian</option> <option value="RU">RU - Russian</option>
						    <option value="DE">DE - German</option><option value="JP">JP - Japanese</option>
			   	         </select>
				         <div id="partners_langs_div"></div>
				    </div>
				    
				    <div class="partners_add_div_items"> 
						             <p>Supported Currencies</p>
						             <select id="partners_currencies_select" class="partners_add_form_inps  partners_add_form_select" onchange="add_currency('add')">
						                <option value="none">Choose a currency</option>
								        <option value="CAD">Canadian Dollar - CAD</option><option value="CNY">Chinese Yuan - CNY</option><option value="EUR">Euro - EUR</option>
									    <option value="USD">US Dollar - USD</option><option value="GBP">British Pound	- GBP</option><option value="RUB">Russian Ruble - RUB</option>
									    <option value="AUD">Australian Dollar - AUD</option><option value="NZD">New Zealand Dollar - NZD</option><option value="ARS">Argentine Peso - ARS</option>
									    <option value="BRL">Brazilian Real - BRL</option><option value="CHF">Swiss Franc - CHF</option><option value="COP">Colombian Peso - COP</option>
									    <option value="CLP">Chilean Peso - CLP</option> <option value="HDK">Hong Kong Dollar - HDK</option> <option value="IDR">Indonesian Rupiah - IDR</option> 
									    <option value="INR">Indian Rupee - INR</option> <option value="JPY">Japanese Yen - JPY</option> <option value="KRW">South Korean Won - KRW</option> 
									    <option value="MYR">Malaysian Ringgit - MYR</option><option value="PEN">Peruvian Sol - PEN</option><option value="PHP">Philippine Piso - PHP</option>
									    <option value="SGD">Singapore Dollar - SGD</option><option value="THB">Thai Baht - THB</option><option value="TWD">New Taiwan Dollar - TWD</option>
									    <option value="UYU">Uruguayan peso - UYU</option><option value="VND">Vietnamese Dong - VND</option><option value="MXM">Mexican Peso - MXN</option>
									    <option value="CZK">Czech koruna - CZK</option><option value="SEK">Swedish krona - SEK</option><option value="DKK">Danish Krone-DKK</option>
									    <option value="TRY">Turkish Lira - TRY</option>
								     </select>
							         <div id="partners_currens_div"></div>
				    </div>
				    
			        <div class="partners_add_div_items"> 
			              <input type="checkbox" id="partners_add_key_check" class="partners_add_checks" onchange="toggle_input(this,'partner_api_key')"><span>Api Key</span>
			              <input type="text" id="partner_api_key" class="partners_add_form_inps" Placeholder="Please enter the key"/>
			        </div>
			         
			        <div class="partners_add_div_items"> 
			              <input type="checkbox" id="partners_add_secret_check" class="partners_add_checks" onchange="toggle_input(this,'partner_api_secret')"><span>Api Secret</span>
			              <input type="text" id="partner_api_secret" class="partners_add_form_inps" Placeholder="Please enter the secret"/>
			        </div>
			         
			        <div class="partners_add_div_items"> 
			              <input type="checkbox" id="partners_add_token_check" class="partners_add_checks" onchange="toggle_input(this,'partner_api_token_url')"><span>Needs Authentication Token</span>
			              <input type="text" id="partner_api_token_url" class="partners_add_form_inps" Placeholder="Please enter the url to obtain token from"/>
			        </div>
			         
			        <div id="partners_add_urls"></div>
			          
			        <div class="partners_add_div_items partner_form_url_buttons_div"> 
			             <div id="partner_form_addurl_button" class="partner_form_url_buttons" onclick="add_url('add',null)"> Add Url </div>
			        </div>
			         
			        <div id="partners_add_form_err"> 
			              Please check all the required fields are filled.
			        </div>
			         
			        <div class="partners_add_div_items"> 
			              <div id="partner_form_button" onclick="store_data()"> Submit </div>
			        </div>
		      </div> <!-- End of add partner -->
		      
		      <!-- ################################################################# -->
		      
		      <div id="list_partners_divs">
				    <div class="partners_add_div_items"> 
					  <p>Partner Category</p>
					  <select id="partners_list_category" class="partners_add_form_inps  partners_add_form_select">
					      <option value="All_Partners" selected>All Partners</option>
						  <optgroup label="Accommodation">
						    <option value="All_Accommodation">All Accommodation</option>
						    <option value="Accommodation_Hotel">Hotel</option>
						    <option value="Accommodation_Vacation-Rental">Vacation Rental</option>
						    <option value="Accommodation_Hostel">Hostel</option>
						    <option value="Accommodation_Multi">Multi</option>
						  </optgroup>  
				       </select>
				    </div>   
				    
				    <div class="partners_add_div_items"> 
			              <div id="partner_list_form_button" onclick="list_partners()"> Search </div>
			        </div>
		     </div>  <!-- End of list_partners_divs -->
		     
		     <!-- ################################################################# -->
		 
		      <div id="edit_partner_div">
		           <div id="partner_edit_list_div"> 
				      <div class="partners_add_div_items"> 
					    <p>List of Partners</p>
					    <select id="existing_partners_list" class="partners_add_form_inps  partners_add_form_select" onchange="create_partner_edit_div();">
					    </select>
				      </div> 
				    </div>
				    <div id="partner_info_edit_div">
						<div class="partners_add_div_items" id="partners_add_title"> Partner Info </div>
						<div class="partners_add_div_items"> 
						    <p>Partner Name</p>
						       <input type="text" id="partner_edit_name" disabled class="partners_add_form_inps"/>
						</div>
						
						<div class="partners_add_div_items"> 
						             <p>Partner Category</p>
						             <select id="partners_edit_category" class="partners_add_form_inps  partners_add_form_select">
						                 <optgroup label="Accommodation">
						                    <option value="Accommodation_Hotel" >Hotel</option>
						                    <option value="Accommodation_Vacation-Rental" selected>Vacation Rental</option>
						                    <option value="Accommodation_Hostel">Hostel</option>
						                    <option value="Accommodation_Multi">Multi</option>
						                 </optgroup>  
						             </select>
						        </div>
						        
						        <div class="partners_add_div_items"> 
						             <p>Data Format</p>
							         <select id="partners_edit_data_format" class="partners_add_form_inps  partners_add_form_select">
							                 <option value="Json">Json</option>
							                 <option value="Xml" selected>Xml</option>
							         </select>  
							    </div>
							    
							    <div class="partners_add_div_items"> 
						             <p>Supported Languages</p>
						             <select id="partners_edit_languages_select" class="partners_add_form_inps  partners_add_form_select" onchange="add_language('edit')">
						                <option value="none">Choose a language</option>
								        <option value="EN">EN - English</option><option value="FR">FR - French</option><option value="ES">ES - Spanish</option>
									    <option value="ZH">ZH - Chinese</option><option value="IT">IT - Italian</option><option value="RU">RU - Russian</option>
									    <option value="DE">DE - German</option><option value="JP">JP - Japanese</option>
						   	         </select>
							         <div id="partners_edit_langs_div"></div>
							    </div>
                                <div class="partners_add_div_items"> 
						             <p>Supported Currencies</p>
						             <select id="partners_edit_currencies_select" class="partners_add_form_inps  partners_add_form_select" onchange="add_currency('edit')">
						                <option value="none">Choose a currency</option>
								        <option value="CAD">Canadian Dollar - CAD</option><option value="CNY">Chinese Yuan - CNY</option><option value="EUR">Euro - EUR</option>
									    <option value="USD">US Dollar - USD</option><option value="GBP">British Pound	- GBP</option><option value="RUB">Russian Ruble - RUB</option>
									    <option value="AUD">Australian Dollar - AUD</option><option value="NZD">New Zealand Dollar - NZD</option><option value="ARS">Argentine Peso - ARS</option>
									    <option value="BRL">Brazilian Real - BRL</option><option value="CHF">Swiss Franc - CHF</option><option value="COP">Colombian Peso - COP</option>
									    <option value="CLP">Chilean Peso - CLP</option> <option value="HDK">Hong Kong Dollar - HDK</option> <option value="IDR">Indonesian Rupiah - IDR</option> 
									    <option value="INR">Indian Rupee - INR</option> <option value="JPY">Japanese Yen - JPY</option> <option value="KRW">South Korean Won - KRW</option> 
									    <option value="MYR">Malaysian Ringgit - MYR</option><option value="PEN">Peruvian Sol - PEN</option><option value="PHP">Philippine Piso - PHP</option>
									    <option value="SGD">Singapore Dollar - SGD</option><option value="THB">Thai Baht - THB</option><option value="TWD">New Taiwan Dollar - TWD</option>
									    <option value="UYU">Uruguayan peso - UYU</option><option value="VND">Vietnamese Dong - VND</option><option value="MXM">Mexican Peso - MXN</option>
									    <option value="CZK">Czech koruna - CZK</option><option value="SEK">Swedish krona - SEK</option><option value="DKK">Danish Krone-DKK</option>
									    <option value="TRY">Turkish Lira - TRY</option>
						   	         </select>
							         <div id="partners_edit_currens_div"></div>
				                </div>
                                                              							    
						        <div class="partners_add_div_items"> 
						              <input type="checkbox" id="partners_edit_key_check" class="partners_add_checks" onchange="toggle_input(this,'partner_edit_api_key')"><span>Api Key</span>
						              <input type="text" id="partner_edit_api_key" class="partners_add_form_inps" Placeholder="Please enter the key"/>
						        </div>
						         
						        <div class="partners_add_div_items"> 
						              <input type="checkbox" id="partners_edit_secret_check" class="partners_add_checks" onchange="toggle_input(this,'partner_edit_api_secret')"><span>Api Secret</span>
						              <input type="text" id="partner_edit_api_secret" class="partners_add_form_inps" Placeholder="Please enter the secret"/>
						        </div>
						         
						        <div class="partners_add_div_items"> 
						              <input type="checkbox" id="partners_edit_token_check" class="partners_add_checks" onchange="toggle_input(this,'partner_edit_api_token_url')"><span>Needs Authentication Token</span>
						              <input type="text" id="partner_edit_api_token_url" class="partners_add_form_inps" Placeholder="Please enter the url to obtain token from"/>
						        </div>
						         
						        <div id="partners_edit_urls"></div>
						          
						        <div class="partners_add_div_items partner_form_url_buttons_div"> 
						             <div id="partner_form_editaddurl_button" class="partner_form_url_buttons" onclick="add_url('edit',null)"> Add Url </div>
						        </div>
						         
						        <div id="partners_edit_form_err"> 
						              Please check all the required fields are filled.
						        </div>
						         
						        <div class="partners_add_div_items"> 
						              <div id="partner_edit_save_button" class="partner_edit_buttons" onclick="edit_partner('edit')"> Save </div>
						              <div id="partner_edit_remove_button" class="partner_edit_buttons" onclick="edit_partner('remove')"> Remove </div>
						        </div>
				    </div>  
		     </div>  <!-- End of edit_partner_div -->
		 
   </div> <!-- End of partners_main_content -->

</body>
</html>   