var is_email_err_open = false;

$("#news_email").focus(function(elem) {
    $("#email_err").hide();
});

$(window).load(function(){
	  $("#content").show();
});

$(window).resize(function(){
	$("#email_err").hide();
});	


function check_email(){
	    
	    var val = document.getElementById("news_email");
	   	var msg = "";

		if (val.value == '') {
			msg = 'This Field is Required. Please <br>Enter a Valid Email Address.';
		} else if (!isEmail(val.value)) {
			msg = 'Please Enter a Valid Email Address. <br>Example: smith@example.com';
		}
		
		if(msg==""){
			$.ajax({
				method : "GET",
				url : "Register_email?email=" + val.value,
				success:function(result){
					if(result==="success"){
						  $("#submit").hide();
					      $(".email_content").html("<p>THANK YOU <i style='font-size:2em;'>&#9786</i><br/>" +
					      		                   "You Sucessfully Subscribed.<br/>" +
					      		                   "<span>You will be notified and receive our exclusive offers when we lunch the website.</span></p>");
					}else if(result==="exist"){
						$("#email_err").children("p").html("This email address is already registered.");
						$("#email_err").show();
					}else if(result==="error"){
						$("#submit").hide();
					    $(".email_content").html("<p>SORRY <i style='font-size:2em;'>&#9785</i><br/>" +
					      		                   "Something Went Wrong.<br/>" +
					      		                   "<span>Please Try Again Later.</span></p>");
					    
					}
				}
			});
		      
		}else{
			  $("#email_err").children("p").html(msg);
			  $("#email_err").show();
		}
}

function isEmail(element) {
	var regx = /^[=`{|}~/_a-z0-9A-Z!#$%&'*+-]+([\.][=`{|}~/_a-z0-9A-Z!#$%&'*+-]+)*@[a-z0-9A-Z]+[-]?[a-z0-9A-Z]+([\.]([a-z0-9A-Z]+[-]?[a-z0-9A-Z]+))+[\s]*$/;
	var value = element;
	return regx.test(value);
}
