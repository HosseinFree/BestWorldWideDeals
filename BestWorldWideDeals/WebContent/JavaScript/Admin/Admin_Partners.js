/**
 * 
 */
var is_add_open = true;
var is_list_open = false;
var is_edit_open = false;

var has_key = false;
var has_secret = false;
var has_token = false;
var num_urls = 0 ;
var last_added_url = 0;
var supported_languages = [];
var supported_currencies = [];

var edit_has_key = false;
var edit_has_secret = false;
var edit_has_token = false;
var edit_num_urls = 0;
var edit_last_added_url = 0;
var edit_supported_languages = [];
var edit_supported_currencies = [];
var is_edit_info_open = false;

var list_of_partners = null;

/**
 * 
 * 
 */
$(window).on('load', function(){
	$('#partners_add_key_check').prop('checked', false);
	$('#partners_add_secret_check').prop('checked', false);
	$('#partners_add_token_check').prop('checked', false);
	$('#partners_menu_add_item').toggleClass("partner_menu_active_item");
	$('#partners_menu_list_item').toggleClass("partner_menu_deactive_item");
	$('#partners_menu_edit_item').toggleClass("partner_menu_deactive_item");
	$('#partner_name').val("");
	$('#partners_category option:first').prop('selected',true);
	$('#partners_data_format option:first').prop('selected',true);
	$('#partners_languages_select option:first').prop('selected',true);
	$('#partners_currencies_select option:first').prop('selected',true);
});




/**
 * 
 * 
 */
function close_add_partner(){
	$('#partner_name').val("");
	$("#partners_langs_div").empty();
	$("#partners_currens_div").empty();
	supported_languages = [];
	supported_currencies = [];
	$('#partners_add_key_check').prop('checked', false);
	$('#partners_add_secret_check').prop('checked', false);
	$('#partners_add_token_check').prop('checked', false);
	$('#partner_api_key').hide();
	$('#partner_api_secret').hide();
	$('#partner_api_token_url').hide();
    $('.partners_add_div').hide();
    $("#partners_add_urls").empty().hide();
    $("#partner_form_remurl_button").hide();
    $("#partner_form_addurl_button").show();
    $('#partners_category option:first').prop('selected',true);
	$('#partners_data_format option:first').prop('selected',true);
	$('#partners_languages_select option:first').prop('selected',true);
	$('#partners_currencies_select option:first').prop('selected',true);
    num_urls = 0;
    last_added_url = 0;
    is_add_open = false;
    $('#partners_menu_add_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_add_item').toggleClass("partner_menu_deactive_item");
    $("#partners_add_form_err").hide();
}

/**
 * 
 * @returns
 */
function open_add_partner(){
	$('.partners_add_div').show();
    is_add_open = true;
    $('#partners_menu_add_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_add_item').toggleClass("partner_menu_deactive_item");
}


/**
 * 
 * 
 */
function close_list_partner(){
	var list_div = $("#partners_list_div")[0];
	if(list_div){
		$("#partners_list_div").empty();
		$("#partners_list_div").remove();
	}
	$('#partners_list_category option:first').prop('selected',true);
	$('#list_partners_divs').hide();
    is_list_open = false;
    $('#partners_menu_list_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_list_item').toggleClass("partner_menu_deactive_item");
}

/**
 * 
 * @returns
 */
function open_list_partner(){
	$('#list_partners_divs').show();
    is_list_open = true;
    $('#partners_menu_list_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_list_item').toggleClass("partner_menu_deactive_item");
}

/**
 * 
 * 
 */
function close_edit_partner(){
	refresh_edit_info_form();
	$('#edit_partner_div').hide();
	$('#partner_info_edit_div').hide();
    is_edit_open = false;
    $('#partners_menu_edit_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_edit_item').toggleClass("partner_menu_deactive_item");
}

/**
 * 
 * @returns
 */
function open_edit_partner(){
	creat_list_existing_partners();
	$('#edit_partner_div').show();
	$('#partner_edit_list_div').show();
	is_edit_open = true;
    $('#partners_menu_edit_item').toggleClass("partner_menu_active_item");
    $('#partners_menu_edit_item').toggleClass("partner_menu_deactive_item");
}



/**
 * 
 * 
 */
function toggle_divs(active_div){
   	
   if( is_add_open ){
	   close_add_partner();
   }
   
   if( is_list_open ){
	   close_list_partner();
   }
   
   if( is_edit_open ){
	   close_edit_partner();
   }
   
   if( active_div.id === "partners_menu_add_item" && !is_add_open ){
	   open_add_partner();
   }
   
   if( active_div.id === "partners_menu_list_item" && !is_list_open ){
	   open_list_partner();
   }
   
   if( active_div.id === "partners_menu_edit_item" && !is_edit_open ){
	   open_edit_partner();
   }
   
   //$("#"+active_div.id).toggleClass("partner_menu_active_item");
   //$("#"+active_div.id).toggleClass("partner_menu_deactive_item");
}


/**
 * 
 * @param elem
 * @param id
 * @returns
 */

function toggle_input(elem,id){
	
	$("#"+id).val("");
	if( elem.checked && id === 'partner_api_secret'){
		has_secret = true;
		$('#partner_api_secret').show();
		return;
	}
	  
    if( !elem.checked && id === 'partner_api_secret'){
    	has_secret = false;
    	$('#partner_api_secret').hide();
    	return;
	}
	
    if( elem.checked && id === 'partner_api_key'){
    	has_key = true;
    	$('#partner_api_key').show();
    	return;
	}
    
    if( !elem.checked && id === 'partner_api_key'){
    	has_key = false;
    	$('#partner_api_key').hide();
    	return;
	}
	
    if( elem.checked && id === 'partner_api_token_url'){
    	has_token = true; 
    	$('#partner_api_token_url').show();
    	return;
    }

    if( !elem.checked && id === 'partner_api_token_url'){
    	has_token = false;
    	$('#partner_api_token_url').hide();
    	return;
    }
    
    /* Edit */
    if( elem.checked && id === 'partner_edit_api_secret'){
		edit_has_secret = true;
		$('#partner_edit_api_secret').show();
		return;
	}
	  
    if( !elem.checked && id === 'partner_edit_api_secret'){
    	edit_has_secret = false;
    	$('#partner_edit_api_secret').hide();
    	return;
	}
	
    if( elem.checked && id === 'partner_edit_api_key'){
    	edit_has_key = true;
    	$('#partner_edit_api_key').show();
    	return;
	}
    
    if( !elem.checked && id === 'partner_edit_api_key'){
    	edit_has_key = false;
    	$('#partner_edit_api_key').hide();
    	return;
	}
	
    if( elem.checked && id === 'partner_edit_api_token_url'){
    	edit_has_token = true; 
    	$('#partner_edit_api_token_url').show();
    	return;
    }

    if( !elem.checked && id === 'partner_edit_api_token_url'){
    	edit_has_token = false;
    	$('#partner_edit_api_token_url').hide();
    	return;
    }
}

/**
 * 
 * @param elem
 * @returns
 */
function handle_url_type_select(elem){
	if (elem.value === "Other"){
		$("#"+elem.id+"_other").show();
	}else{
		$("#"+elem.id+"_other").val('');
		$("#"+elem.id+"_other").hide();
	}
}

/**
 * 
 * @returns
 */
function add_url(action_type,value){
	
	if(action_type === "add"){	
		
			last_added_url += 1;
			num_urls += 1;
			if (num_urls == 1){
				$('#partners_add_urls').show();
			}
			
			var url_div = $("<div class='partners_url_div'></div>").attr('id','url_div_'+last_added_url);
			
			var type_div = $("<div class='partners_url_type_divs'></div>")
			var select = $("<select class='partners_url_selects partners_url_type_items' onchange='handle_url_type_select(this)'>"+
					        "<option>List</option><option>Details</option><option>Availability</option><option>Other</option></select>").attr('id','url'+last_added_url+'type');
			
			type_div.append($("<div class='partners_url_type_title partners_url_type_items'>Url Type </div>"))
			type_div.append(select);
			type_div.append($("<input type='text' class='partners_url_inps partners_url_type_items' placeholder='Please enter the type' />").attr('id','url'+last_added_url+'type_other'));
			
			url_div.append(type_div);
			url_div.append($("<input type='text' class='partners_add_form_inps' placeholder='Please enter the url' />").attr('id','url'+last_added_url+'url'));
			url_div.append($("<p class='partners_url_title'></p>").text("Description"));
			url_div.append($("<textarea class='partners_url_descps'></textarea>").attr('id','url'+last_added_url+'describ'));
			url_div.append($("<div class='partners_url_rem_buttons'>Remove</div>").attr('id','url'+last_added_url+'rembut'));
				
			$('#partners_add_urls').append(url_div);
			$('#url'+last_added_url+'rembut').click( ( function(num){
				 return function(){
					 $('#url_div_'+num).empty();
					 $('#url_div_'+num).remove();
					 num_urls -=1 ;
					 if (num_urls == 0){
							$('#partners_add_urls').hide();
					 }
					 
					 if(num_urls == 4){
						   $('#partner_form_addurl_button').hide();
					 }
				 };      
			})(last_added_url));
			
			if(num_urls == 5){
				   $('#partner_form_addurl_button').hide();
				   return;	
			}
	}
	
	if(action_type === "edit"){	
		
		edit_last_added_url += 1;
		edit_num_urls += 1;
		if (edit_num_urls == 1){
			$('#partners_edit_urls').show();
		}
		
		var url_div = $("<div class='partners_url_div'></div>").attr('id','edit_url_div_'+edit_last_added_url);
		
		var type_div = $("<div class='partners_url_type_divs'></div>")
		var select = $("<select class='partners_url_selects partners_url_type_items' onchange='handle_url_type_select(this)'>"+
				        "<option>List</option><option>Details</option><option>Availability</option><option>Other</option></select>").attr('id','edit_url'+edit_last_added_url+'type');
				
		type_div.append($("<div class='partners_url_type_title partners_url_type_items'>Url Type </div>"))
		type_div.append(select);
		type_div.append($("<input type='text' class='partners_url_inps partners_url_type_items' placeholder='Please enter the type' />").attr('id','edit_url'+edit_last_added_url+'type_other'));
		
		url_div.append(type_div);
		
		url_div.append($("<input type='text' class='partners_add_form_inps' placeholder='Please enter the url' />").attr('id','edit_url'+edit_last_added_url+'url'));
		url_div.append($("<p class='partners_url_title'></p>").text("Description"));
		url_div.append($("<textarea class='partners_url_descps'></textarea>").attr('id','edit_url'+edit_last_added_url+'describ'));
		url_div.append($("<div class='partners_url_rem_buttons'>Remove</div>").attr('id','edit_url'+edit_last_added_url+'rembut'));
			
		$('#partners_edit_urls').append(url_div);
		
		if( value != null ){
			$('#edit_url'+edit_last_added_url+'type').find('option').each(function() {
		        if( String(value.url_type) === String($(this).val()) ){
		        	($(this).prop('selected',true));
		        	if ( String(value.url_type) === String("Other") ){
		        		$('#edit_url'+edit_last_added_url+'type_other').val(String(value.type_other));
		        		$('#edit_url'+edit_last_added_url+'type_other').show();
		        	}
		        }
		    });
			
			$('#edit_url'+edit_last_added_url+'url').val(value.url);
			$('#edit_url'+edit_last_added_url+'describ').val(value.description);
		}
		
		
		$('#edit_url'+edit_last_added_url+'rembut').click( ( function(num){
			 return function(){
				 $('#edit_url_div_'+num).empty();
				 $('#edit_url_div_'+num).remove();
				 edit_num_urls -=1 ;
				 if (edit_num_urls == 0){
						$('#partners_edit_urls').hide();
				 }
				 
				 if(edit_num_urls == 4){
					   $('#partner_form_editaddurl_button').show();
				 }
			 };      
		})(edit_last_added_url));
		
		if(edit_num_urls == 5){
			   $('#partner_form_editaddurl_button').hide();
			   return;	
		}
     }
	
	
}


/**
 * 
 * @returns
 */
function remove_url(){
	
	$('#url_div_'+num_urls).last().remove();
	
	num_urls -= 1;
	
	if (num_urls == 4){
		$('#partner_form_addurl_button').show();
	}
	
	if (num_urls ==  0){
		$('#partners_add_urls').hide();
		$('#partner_form_remurl_button').hide();
	}
	
}

/**
 * 
 * @returns
 */
function add_language(action_type){
	
   if(action_type === "add"){	
		   var lang_str = $("#partners_languages_select").val();
		   if( lang_str === "none" ){
			   return;
		   }
		   var contains = false;
		   for(i=0;i<supported_languages.length;i++){
			   if( supported_languages[i] === lang_str){
				   contains = true;
				   break;
			   }
		   }
		   
		   if( !contains ){
			  supported_languages.push(lang_str);
			  $("#partners_langs_div").append($("<div class='partner_langs_subdivs'></div>").attr('id',lang_str+'_subdiv').text(lang_str));
			  var close_div = $("<div class='partner_langs_subdivs_close'></div>").click(function(){
				  for(i=0;i<supported_languages.length;i++){
					   if( supported_languages[i] === lang_str){
						   supported_languages.splice(i,1);
						   $("#"+lang_str+'_subdiv').remove();
					   }
				  }
			  });
			  
			  $("#"+lang_str+'_subdiv').append(close_div);
		   }
		   return;
   }	   
   
   if(action_type === "edit"){	
	   var lang_str = $("#partners_edit_languages_select").val();
	   if( lang_str === "none" ){
		   return;
	   }
	   var contains = false;
	   for(i=0;i<edit_supported_languages.length;i++){
		   if( String(edit_supported_languages[i]) === String(lang_str)){
			   contains = true;
			   break;
		   }
	   }
	   
	   if( !contains ){
		  edit_supported_languages.push(lang_str);
		  $("#partners_edit_langs_div").append($("<div class='partner_langs_subdivs'></div>").attr('id',lang_str+'_editsubdiv').text(lang_str));
		  var close_div = $("<div class='partner_langs_subdivs_close'></div>").click(function(){
			  for(i=0;i<edit_supported_languages.length;i++){
				   if( String(edit_supported_languages[i]) === String(lang_str) ){
					   edit_supported_languages.splice(i,1);
					   $("#"+lang_str+'_editsubdiv').remove();
				   }
			  }
		  });
		  
		  $("#"+lang_str+'_editsubdiv').append(close_div);
	   }
	   return;
    }	   
}

/**
 * 
 * @returns
 */
function add_currency(action_type){
   
   if(action_type === "add"){	
		   var curr_str = $("#partners_currencies_select").val();
		   if( curr_str === "none" ){
			   return;
		   }
		   var contains = false;
		   for(i=0;i<supported_currencies.length;i++){
			   if( supported_currencies[i] === curr_str){
				   contains = true;
				   break;
			   }
		   }
		   
		   if( !contains ){
			  supported_currencies.push(curr_str);
			  $("#partners_currens_div").append($("<div class='partner_currens_subdivs'></div>").attr('id',curr_str+'_subdiv').text(curr_str));
			  var close_div = $("<div class='partner_currens_subdivs_close'></div>").click(function(){
				  for(i=0;i<supported_currencies.length;i++){
					   if( supported_currencies[i] === curr_str){
						   supported_currencies.splice(i,1);
						   $("#"+curr_str+'_subdiv').remove();
					   }
				  }
			  });
			  
			  $("#"+curr_str+'_subdiv').append(close_div);
		   }
		   return;
   }	   
   
   if(action_type === "edit"){	
	   var curr_str = $("#partners_edit_currencies_select").val();
	   if( curr_str === "none" ){
		   return;
	   }
	   var contains = false;
	   for(i=0;i<edit_supported_currencies.length;i++){
		   if( String(edit_supported_currencies[i]) === String(curr_str)){
			   contains = true;
			   break;
		   }
	   }
	   
	   if( !contains ){
		  edit_supported_currencies.push(curr_str);
		  $("#partners_edit_currens_div").append($("<div class='partner_currens_subdivs'></div>").attr('id',curr_str+'_editsubdiv').text(curr_str));
		  var close_div = $("<div class='partner_currens_subdivs_close'></div>").click(function(){
			  for(i=0;i<edit_supported_currencies.length;i++){
				   if( String(edit_supported_currencies[i]) === String(curr_str) ){
					   edit_supported_currencies.splice(i,1);
					   $("#"+curr_str+'_editsubdiv').remove();
				   }
			  }
		  });
		  
		  $("#"+curr_str+'_editsubdiv').append(close_div);
	   }
	   return;
    }	   
}

/**
 * 
 * @returns
 */
function check_fields(action_type){
	
	if(action_type === "add"){	
			all_checked = true;
			if( $('#partner_name').val().length == 0  ){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_add_key_check')[0].checked && $('#partner_api_key').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_add_secret_check')[0].checked && $('#partner_api_secret').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_add_token_check')[0].checked && $('#partner_api_token_url').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if (supported_languages.length <= 0 || supported_currencies.length <= 0){
				all_checked = false;
				return all_checked;
			}
			
		   	for(i=1;i<=last_added_url;i++){
				if( $("#url_div_"+i)[0] ){	
				    if( $('#url'+i+'type').val().length == 0 || ( $('#url'+i+'type').val() === "Other"  && $('#url'+i+'type_other').val().length == 0 )  
				    		|| $('#url'+i+'url').val().length == 0 	|| $('#url'+i+'describ').val().length == 0){
						all_checked = false;
						return all_checked;
					}
				}
			}
			
			return all_checked;
	}
	
	if( action_type === "edit"){
		    
			all_checked = true;
			if( $('#partner_edit_name').val().length == 0  ){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_edit_key_check')[0].checked && $('#partner_edit_api_key').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_edit_secret_check')[0].checked && $('#partner_edit_api_secret').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if($('#partners_edit_token_check')[0].checked && $('#partner_edit_api_token_url').val().length == 0){
				all_checked = false;
				return all_checked;
			}
			
			if (edit_supported_languages.length <= 0 || edit_supported_currencies.length <= 0 ){
				all_checked = false;
				return all_checked;
			}
			
			for(i=1;i<=edit_last_added_url;i++){
				if( $("#edit_url_div_"+i)[0] ){	
				    if( $('#edit_url'+i+'type').val().length == 0 ||  ( $('#edit_url'+i+'type').val() === "Other"  && $('#edit_url'+i+'type_other').val().length == 0 )   
				    		    || $('#edit_url'+i+'url').val().length == 0 || $('#edit_url'+i+'describ').val().length == 0){
						all_checked = false;
						return all_checked;
					}
				}
			}
			
			return all_checked;
	}
	
}

/**
 * 
 * @returns
 */
function store_data(){
	if(check_fields("add") ){
		/*$.get("store_new_partner_info",
			  {"data":create_json()});*/
		
		$.ajax({ methode:"GET",
			     url:"../Admin/store_new_partner_info",
		      	 data:{req_type:"ajax",partner_data:create_json("add")},
		      	 success: function(data){
		      		if( data === "logedout"){
		      			$(location).attr("href","../Admin_Login.jsp");
		      		}else{
		      			close_add_partner();
		      		    open_add_partner();
		      		    show_partners_msg("The partner was successfully added to the system.");
		      		}    
		       	 },
		      	 error: function(){
		      		show_partners_msg("The partner could not be added to the system. Please try again later.");
		      	 }
		});
	}else{
		$("#partners_add_form_err").show();
	}
}

/**
 * 
 * @returns
 */
function create_json(action_type){
	
	var json_obj = {}
	
	if(action_type === "add"){	
		
			json_obj["partner_name"] = $('#partner_name').val().trim();
			json_obj["partner_category"] = $('#partners_category').val().trim();
			json_obj["data_format"] = $('#partners_data_format').val().trim();
			json_obj["languages"] = supported_languages.join(",");
			json_obj["currencies"] = supported_currencies.join(",");
			
			if($('#partners_add_key_check')[0].checked ){
				json_obj["has_key"] = "YES";
				json_obj["api_key"] = $('#partner_api_key').val().trim();
			}else{
				json_obj["has_key"] = "NO";
			}
			
			if($('#partners_add_secret_check')[0].checked ){
				json_obj["has_secret"] = "YES";
				json_obj["api_secret"] = $('#partner_api_secret').val().trim();
			}else{
				json_obj["has_secret"] = "NO";
			}
		
			if($('#partners_add_token_check')[0].checked ){
				json_obj["has_token"] = "YES";
				json_obj["api_token_url"] = $('#partner_api_token_url').val().trim();
			}else{
				json_obj["has_token"] = "NO";
			}
		
			urls = []
			
			for(i=1;i<=last_added_url;i++){
				if( $("#url_div_"+i)[0] ){	
					url_t = {}
					url_t['url_type'] = $('#url'+i+'type').val().trim();
					url_t['url_type_other'] = $('#url'+i+'type_other').val().trim();
					url_t['url'] = $('#url'+i+'url').val().trim();
					url_t['url_describ'] =	$('#url'+i+'describ').val().trim();
					urls[i-1] = url_t;
				}	
			}
			
			json_obj['urls'] = urls;
	}
	
	if(action_type === "edit"){	
		
		    json_obj["partner_id"] = $('#edit_partner_id').val();
			json_obj["partner_name"] = $('#partner_edit_name').val().trim();
			json_obj["partner_category"] = $('#partners_edit_category').val().trim();
			json_obj["data_format"] = $('#partners_edit_data_format').val().trim();
			json_obj["languages"] = edit_supported_languages.join(",");
			json_obj["currencies"] = edit_supported_currencies.join(",");
			
			if($('#partners_edit_key_check')[0].checked ){
				json_obj["has_key"] = "YES";
				json_obj["api_key"] = $('#partner_edit_api_key').val().trim();
			}else{
				json_obj["has_key"] = "NO";
			}
			
			if($('#partners_edit_secret_check')[0].checked ){
				json_obj["has_secret"] = "YES";
				json_obj["api_secret"] = $('#partner_edit_api_secret').val().trim();
			}else{
				json_obj["has_secret"] = "NO";
			}
		
			if($('#partners_edit_token_check')[0].checked ){
				json_obj["has_token"] = "YES";
				json_obj["api_token_url"] = $('#partner_edit_api_token_url').val().trim();
			}else{
				json_obj["has_token"] = "NO";
			}
		
			urls = []
			
			for(i=1;i<= edit_last_added_url;i++){
				if( $("#edit_url_div_"+i)[0] ){	
					url_t = {}
					url_t['url_type'] = $('#edit_url'+i+'type').val().trim();
					url_t['url_type_other'] = $('#edit_url'+i+'type_other').val().trim();
					url_t['url'] = $('#edit_url'+i+'url').val().trim();
					url_t['url_describ'] =	$('#edit_url'+i+'describ').val().trim();
					urls[i-1] = url_t;
				}	
		}
		
		json_obj['urls'] = urls;
	}
	
	return JSON.stringify(json_obj);	
   
}

/*############################# List ################################*/

/**
 * 
 * 
 */
function list_partners(){
	var partner_cat = $("#partners_list_category").val();
	var txt_val = $("#partners_list_category option:selected").text();
	close_list_partner();
	open_list_partner();
	$.ajax({ methode:"GET",
	     url:"../Admin/get_partners_list",
	     data:{req_type:"ajax","partner_cat":partner_cat},
    	 success: function(data){
    		 if( data === "logedout"){
	      			$(location).attr("href","../Admin_Login.jsp");
	      	 }else{
    		        create_partners_list(data,txt_val);
	      	 }       
    	 },
    	 error: function(){
    		 show_partners_msg("We experiencing some technical issue at the moment. Please try again later.");
    	 }
	});
}

/**
 * 
 * @param json_obj
 * @returns
 */
function create_partners_list(json_obj,title){
	$("#partners_main_content").append($("<div></div>").attr('id',"partners_list_div"));
	
	if( json_obj.length > 0 ){
	   $("#partners_list_div").append($("<div></div>").attr('id','partners_list_title').text(title));
	}
	
	$("#partners_list_div").append($("<div></div>").attr('id','accordion'));
		
	for( i=0 ; i < json_obj.length ; i++ ){
		$("#accordion").append($("<h3></h3>").text(json_obj[i].p_name));
		var table = $("<table class='partners_list_tables'></table>").attr('id','partners_list_table'+i);
		var table_2 = $("<table class='partners_list_tables_url'></table>").attr('id','partners_list_table_url'+i);
		var urls = JSON.parse(json_obj[i].urls);
		if( urls.length > 0 ){
		    $("#accordion").append($("<div></div>").append(table).append("<div id='url_label_div'>List of Urls</div>").append(table_2));
		} else{
			$("#accordion").append($("<div></div>").append(table));
		}   
		    
		var tr = $("<tr></tr>").append($("<td><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>Category: </span>"+json_obj[i].p_cat+
				                            "</div><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>Sub Category: </span>"+json_obj[i].p_subcat+           
				                              "</div><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>Data Format: </span>"+json_obj[i].p_data_form+"</div></td>"));
		
		$('#partners_list_table'+i).append(tr);
		tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>Supported Languages: </span>"+json_obj[i].p_languages+"</td>"));
		$('#partners_list_table'+i).append(tr);
		
		tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>Supported Currencies: </span>"+json_obj[i].p_currencies+"</td>"));
		$('#partners_list_table'+i).append(tr);
		
		if ( json_obj[i].p_has_key === "YES" ){
			tr = $("<tr></tr>").append($("<td><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>API Key: </span>"+
					   json_obj[i].p_key+"</div>" +
					    "<div class='partners_list_copy_div' id='key_copy_div"+i+"'>	</div></td>"));
			
			$('#partners_list_table'+i).append(tr);
			copy_content = json_obj[i].p_key;
			$("#key_copy_div"+i).click( (function(content){
				return function(){
				        copy_content_to_clipboard(content);
				};
			})(copy_content));
		}else{
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Key: </span>Not Applicable</td>"));
			$('#partners_list_table'+i).append(tr);
		}
		
				
		if ( json_obj[i].p_has_secret === "YES" ){
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Secret: </span>"+
					 json_obj[i].p_secret+
					  "<div class='partners_list_copy_div' id='secret_copy_div"+i+"'>	</div></td>"));
			$('#partners_list_table'+i).append(tr);
			copy_content = json_obj[i].p_secret;
			$("#secret_copy_div"+i).click( (function(content){
				return function(){
				        copy_content_to_clipboard(content);
				};
			})(copy_content));
		}else{
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Secret: </span>Not Applicable</td>"));
			$('#partners_list_table'+i).append(tr);
		}
		
		if ( json_obj[i].p_has_token === "YES" && json_obj[i].p_token ){
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Token: </span>"+
			      json_obj[i].p_token+
			      "</td><td><div class='partners_list_tab_infodivs partners_list_copy_div' id='token_copy_div"+i+"'></div></td>"));
			$('#partners_list_table'+i).append(tr);
			copy_content = json_obj[i].p_token;
			$("#token_copy_div"+i).click( (function(content){
				return function(){
				        copy_content_to_clipboard(content);
				};
			})(copy_content));
			
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Token Expiry Date: </span>"+json_obj[i].p_expiry_date+"</td>"));
			$('#partners_list_table'+i).append(tr);
		}else{
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>API Token: </span>Not Applicable</td>"));
			$('#partners_list_table'+i).append(tr);
		}
		
		//var urls = JSON.parse(json_obj[i].urls);
		
		for(j = 0 ; j < urls.length ; j++){
			tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>Url: </span>"+urls[j].url+
					   "<div class='partners_list_copy_div' id='url_copy_div"+i+"-"+j+"'></div></td>"));
			$('#partners_list_table_url'+i).append(tr);
			copy_content = urls[j].url;
			$("#url_copy_div"+i+"-"+j).click( (function(content){
				return function(){
				        copy_content_to_clipboard(content);
			    };
			})(copy_content));
			
			if(urls[j].url_type === "Other"){
			    tr = $("<tr></tr>").append($("<td><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>Type: </span>"+urls[j].url_type +
			    		                       "</div><div class='partners_list_tab_infodivs'><span class='partners_list_tab_lables'>Type Details: </span>"+urls[j].type_other+"</div></td>"));
			}else{
				tr = $("<tr></tr>").append($("<td><span class='partners_list_tab_lables'>Type: </span>"+urls[j].url_type +"</td>"));
			}
			$('#partners_list_table_url'+i).append(tr);
			
			tr = $("<tr></tr>").append($("<td  class='partners_list_table_url_lastrow'><span class='partners_list_tab_lables'>Description: </span>"+urls[j].description +"</td>"));
			$('#partners_list_table_url'+i).append(tr);
		}
	}
	
	$( "#accordion" ).accordion({
		active:false,
		icons: {"header": "ui-icon-custome-triangle-e", "activeHeader": "ui-icon-custome-triangle-s" },
		collapsible: true,
		heightStyle: "content"
	});
	
}


function copy_content_to_clipboard(text){
	var id = "mycustom-clipboard-textarea-hidden-id";
    var existsTextarea = document.getElementById(id);

    if(!existsTextarea){
        console.log("Creating textarea");
        var textarea = document.createElement("textarea");
        textarea.id = id;
        // Place in top-left corner of screen regardless of scroll position.
        textarea.style.position = 'fixed';
        textarea.style.top = 0;
        textarea.style.left = 0;

        // Ensure it has a small width and height. Setting to 1px / 1em
        // doesn't work as this gives a negative w/h on some browsers.
        textarea.style.width = '1px';
        textarea.style.height = '1px';

        // We don't need padding, reducing the size if it does flash render.
        textarea.style.padding = 0;

        // Clean up any borders.
        textarea.style.border = 'none';
        textarea.style.outline = 'none';
        textarea.style.boxShadow = 'none';

        // Avoid flash of white box if rendered for any reason.
        textarea.style.background = 'transparent';
        document.querySelector("body").appendChild(textarea);
        console.log("The textarea now exists :)");
        existsTextarea = document.getElementById(id);
    }
    
    existsTextarea.value = text;
    existsTextarea.select();

    try {
        var status = document.execCommand('copy');
        if(!status){
            alert("Text cannot be copied into the clipboard. please copy the text manually.");
        }else{
            alert("The text is now copied into the clipboard");
        }
    } catch (err) {
    	alert("Text cannot be copied into the clipboard. please copy the text manually.");
    }
} 

/*################################## Edit #######################################*/

function creat_list_existing_partners(){
	$.ajax({ methode:"GET",
	     url:"../Admin/get_partners_list",
	     data:{req_type:"ajax","partner_cat":"All_Partners"},
   	 success: function(data){
   		if( data === "logedout"){
  			$(location).attr("href","../Admin_Login.jsp");
  		}else{
   		    process_partners_list(data);
  		}    
   	 },
   	 error: function(){
   		show_partners_msg("We experiencing some technical issue at the moment. Please try again later.");
   	 }
	});
}


function process_partners_list(data){
	list_of_partners = data;
	$("#existing_partners_list").empty();
	$("#existing_partners_list").append($("<option></option>").prop("selected",true).text("Please select a partner"))
	for(i=0 ; i < list_of_partners.length ; i++){
		$("#existing_partners_list").append($("<option></option>").attr("value",list_of_partners[i].p_name).text(list_of_partners[i].p_name));
    }
}

function refresh_edit_info_form(){
	$('#partner_edit_name').val("");
	$("#partners_edit_langs_div").empty();
	$("#partners_edit_currens_div").empty();
	edit_supported_languages = [];
	edit_supported_currencies = [];
	edit_num_urls = 0;
	edit_last_added_url = 0;
	$('#partners_edit_key_check').prop('checked', false);
	$('#partners_edit_secret_check').prop('checked', false);
	$('#partners_edit_token_check').prop('checked', false);
	$('#partner_edit_api_key').hide();
	$('#partner_edit_api_secret').hide();
	$('#partner_edit_api_token_url').hide();
	
	$("#partner_form_editaddurl_button").show();
	$('#partners_edit_category option:first').prop('selected',true);
	$('#partners_edit_data_format option:first').prop('selected',true);
	$('#partners_edit_languages_select option:first').prop('selected',true);
	$('#partners_edit_currencies_select option:first').prop('selected',true);
	$('#partners_edit_urls').empty();
	$('#partners_edit_urls').hide();
	
	
}


function create_partner_edit_div(){
	refresh_edit_info_form();
		
	var chosen_partner = $("#existing_partners_list").val();
	if (chosen_partner === "Please select a partner"){
		$("#partner_info_edit_div").hide()
		return
	}
	var partner_obj = null;
	
	if( ! is_edit_info_open ){
		$("#partner_info_edit_div").show(); 
	}	
	
	for(i=0 ; i < list_of_partners.length ; i++){
	   	if(list_of_partners[i].p_name === chosen_partner ){
	   		partner_obj = list_of_partners[i];
	   	}
	}
	if( !$("#edit_partner_id")[0] ){
	   $("#partner_info_edit_div").append($("<input/>").prop('type','hidden').attr('id','edit_partner_id').val(partner_obj.p_id));
	}else{
		$('#edit_partner_id').val(partner_obj.p_id);
	}
	
	$("#partner_edit_name").val(partner_obj.p_name);
    $("#partners_edit_category").find('option').each(function() {
        if( partner_obj.p_cat+"_"+partner_obj.p_subcat === $(this).val() ){
        	($(this).prop('selected',true));
        }
    });
    
    $("#partners_edit_data_format").find('option').each(function() {
        if( partner_obj.p_data_form === $(this).val() ){
        	($(this).prop('selected',true));
        }
    });
    
    $(partner_obj.p_languages.split(",")).each(function(){
    	edit_supported_languages.push(this);
    });
    
       
    $(partner_obj.p_languages.split(",")).each(function(){
    	$("#partners_edit_langs_div").append($("<div class='partner_langs_subdivs'></div>").attr('id',this+'_editsubdiv').text(this));
    	var lang = this;
    	var close_div = $("<div class='partner_langs_subdivs_close'></div>").click(function(){
    	  for(i=0; i < edit_supported_languages.length; i++){
    		   if( String(edit_supported_languages[i]) === String(lang)){
  				   edit_supported_languages.splice(i,1);
  				   $("#"+lang+'_editsubdiv').remove();
  			   }
  		  }
  	  });
  	  $("#"+lang+'_editsubdiv').append(close_div);  
    });
    
    $(partner_obj.p_currencies.split(",")).each(function(){
    	edit_supported_currencies.push(this);
    });
   
    $(partner_obj.p_currencies.split(",")).each(function(){
    	$("#partners_edit_currens_div").append($("<div class='partner_currens_subdivs'></div>").attr('id',this+'_editsubdiv').text(this));
    	var curren = this;
    	var close_div = $("<div class='partner_currens_subdivs_close'></div>").click(function(){
    	  for(i=0; i < edit_supported_currencies.length; i++){
    		   if( String(edit_supported_currencies[i]) === String(curren)){
  				   edit_supported_currencies.splice(i,1);
  				   $("#"+curren+'_editsubdiv').remove();
  			   }
  		  }
  	  });
  	  $("#"+curren+'_editsubdiv').append(close_div);  
    });
    
    
    if( String(partner_obj.p_has_key) === String("YES") ){
    	$("#partners_edit_key_check").prop('checked',true);
       	$("#partner_edit_api_key").val(String(partner_obj.p_key));
    	$("#partner_edit_api_key").show();
    }
    
    
    if( String(partner_obj.p_has_secret) === String("YES") ){
    	$("#partners_edit_secret_check").prop('checked',true);
       	$("#partner_edit_api_secret").val(String(partner_obj.p_key));
    	$("#partner_edit_api_secret").show();
    }
    
    
    if( String(partner_obj.p_has_token) === String("YES") ){
    	$("#partners_edit_token_check").prop('checked',true);
    	urls = JSON.parse(partner_obj.urls);
    	$(urls).each(function(){
        	if( String(this.url_type) === String( "Token") ){
        	 		$("#partner_edit_api_token_url").val(String(this.url));
          	}
        });
        $("#partners_edit_token_check").prop('checked',true);
        $("#partner_edit_api_token_url").show();
    }
    
    urls = JSON.parse(partner_obj.urls);
	$(urls).each(function(){
    	if( String(this.url_type) !== String( "Token") ){
    		add_url('edit',this);
    	    //$('#partners_edit_urls').append(url_div);
    	}
    });
    
}

/**
 * 
 * 
 */
function edit_partner(action_type){
	if( check_fields("edit") ){
        $.ajax({ methode:"GET",
			     url:"../Admin/update_partner_info",
		      	 data:{req_type:"ajax",partner_new_data:create_json("edit"),action:action_type},
		      	 success: function(data){
		      		if( data === "logedout"){
		      			$(location).attr("href","../Admin_Login.jsp");
		      		}else{ 
		      		    close_edit_partner();
		      		    open_edit_partner();
		      		    $('#partner_info_edit_div').hide();
		      		    show_partners_msg("The partner info was successfully updated in the system.");
		      		}    
		       	 },
		      	 error: function(){
		      		close_edit_partner();
		      		open_edit_partner();
		      		$('#partner_info_edit_div').hide(); 
		      		show_partners_msg("The partner info could not be updated in the system. Please try again later.");
		      	 },
		      	async: false
		});
	}else{
		$("#partners_edit_form_err").show();
	}
	
}



