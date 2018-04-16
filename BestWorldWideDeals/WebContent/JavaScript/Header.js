/**
 * 
 */
var is_account_menu_open = false;
var is_language_menu_open = false;
var is_currency_menu_open = false;
var is_small_menu_open = false;
var is_small_account_menu_open = false;
var is_small_language_menu_open = false;
var is_small_currency_menu_open = false;
var language_val = 'EN';
var currency_val = 'CAD';

document.onclick = function(elem) {
	if (!(  elem.target.id == "account_menu" || elem.target.id == "account_div" || elem.target.id == "acc_arrow") ){
		$("#acc_arrow").addClass('arrow_down').removeClass('arrow_up');
		is_account_menu_open = false;
		$("#account_menu").hide();
	 }    
	
	if (!( elem.target.id == "language_menu" || elem.target.id == "language_div" || elem.target.id == "lan_arrow") ){
		$("#lan_arrow").addClass('arrow_down').removeClass('arrow_up');
		is_language_menu_open = false;
		$("#language_menu").hide();
	 }    
	
	if (!(  elem.target.id == "currency_menu" || elem.target.id == "currency_div" || elem.target.id == "cur_arrow") ){
		$("#cur_arrow").addClass('arrow_down').removeClass('arrow_up');
		is_currency_menu_open = false;
		$("#currency_menu").hide();
	 }    
};

function toggle_acc_menu(){
	if ( is_account_menu_open == false) {
		$("#account_menu").show();
		$("#acc_arrow").addClass('arrow_up').removeClass('arrow_down');
		is_account_menu_open = true;
	}else{
		is_account_menu_open = false;
		$("#acc_arrow").addClass('arrow_down').removeClass('arrow_up');
		
		$("#account_menu").hide();
    }
}

function toggle_lan_menu(){
	if ( is_language_menu_open == false) {
		$("#language_menu").show();
		$("#lan_arrow").addClass('arrow_up').removeClass('arrow_down');
		is_language_menu_open = true;
	}else{
		is_language_menu_open = false;
		$("#lan_arrow").addClass('arrow_down').removeClass('arrow_up');
		$("#language_menu").hide();
    }
}

function toggle_cur_menu(){
	if ( !is_currency_menu_open) {
		$("#currency_menu").show();
		$("#cur_arrow").addClass('arrow_up').removeClass('arrow_down');
		is_currency_menu_open = true;
	}else{
		is_currency_menu_open = false;
		$("#cur_arrow").addClass('arrow_down').removeClass('arrow_up');
		$("#currency_menu").hide();
    }
}

function small_menu_view_switch(elem){
	elem.classList.toggle("change");
}


function toggle_small_menu(){
	if ( is_small_menu_open ){
	   is_small_menu_open = false;	
	   $("#cover_screen").hide();
	   $("#small_menu").animate({width: 'toggle'},"fast","linear");
	}else{
		is_small_menu_open = true;	
		$("#small_menu").animate({width: 'toggle'},"fast","linear");
		$("#cover_screen").show();
		
	}   
}


function toggle_small_acc_menu(){
	if ( is_small_account_menu_open == false) {
		if ( is_small_currency_menu_open == true) {
			$("#small_currency_menu").animate({height: 'toggle'},"slow","linear");
			$("#small_cur_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
	        is_small_currency_menu_open = false;
	     }
	    if ( is_small_language_menu_open == true) {
			$("#small_language_menu").animate({height: 'toggle'},"slow","linear");
			$("#small_lan_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
	        is_small_language_menu_open = false;
        }
		$("#small_account_menu").animate({height: 'toggle'},"slow","linear");
		$("#small_acc_arrow").addClass('small_menu_arrow_up').removeClass('arrow_down');
		is_small_account_menu_open = true;
	}else{
		is_small_account_menu_open = false;
		$("#small_acc_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
		
		$("#small_account_menu").animate({height: 'toggle'},"slow","linear");
    }
}

function toggle_small_cur_menu(){
	
	if ( is_small_currency_menu_open == false) {
		if ( is_small_account_menu_open == true) {
				$("#small_account_menu").animate({height: 'toggle'},"slow","linear");
				$("#small_acc_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
		        is_small_account_menu_open = false;
		}
		if ( is_small_language_menu_open == true) {
			$("#small_language_menu").animate({height: 'toggle'},"slow","linear");
			$("#small_lan_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
	        is_small_language_menu_open = false;
	    }
	
		$("#small_currency_menu").animate({height: 'toggle'},"slow","linear");
		$("#small_cur_arrow").addClass('small_menu_arrow_up').removeClass('arrow_down');
		is_small_currency_menu_open = true;
	}else{
		is_small_currency_menu_open = false;
		$("#small_cur_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
		$("#small_currency_menu").animate({height: 'toggle'},"slow","linear");
    }
}

function toggle_small_lan_menu(){
	
	if ( is_small_language_menu_open == false) {
		if ( is_small_account_menu_open == true) {
				$("#small_account_menu").animate({height: 'toggle'},"slow","linear");
				$("#small_acc_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
		        is_small_account_menu_open = false;
		}
		if ( is_small_currency_menu_open == true) {
			$("#small_currency_menu").animate({height: 'toggle'},"slow","linear");
			$("#small_cur_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
	        is_small_currency_menu_open = false;
	     }
	    
		$("#small_language_menu").animate({height: 'toggle'},"slow","linear");
		$("#small_lan_arrow").addClass('small_menu_arrow_up').removeClass('arrow_down');
		is_small_language_menu_open = true;
	}else{
		is_small_language_menu_open = false;
		$("#small_lan_arrow").addClass('arrow_down').removeClass('small_menu_arrow_up');
		$("#small_language_menu").animate({height: 'toggle'},"slow","linear");
    }
}


function set_value(element,value){
	if (element === 'currency')
		currency_val = value;
	   
	if (element === 'language')
		language_val = value;
	
	toggle_small_cur_menu()
	
	$('#'+element+'_div').text(value);
	$('#small_'+element+'_val').text(value);
}

function show_partners_msg(msg_content){
	$("body").append($("<div id='partners_add_msg_div'></div>"));
}