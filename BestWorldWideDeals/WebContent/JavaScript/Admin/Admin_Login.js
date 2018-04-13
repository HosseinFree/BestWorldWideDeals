

function check_form(){
	un = $("#Admin_un").val();
	pass = $("#Admin_pass").val();
	
	if( un === "" || pass === ""){
		$("#Admin_error").empty();
		$("#Admin_error").append("<p>Please check all required fields are filled correctly.</p>");
		$("#Admin_error").removeClass("Admin_error_deactive");
		return false;
	}
	return true;
	
}

