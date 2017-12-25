/**
 * 
 */

var is_account_menu_open = false;
var is_language_menu_open = false;
var is_currency_menu_open = false;


document.onclick = function(elem) {
	
	if (!(  elem.target.id == "account_menu" || elem.target.id == "account_div" ) ){
		$("#acc_arrow").addClass('arrow_down').removeClass('arrow_up');
		is_account_menu_open = false;
		$("#account_menu").hide();
	 }    
	
	if (!(  elem.target.id == "language_menu" || elem.target.id == "language_div" ) ){
		$("#lan_arrow").addClass('arrow_down').removeClass('arrow_up');
		is_language_menu_open = false;
		$("#language_menu").hide();
	 }    
	
	if (!(  elem.target.id == "currency_menu" || elem.target.id == "currency_div" ) ){
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
	if ( is_currency_menu_open == false) {
		$("#currency_menu").show();
		$("#cur_arrow").addClass('arrow_up').removeClass('arrow_down');
		is_currency_menu_open = true;
	}else{
		is_currency_menu_open = false;
		$("#cur_arrow").addClass('arrow_down').removeClass('arrow_up');
		$("#currency_menu").hide();
    }
}





