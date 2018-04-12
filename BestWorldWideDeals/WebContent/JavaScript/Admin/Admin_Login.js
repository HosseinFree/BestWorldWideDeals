

function check_form(){
	un = $("#Admin_un").val();
	pass = $("#Admin_pass").val();
	
	if( un === "" || pass === ""){
		$("#Admin_error").append("<p>Please check all required fields are filled correctly.</p>").show();
		return false;
	}
	return true;
	
}

function process_login(){
	if( !check_form() ){
		$("#Admin_error").append("<p>Please check all required fields are filled correctly.</p>").show();
		return;
	}	
	
	un = $("#Admin_un").val();
	pass = $("#Admin_pass").val();
	
	$.get("handle_admin_login",{uname:un,password:pass}).done(function(data){
		    
		       if( data === "passed" ){
		    	   $(location).attr("href","./Admin/Index_Admin.jsp")
		       }
		
     		   if( data === "not_exist" ){
     			  $("#Admin_error").append("<p>Admin information you entered does not exist in our system.</p>").show();
     		   }
     		   
     		   if( data === "system_Error" ){
     			  $("#Admin_error").append("<p>Sorry, we are having some technical difficulty at the moment. Please try again later.</p>").show();
    		   }
     		   
     	   });
	
}	