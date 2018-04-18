function get_local(){
	return $("#language_val").val().split("_")[0];
} 

function get_region(){
	return $("#language_val").val().split("_")[1];
} 



function search_hotels(){
	$.get("hotels_search_results", 
			{"data":JSON.stringify(create_json_info())}
	);
}


function create_json_info(){
	var lat_lan = $("#destination_coord").attr("value");
	var output = { "destination":{"lat":lat_lan.split(",")[0],"lan":lat_lan.split(",")[1]},
			       "from_date":$("#from_date_inp").attr("value"),
			       "to_date":$("#to_date_inp").attr("value"),
			       "room_type":$("#room_type_inp").attr("value"),
			       "num_of_rooms":$("#num_of_rooms").attr("value"),
			       "rooms_info":""
	             };
	var rooms_info ={};
	if(parseInt($("#room_type_inp").attr("value")) > 2 ){
		
		for(i=1;i<= parseInt($("#num_of_rooms").attr("value"));i++){
			rooms_info["room"+i]={"adults":$("#room"+i+"_adult").val(),
					              "children":$("#room"+i+"_children").val(),
					              "childrenAge":{"1":$("#room"+i+"_child1").val(),
					            	             "2":$("#room"+i+"_child2").val(),
					            	             "3":$("#room"+i+"_child3").val(),
					            	             "4":$("#room"+i+"_child4").val()
					                             }
					            	  
					               }
			}//end of for
	 }
	 output["rooms_info"]=rooms_info;
	return output;
}