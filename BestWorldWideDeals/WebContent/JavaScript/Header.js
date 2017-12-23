/**
 * 
 */

var is_account_menu_open = false;


document.onclick = function(elem) {
	if (!(  elem.target.id == "account_menu" || elem.target.id == "account_div" ) ){
		is_account_menu_open = false;
		$("#account_menu").hide();
	 }    
};

function toggle_acc_menu(){
	if ( is_account_menu_open == false) {
		$("#account_menu").show();
		is_account_menu_open = true;
	}else{
		is_account_menu_open = false;
		$("#account_menu").hide();
    }
}
