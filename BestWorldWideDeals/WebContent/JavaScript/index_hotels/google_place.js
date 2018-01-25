var is_suggestion_window_open = false;
var sugesstion_items;
var suggestion_item_num = 0;
var prev_val="";
var is_enter_key_pressed = false;
var placeDetails, autocomplete;

$(window).resize(function() {
	if(document.getElementsByClassName("search_suggest_div").length>0){
    	document.body.removeChild(document.getElementsByClassName("search_suggest_div")[0]);
    }
});


document.addEventListener('click',function(elem) {
     if(!(elem.target.id == "search_suggest_menu" || $(elem.target).parents("#search_suggest_menu").length > 0)){
		remove_sugg_div();
     }	
});


$("#search_input").keydown(function(e) {
		
    	if( is_suggestion_window_open ){
    		if  (e.keyCode == 38 || e.keyCode == 40){
    		    e.preventDefault();
    		    $("#"+suggestion_item_num).toggleClass("active_suggest_item");
    			$("#"+suggestion_item_num).toggleClass("deactive_item");
	    	    if (e.keyCode == 40){ // key down 
	    			if( suggestion_item_num == 5 ){
	    				suggestion_item_num = 1;
	    			}else{
	    				suggestion_item_num += 1
	       			}
	    		}else if (e.keyCode == 38){
	    			if( suggestion_item_num == 1 ){
	    				suggestion_item_num = 5;
	    			}else{
	    				if( suggestion_item_num > 1 ){
	    				   suggestion_item_num -= 1
	    				}   
	    			}
	    		}
	    	    $("#"+suggestion_item_num).toggleClass("active_suggest_item");
    			$("#"+suggestion_item_num).toggleClass("deactive_item");
    			return;
	    	
    		}
    		
    		if  (e.keyCode == 13){
    			if(suggestion_item_num>0){
    			  $("#search_input").val(suggestion_items[parseInt(suggestion_item_num-1)].name);
    			  remove_sugg_div();
    			  $("#search_input").blur();
    			  is_enter_key_pressed = true;
    			}  
    	    }
    	}	
   });


/*
 * 
 * 
 * 
 */
function initAutocomplete(){
	val = $("#search_input").val();
	
	if ( prev_val === val ) {
	    return;	
	}
	if(is_enter_key_pressed){
		is_enter_key_pressed = false;
		return;
	}
	
	prev_val = val;
	if( val.length == 0 ){
		remove_sugg_div();	
	}
	
	var show_preds = function(predictions,status){
		if (status != google.maps.places.PlacesServiceStatus.OK) {
			var y = $("#search_input").offset().top+$("#search_input").outerHeight();
			var x = $("#search_input").offset().left;
			var str="<b class='sugg_border_notch sugg_notch'></b><b class='sugg_notch'></b>";
			if(document.getElementsByClassName("search_suggest_div").length==0){
				var sugg_div = $("<div/>");
				sugg_div.attr("id","search_suggest_menu");
				sugg_div.attr("class","search_suggest_div sugg_border_callout");
				if ( $(document).width() <= 782){
				  sugg_div.css("width",$("#search_input").innerWidth()-5);
				}	  
				sugg_div.css("top",y+12);
				sugg_div.css("left",x);
				$("body").append(sugg_div);
			}
			$(".search_suggest_div").children("div").remove();
			str += '<div = "sugg_error_msg"><span>No Result Found For "'+val+'"</span></div>';	
	    	$(".search_suggest_div").html($(".search_suggest_div").html()+str);
			return;
        }
		
		render_suggestions(predictions);
	};	
	
	autocomplete = new google.maps.places.AutocompleteService();
	autocomplete.getPlacePredictions({input:$("#search_input").val()},show_preds);
}

/*
 * 
 * 
 * 
 */
function render_suggestions(predictions){
	var y = $("#search_input").offset().top+$("#search_input").outerHeight();
	var x = $("#search_input").offset().left;
	var str="<b class='sugg_border_notch sugg_notch'></b><b class='sugg_notch'></b>";
	if(document.getElementsByClassName("search_suggest_div").length==0){
		var sugg_div = $("<div/>");
		sugg_div.attr("id","search_suggest_menu");
		sugg_div.attr("class","search_suggest_div sugg_border_callout");
		if ( $(document).width() <= 782){
		  sugg_div.css("width",$("#search_input").innerWidth()-5);
		}	  
		sugg_div.css("top",y+12);
		sugg_div.css("left",x);
		$("body").append(sugg_div);
		is_suggestion_window_open = true;
	}
	
	$(".search_suggest_div").children("div").remove();
	
	count = 0;
	suggestion_items = [];
	predictions.forEach(function(prediction){
		
		temp_obj = {
			"name":	prediction.description,
		    "place_id": prediction.place_id
		};
		suggestion_items.push(temp_obj);
		count = count + 1;
		str += "<div class='deactive_item suggestion_items' id='"+count+"'><span>"+prediction.description+"</span></div>";
		
    });
	
	$(".search_suggest_div").html($(".search_suggest_div").html()+str);
	$(".suggestion_items").click(function(){
		$("#search_input").val(suggestion_items[parseInt(this.id-1)].name);
		unpdate_dest_coord(suggestion_items[parseInt(this.id-1)].place_id)
		remove_sugg_div();
	});
	
}

/*
 * 
 * 
 * 
 */
function remove_sugg_div(){
	if(document.getElementsByClassName("search_suggest_div").length>0){
		is_suggestion_window_open = false;
		suggestion_item_num = 0;
		document.body.removeChild(document.getElementsByClassName("search_suggest_div")[0]);
	}
}



/*
 * 
 * 
 */
function unpdate_dest_coord(place_id){
	placeDetails = new google.maps.places.PlacesService($('#hidd_content').get(0));
	placeDetails.getDetails({placeId:place_id},function(place, status){
		$("#destination_coord").attr("value",place.geometry.location.lat()+','+place.geometry.location.lng());
	});
}

/*
 * Future Improvements:
 * 1- create calendars  dynamically instead of hard coding them in html.
 */