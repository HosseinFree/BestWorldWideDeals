var the_last_room_activated = 4 ;

/**
 * 
 */

var is_room_type_menu_open = false;
var is_room_num_menu_open = false;

$(window).resize(function() {

	if(is_room_type_menu_open){
		is_room_type_menu_open = false;
		$('#room_selector').css('border','2px solid #dedede');
		$('#room_selector').css('box-shadow', 'none');
	    $('#room_selector_menu').hide();
	}
	
	if(is_room_num_menu_open){
		$('#room_selector').css('border','2px solid #dedede');
		$('#room_selector').css('box-shadow', 'none');
		deactivate_all_multiple_rooms();
		$('#rooms_info_menu').hide();
		is_room_num_menu_open = false;
		
	}
});

document.addEventListener('click',function(elem) {
	if( !(elem.target.id == "room_selector" || $(elem.target).parents("#room_selector").length > 0 ) ){
	   if(!is_room_num_menu_open){	
		   $('#room_selector').css('border','2px solid #dedede');
		   $('#room_selector').css('box-shadow', 'none');
	   }	   
	   is_room_type_menu_open = false;	
	   $('#room_selector_menu').hide();
	}   
});

document.addEventListener('click',function(elem) {
    if( is_room_num_menu_open ){
		if(!(elem.target.id == "rooms_info_menu" || $(elem.target).parents("#rooms_info_menu").length > 0 
				|| elem.target.id == "family_div" || $(elem.target).parents("#family_div").length > 0 
				  || elem.target.id == "multiple_div" || $(elem.target).parents("#multiple_div").length > 0) ){
		   is_room_num_menu_open = false;
		   
		   if( !(elem.target.id == "room_selector" || $(elem.target).parents("#room_selector").length > 0 ) ){
			   $('#room_selector').css('border','2px solid #dedede');
			   $('#room_selector').css('box-shadow', 'none');
		   }	   
		   $('#rooms_info_menu').hide();
	     }
	}	
});

$("#family_confirm_butt").click(function(){
	confirm_room_num('./Images/Index_hotels/family.png','family','3')
});

$("#multiple_confirm_butt").click(function(){
	confirm_room_num('./Images/Index_hotels/family.png','multiple','4')
});


document.addEventListener('click',function(elem) {
    if( is_room_num_menu_open ){
		if(!(elem.target.id == "rooms_info_menu" || $(elem.target).parents("#rooms_info_menu").length > 0 
				|| elem.target.id == "family_div" || $(elem.target).parents("#family_div").length > 0 
				  || elem.target.id == "multiple_div" || $(elem.target).parents("#multiple_div").length > 0) ){
		   is_room_num_menu_open = false;
		   $('#room_selector').css('border','2px solid #dedede');
		   $('#room_selector').css('box-shadow', 'none');	
		   $('#rooms_info_menu').hide();
	     }
	}	
});

/*
 * 
 * 
 * 
 */
function toggle_room_type_menu(){
	if( !is_room_type_menu_open){
	   is_room_type_menu_open = true;	
	   $('#room_selector').css('border','2px solid #f49000');
	   $('#room_selector').css('box-shadow', '0px 0px 8px #f49000');
	   $('#room_selector_menu').show();
	}else{
	   $('#room_selector').css('border','2px solid #dedede');
	   $('#room_selector').css('box-shadow', 'none');	
	   is_room_type_menu_open = false;	
	   $('#room_selector_menu').hide();
	}
}


/*
 * 
 * 
 * 
 */
function replace_room_selector_val(background_img,text_val){
	$('#room_type_val').html(text_val);
	$('#room_selector').css('background-image','url("'+background_img+'")');
}

/*
 * 
 * 
 * 
 */
function activate_multiple_room(elem){
	deactivate_all_multiple_rooms()
	the_last_room_activated = 1;
	is_room_type_menu_open = false;	
	is_room_num_menu_open = true;
	$("#rooms_info_menu").show();
	$("#room1_info").show();
	$("#add_butt_td").show();
	$("#add_butt").show();
	
	if(elem === "family"){
	     $("#family_confirm_butt").show();
	     $("#multiple_confirm_butt").hide();
	}
	
	if(elem === "multiple"){
	     $("#family_confirm_butt").hide();
	     $("#multiple_confirm_butt").show();
	}
	
	
}


/*
 * 
 * 
 * 
 */
function deactivate_all_multiple_rooms(){
	for(j=1;j <= the_last_room_activated ; j++){
		deactivate_all_child_ages($("#room"+j+"_children"));
		$("#room"+j+"_adult").val(1);
		$("#room"+j+"_children").val(0);
		$("#room"+j+"_info").hide();
	}
	the_last_room_activated = 0;
	$("#add_butt").hide();
	$("#rem_butt").hide();
}


/*
 * 
 * 
 * 
 */
function activate_next_room(){
if(the_last_room_activated < 4 ){
		the_last_room_activated = the_last_room_activated + 1;
		$("#room"+the_last_room_activated+"_info").show();
		
		if(the_last_room_activated == 4){
			$("#add_butt_td").hide();
			$("#rem_butt_td").attr("align","left");
		}
		
		if(the_last_room_activated == 2){
			$("#rem_butt").show();
		}
	}
}

/*
 * 
 * 
 * 
 */
function deactivate_last_room(){
	deactivate_all_child_ages($("#room"+the_last_room_activated+"_children"));
	if( the_last_room_activated > 1 ){
		$("#room"+the_last_room_activated+"_info").hide();
		$("#room"+the_last_room_activated+"_adult").val(1);
		$("#room"+the_last_room_activated+"_children").val(0);
		the_last_room_activated = the_last_room_activated - 1;
		if(the_last_room_activated == 3){
			$("#rem_butt_td").attr("align","center");
			$("#add_butt_td").show();
		}
		
		if(the_last_room_activated == 1){
			$("#rem_butt").hide();
		}
	}	
}

/*
 * 
 * 
 * 
 */
function update_child_ages(elem){
	var num_of_childs = parseInt($(elem).val());
	var room_num = $(elem).attr('id').split("_")[0];
	
	for(i=1;i<=4;i++){
		if ( i > num_of_childs){
			$("#"+room_num+"_child"+i).val(0);	
		    $("#"+room_num+"_child"+i).hide();	
		}    
	}
	$("#"+room_num+"_children_age_label").hide();
}



/*
 * 
 * 
 * 
 */
function deactivate_all_child_ages(elem){
	var num_of_childs = parseInt($(elem).val());
	var room_num = $(elem).attr('id').split("_")[0];
	
	for(i=1;i<=4;i++){
		$("#"+room_num+"_child"+i).val(0);	
	    $("#"+room_num+"_child"+i).hide();	
	}
	$("#"+room_num+"_children_age_label").hide();
}


/*
 * 
 * 
 * 
 */
function activate_num_child_ages(elem){
	var num_of_childs = parseInt($(elem).val());
	var room_num = $(elem).attr('id').split("_")[0];
	if( num_of_childs > 0  ){
		$("#"+room_num+"_children_age_label").show();
		for(i=1;i<=num_of_childs;i++){
			$("#"+room_num+"_child"+i).show();	
		}
    } 	
}


/*
 * 
 * 
 * 
 */
function handle_change_child_num(elem){
	update_child_ages(elem);
	activate_num_child_ages(elem);
}

/*
 * 
 * 
 * 
 */
function confirm_room_num(logo_img,elem_text_val,type_num){
	text_val = document.getElementById(elem_text_val+"_div").firstChild.innerHTML;
	replace_room_selector_val(logo_img,text_val);
	update_room_type_val(type_num);
	is_room_num_menu_open = false;
    $('#room_selector').css('border','2px solid #dedede');
    $('#room_selector').css('box-shadow', 'none');
    $("#"+elem_text_val+"_confirm_butt").hide();
    $('#rooms_info_menu').hide();
    $("#num_of_rooms").attr("value",the_last_room_activated);
}	   

/*
 * 
 * 
 * 
 */
function close_room_info(){
	is_room_num_menu_open = false;
    $('#room_selector').css('border','2px solid #dedede');
    $('#room_selector').css('box-shadow', 'none');	
    $('#rooms_info_menu').hide();
}

/*
 * 
 * 
 * 
*/
function update_room_type_val(type_num){
   $("#room_type_inp").attr("value",type_num);
}
   
