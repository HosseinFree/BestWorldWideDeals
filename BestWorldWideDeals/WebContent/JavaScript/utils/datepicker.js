today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
global_startDate  = today;
global_endDate    = new Date(today.getFullYear(),today.getMonth(), today.getDate() + 1);
is_from_date_window_open = false;
is_to_date_window_open = false;
is_small_from_date_window_open = false;
is_small_to_date_window_open = false;

var MS_PER_DAY = 1000 * 60 * 60 * 24;


global_months     = 2;
monthNames = [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July',
            'August',
            'September',
            'October',
            'November',
            'December'
        ];
dayNames = [
            'Sunday',
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday'
        ];
weekDays = [
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday',
            'Sunday',
        ];
daysAbbreviation = [
            'Mo',
            'Tu',
            'We',
            'Th',
            'Fr',
            'Sa',
            'Su'
        ];

current_from_date = {
	 day:global_startDate.getDate(),
	 month:global_startDate.getMonth(),
	 year:global_startDate.getFullYear()
};

current_to_date = {
		 day:global_endDate.getDate(),
		 month:global_endDate.getMonth(),
		 year:global_endDate.getFullYear()
};

$( document ).ready(function() {
	
	end_value = global_endDate.getDate() + ' ' + monthNames[global_endDate.getMonth()] + ' ' + global_endDate.getFullYear();
	start_value = global_startDate.getDate() + ' ' + monthNames[global_startDate.getMonth()] + ' ' + global_startDate.getFullYear();
	$("#to_date_val").html(end_value);
	$("#small_to_date_val").html(end_value);
    
    $("#from_date_val").html(start_value);
    $("#small_from_date_val").html(start_value);
});


$(window).resize(function() {
      if(is_small_from_date_window_open){
    	  $('#from_date_input').css('border','2px solid #dedede');
		  $('#from_date_input').css('box-shadow', 'none');
    	  is_small_from_date_window_open = false;
    	  document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
    	  $('#small_from_calendar').hide();
      }
      
      if(is_small_to_date_window_open){
    	  $('#to_date_input').css('border','2px solid #dedede');
		  $('#to_date_input').css('box-shadow', 'none');
    	  is_small_to_date_window_open = false;
    	  document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
    	  $('#small_to_calendar').hide();
      }
      
      if(is_from_date_window_open){
    	  $('#from_date_input').css('border','2px solid #dedede');
		  $('#from_date_input').css('box-shadow', 'none');
    	  is_from_date_window_open = false;
    	  document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
    	  $('#from_calendar').hide();
      }
      
      if(is_to_date_window_open){
    	  $('#to_date_input').css('border','2px solid #dedede');
		  $('#to_date_input').css('box-shadow', 'none');
    	  is_to_date_window_open = false;
    	  document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
    	  $('#to_calendar').hide();
      }
      
});


document.addEventListener('click',function(elem) {
		
	if (!( elem.target.id == "to_date_val" || elem.target.id == "to_calendar" || elem.target.id == "to_date_input" ||
		elem.target.id == "to_right_nav" || elem.target.id == "to_left_nav" || 
		     $(elem.target).parents("#to_calendar").length > 0 ) ){
		if(is_to_date_window_open){
		   $('#to_date_input').css('border','2px solid #dedede');
	       $('#to_date_input').css('box-shadow', 'none');
		}
	    is_to_date_window_open = false;
	    document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
	    $("#to_calendar").hide();
    }    
});

document.addEventListener('click',function(elem) {	
	if (!(  elem.target.id == "from_date_val" || elem.target.id == "from_calendar" || elem.target.id == "from_date_input" ||
			elem.target.id == "from_right_nav" || elem.target.id == "from_left_nav" || 
			     $(elem.target).parents("#from_calendar").length > 0 ) ){
		if(is_from_date_window_open){
		   $('#from_date_input').css('border','2px solid #dedede');
	       $('#from_date_input').css('box-shadow', 'none');
		}   
		is_from_date_window_open = false;
		document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
		$("#from_calendar").hide();
    }    
});

document.addEventListener('click',function(elem) {
	
	if (!( elem.target.id == "small_from_date_val" || elem.target.id == "small_from_calendar" || elem.target.id == "from_date_input" ||
		elem.target.id == "small_from_right_nav" || elem.target.id == "small_from_left_nav" || 
		     $(elem.target).parents("#small_from_calendar").length > 0 ) ){
		if(is_small_from_date_window_open){
		   $('#from_date_input').css('border','2px solid #dedede');
	       $('#from_date_input').css('box-shadow', 'none');
		}   
	    is_small_from_date_window_open = false;
	    document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
	    $("#small_from_calendar").hide();
     }    
});

document.addEventListener('click',function(elem) {
	if (!( elem.target.id == "small_to_date_val" || elem.target.id == "small_to_calendar" || elem.target.id == "to_date_input" ||
		elem.target.id == "small_to_right_nav" || elem.target.id == "small_to_left_nav" || 
		     $(elem.target).parents("#small_to_calendar").length > 0 ) ){
		if(is_small_to_date_window_open){
		   $('#to_date_input').css('border','2px solid #dedede');
	       $('#to_date_input').css('box-shadow', 'none');
		}   
	    is_small_to_date_window_open = false;
	    document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
	    $("#small_to_calendar").hide();
   }    
});

/*
 * 
 * 
 */
function get_num_days(from,to){
	var utc1 = Date.UTC(from.getFullYear(), from.getMonth(), from.getDate());
	var utc2 = Date.UTC(to.getFullYear(), to.getMonth(), to.getDate());
	return Math.round((utc2 - utc1) / MS_PER_DAY);
}

/*
 * 
 * 
 */
function getWeekOfMonth(date) {
            var day = date.getDate()
            day-=(date.getDay()==0?6:date.getDay()-1);//get monday of vm week
            //special case handling for 0 (sunday)
            day+=7;
            //for the first non full week the value was negative

            var prefixes = ['0', '1', '2', '3', '4', '5'];
            return prefixes[0 | (day) / 7];
};

/*
 * 
 * 
 */
function getMonts(startDate,months) {
	var months_list = [];
    for (var i = 0; i < months; i++) {
        var date = new Date(startDate.getFullYear(), startDate.getMonth() + i, 1);
        var month = {
            name: this.monthNames[date.getMonth()],
            year: date.getFullYear(),
            weeks: [],
            is_first: false,
            is_last:false
        }
        
        if( i == 0 ){ // this is the first month
        	month.is_first = true;
        }
        
        if( (i+1) == months ){ // this is the last month
        	month.is_last = true;
        }
        
        while (date.getMonth() === new Date(startDate.getFullYear(), startDate.getMonth() + i, 1).getMonth()) {
            var week = getWeekOfMonth(new Date(date));
            if(month.weeks[week] == null){
                month.weeks[week] = {};
            }

            var day = new Date(date);
            month.weeks[week][dayNames[day.getDay()]] = {
                date: day,
                //name: dayNames[day.getDay()]
            };
            date.setDate(date.getDate() + 1);
        }

        months_list.push(month);
    }
    return months_list;
}


/*
 * 
 * 
 */
function toggleClass(element, className){
    if (!element || !className){
        return;
    }
    var classString = element.className, nameIndex = classString.indexOf(className);
    if (nameIndex == -1) {
        classString += ' ' + className;
    }
    else {
        classString = classString.substr(0, nameIndex) + classString.substr(nameIndex+className.length);
    }
    element.className = classString;
}

/*
 * 
 * 
 */
function updateCalendar(element,startDate,months,type,show_small,show_regular){
	var startDate = startDate ? startDate : element === "from_date_input" ? global_startDate : new Date(global_endDate.getFullYear() , global_endDate.getMonth() -1, 1);
	startDate = startDate.getTime() >= today.getTime() ? startDate : today;
	
    calendar = getMonts(startDate,months);
    renderCalendar(calendar, element,months,type,show_small,show_regular);
    
}

/*
 * 
 * 
 */
function showCalendar(element,type,startDate){
	
    if(element === "from_date_input"){
    	if (type == 'regular'){
    		var startDate = startDate ? startDate : element === "from_date_input" ? global_startDate : new Date(global_endDate.getFullYear() , global_endDate.getMonth()-1, 1);
     	   if (is_from_date_window_open){
		      document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
		      $('#from_date_input').css('border','2px solid #dedede');
			  $('#from_date_input').css('box-shadow', 'none');
		      $('#from_calendar').hide();
		      is_from_date_window_open = false;
		      return;
		   }else{
			   $('#from_date_input').css('border','2px solid #f49000');
			   $('#from_date_input').css('box-shadow', '0px 0px 8px #f49000');
			   is_from_date_window_open = true;		
	     	}   
    	}
    	
    	if (type == 'small'){
    		  var startDate = startDate ? startDate : element === "from_date_input" ? global_startDate : new Date(global_endDate.getFullYear() , global_endDate.getMonth(), 1);
	          if (is_small_from_date_window_open){
		          document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
		          $('#from_date_input').css('border','2px solid #dedede');
				  $('#from_date_input').css('box-shadow', 'none');
			      $('#small_from_calendar').hide();
			      is_small_from_date_window_open = false;
			      return;
			   }else{
				   $('#from_date_input').css('border','2px solid #f49000');
				   $('#from_date_input').css('box-shadow', '0px 0px 8px #f49000');
				   is_small_from_date_window_open = true;		
		     	}
    	}   
	}
		
	if(element === "to_date_input"){
		
		if (type == 'regular'){
			var startDate = startDate ? startDate : element === "from_date_input" ? global_startDate : new Date(global_endDate.getFullYear() , global_endDate.getMonth()-1, 1);
		    if (is_to_date_window_open){	 
                 document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
                 $('#to_date_input').css('border','2px solid #dedede');
				 $('#to_date_input').css('box-shadow', 'none');
			     $('#to_calendar').hide();
			     is_to_date_window_open = false;
			     return;
	        }else{
	        	 $('#to_date_input').css('border','2px solid #f49000');
				 $('#to_date_input').css('box-shadow', '0px 0px 8px #f49000');
	    	     is_to_date_window_open = true;		
	        }
	    }
		
		if (type == 'small'){
			var startDate = startDate ? startDate : element === "from_date_input" ? global_startDate : new Date(global_endDate.getFullYear() , global_endDate.getMonth(), 1);
	          if (is_small_to_date_window_open){
		          document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
		          $('#to_date_input').css('border','2px solid #dedede');
			      $('#to_date_input').css('box-shadow', 'none');
			      $('#small_to_calendar').hide();
			      is_small_to_date_window_open = false;
			      return;
			   }else{
				   $('#to_date_input').css('border','2px solid #f49000');
				   $('#to_date_input').css('box-shadow', '0px 0px 8px #f49000');
				   is_small_to_date_window_open = true;		
		       }
  	    }
	 }

	
	startDate = startDate.getTime() >= today.getTime() ? startDate : today;
	calendar = getMonts(startDate,global_months);
	small_calendar = getMonts(startDate,1);
	if(type == 'regular'){
	   renderCalendar(calendar,element,global_months,'regular',false,true);
	}else{   
	   renderCalendar(small_calendar,element,1,'small',true,false);
	}
}

/*
 * 
 * 
 * 
 */
function setDate(element,date){
        
    if( element ===  "from_date_input" ){
    	
    	value = date.getDate() + ' ' + monthNames[date.getMonth()] + ' ' + date.getFullYear();
       	current_from_date.day = date.getDate();
    	current_from_date.month = date.getMonth();
    	current_from_date.year = date.getFullYear();
    	global_startDate = date;
    	document.getElementById("from_date_val").innerHTML = value;
    	document.getElementById("small_from_date_val").innerHTML = value;
    	
    	if(is_from_date_window_open){
    		document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
    		$('#from_date_input').css('border','2px solid #dedede');
			$('#from_date_input').css('box-shadow', 'none');
			is_from_date_window_open = false;
	    	$("#from_calendar").hide();
    	}
    	
    	if(is_small_from_date_window_open){
    		document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
    		$('#from_date_input').css('border','2px solid #dedede');
			$('#from_date_input').css('box-shadow', 'none');
			is_small_from_date_window_open = false;
	    	$("#small_from_calendar").hide();
    	}
    	   	
        if(global_startDate.getTime() >= global_endDate.getTime()){
        	global_endDate = new Date(date.getFullYear(),date.getMonth(), date.getDate() + 1);
        }
        end_value = global_endDate.getDate() + ' ' + monthNames[global_endDate.getMonth()] + ' ' + global_endDate.getFullYear();
        document.getElementById("to_date_val").innerHTML = end_value;
        document.getElementById("small_to_date_val").innerHTML = end_value;
        $("#num_nights").html(get_num_days(global_startDate,global_endDate)+" Nights Stay");
    }else{
    	value = date.getDate() + ' ' + monthNames[date.getMonth()] + ' ' + date.getFullYear();
        global_endDate = date;
        current_to_date.day = date.getDate();
        current_to_date.month = date.getMonth();
        current_to_date.year = date.getFullYear();
    	document.getElementById("to_date_val").innerHTML = value;
    	document.getElementById("small_to_date_val").innerHTML = value;
    	
    	if(is_to_date_window_open){
    		document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
    		$('#to_date_input').css('border','2px solid #dedede');
			$('#to_date_input').css('box-shadow', 'none');
    		is_to_date_window_open = false;
    	    $('#to_calendar').hide();
    	}
    	
    	if(is_small_to_date_window_open){
    		document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
    		$('#to_date_input').css('border','2px solid #dedede');
			$('#to_date_input').css('box-shadow', 'none');
    		is_small_to_date_window_open = false;
    	    $('#small_to_calendar').hide();
    	}
    	
    	if(global_startDate.getTime() >= global_endDate.getTime()){
    	     global_startDate = new Date(date.getFullYear(),date.getMonth(), date.getDate() -1);
        }
    	start_value = global_startDate.getDate() + ' ' + monthNames[global_startDate.getMonth()] + ' ' + global_startDate.getFullYear();
        document.getElementById("from_date_val").innerHTML = start_value;
        document.getElementById("small_from_date_val").innerHTML = start_value;
        $("#num_nights").html(get_num_days(global_startDate,global_endDate)+" Nights Stay");
      }
}

/*
 * 
 * 
 */
function renderCalendar(calendar, element,months,type,show_small,show_regular) {
	
	var widget = document.createElement('div');
	if (type == 'regular'){
        widget.className = 'calendar';
	}else{
		widget.className = 'small_calendar';
	}
    calendar.forEach(function(month){
    	
        var monthDiv = document.createElement('div');
        monthDiv.className = 'month';

        var name = document.createElement('div');
        name.className = "month_title";
        $(name).html(
                month.name + ' ' + month.year
        );
       
        if(month.is_first){
        	if(months > 1){
        	   toggleClass(monthDiv,'is_first');
        	}   
        	var left_Div = document.createElement('div');
            left_Div.className = "nav_div left_nav";
            
            if (element === "from_date_input"){
            	if(type == 'regular'){
                    left_Div.id="from_left_nav";
            	}else{
            		left_Div.id="small_from_left_nav";
            	}    
            }else{
            	if(type == 'regular'){
            	   left_Div.id="to_left_nav";
            	}else{
            	   left_Div.id="small_to_left_nav";
            	}   
            }
            name.appendChild(left_Div);
        }
     
        if(month.is_last){
                      	
           	var right_Div = document.createElement('div');
               right_Div.className = "nav_div right_nav";
               
               if (element === "from_date_input"){
            	   if(type == 'regular'){
                      right_Div.id="from_right_nav";
            	   }else{
            		  right_Div.id="small_from_right_nav"; 
            	   }   
               }else{
            	   if(type == 'regular'){
               	       right_Div.id="to_right_nav";
            	   }else{
            		   right_Div.id="small_to_right_nav";
            	   }    
               }
               name.appendChild(right_Div);
           }
            monthDiv.appendChild(name);
              
        var table = document.createElement('table');
        table.className = 'table';

        var tableHead = document.createElement('thead');
        var tr =  document.createElement('tr');
        daysAbbreviation.forEach(function(abb) {
            var td = document.createElement('td');
            td.appendChild(
                document.createTextNode(abb)
            );

            tr.appendChild(td);
        });

        tableHead.appendChild(tr);
        table.appendChild(tableHead);

        var tbody = document.createElement('tbody');
        month.weeks.forEach(function(week){
            var tr = document.createElement('tr');
            var td;

            weekDays.forEach(function(day){
                if(typeof week[day] !== 'undefined'){
                    td = document.createElement('td');
                    if(week[day].date.getTime() >= today.getTime()){
                        td.className = 'active';
                        
                        if(week[day].date.getTime() == today.getTime()  && element === "to_date_input"){
                        	td.id = "today_td";
                        }
                        if(week[day].date.getTime() == global_startDate.getTime()){
                        	td.className = "start_selected";
                        }
                        
                        if(week[day].date.getTime() == global_endDate.getTime()){
                            td.className = 'end_selected';
                        }
                        
                        if(week[day].date.getTime() > global_startDate.getTime() && week[day].date.getTime() < global_endDate.getTime()){
                            td.className = 'selected';
                        }
                        
                        td.addEventListener('click', function(e){
                        	if (!(e.target.id === "today_td" && element === "to_date_input") ){
                        		setDate(element,week[day].date);
                        	}
                            
                        });
                        
                     }else{
                        td.className = 'disabled';
                    }

                    td.appendChild(
                        document.createTextNode(week[day].date.getDate())
                    );
                    tr.appendChild(td);
                }else{
                    td = document.createElement('td');
                    tr.appendChild(td);
                }
            });

            tbody.appendChild(tr);
        });

        table.appendChild(tbody);
        monthDiv.appendChild(table);
        widget.appendChild(monthDiv);
        
    });

    
    if(element === "from_date_input" && type ==='regular'){
     	widget.id = "from_cal_div";
    	document.getElementById("from_calendar").appendChild(widget);
    	if(show_regular){
    	   $('#from_calendar').show();
    	}   
        document.getElementById("from_right_nav").addEventListener('click', function(e){
    		    if(element === "from_date_input"){
    		       document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
    		    }
    		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
                var startDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
                updateCalendar(element,startDate,months,type,show_small,show_regular);
        });
      	    
        document.getElementById("from_left_nav").addEventListener('click', function(e){
        	    if(element === "from_date_input"){
    		        document.getElementById("from_calendar").removeChild(document.getElementById("from_cal_div"));
    		    }
    		var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
            updateCalendar(element, startDate,months,type,show_small,show_regular);
        	
            });
      }
    
     if(element === "from_date_input" && type ==='small'){
      	widget.id = "small_from_cal_div";
    	document.getElementById("small_from_calendar").appendChild(widget);
  
    	if(show_small){
    	    $('#small_from_calendar').show();
    	}
    	
        document.getElementById("small_from_right_nav").addEventListener('click', function(e){
    		    if(element === "from_date_input"){
    		       document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
    		    }
    		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
                var startDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
                updateCalendar(element,startDate,1,type,show_small,show_regular);
        });
      	    
        document.getElementById("small_from_left_nav").addEventListener('click', function(e){
        	    if(element === "from_date_input"){
    		        document.getElementById("small_from_calendar").removeChild(document.getElementById("small_from_cal_div"));
    		    }
    		var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
            updateCalendar(element, startDate,1,type,show_small,show_regular);
        	
            });
      }
    
    if(element === "to_date_input" && type ==='regular'){
    	widget.id = "to_cal_div";
    	document.getElementById("to_calendar").appendChild(widget);
        $('#to_calendar').show();
	    document.getElementById("to_right_nav").addEventListener('click', function(e){
	    	if(element === "to_date_input"){
		         document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
			}
		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
            updateCalendar(element, startDate,months,type,show_small,show_regular);
         });
	    
	    document.getElementById("to_left_nav").addEventListener('click', function(e){
	    	if(element === "to_date_input"){
		       document.getElementById("to_calendar").removeChild(document.getElementById("to_cal_div"));
			}
		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
            updateCalendar(element, startDate,months,type,show_small,show_regular);
         });
    }
    
    if(element === "to_date_input" && type ==='small'){
    	widget.id = "small_to_cal_div";
    	document.getElementById("small_to_calendar").appendChild(widget);
        $('#small_to_calendar').show();
	    document.getElementById("small_to_right_nav").addEventListener('click', function(e){
	    	if(element === "to_date_input"){
		         document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
			}
		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
            updateCalendar(element, startDate,months,type,show_small,show_regular);
         });
	    
	    document.getElementById("small_to_left_nav").addEventListener('click', function(e){
	    	if(element === "to_date_input"){
		       document.getElementById("small_to_calendar").removeChild(document.getElementById("small_to_cal_div"));
			}
		    var date = new Date(1 + ' ' + calendar[0].name + ' ' + calendar[0].year);
            var startDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
            updateCalendar(element, startDate,1,type,show_small,show_regular);
         });
    }
} 
