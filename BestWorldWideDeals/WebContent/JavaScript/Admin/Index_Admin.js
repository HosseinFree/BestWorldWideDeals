/**
 * 
 */
function show_partners_msg(msg_content){
    var msg_div = $("<div></div>").attr('id','admin_msg_div');
    var msg_but = $("<div></div>").attr('id','admin_msg_but').text("OK").click(function(){
    	$("#admin_msg_div").remove();
    });
    
  	msg_div.append($("<div></div>").attr('id','admin_msg_content').append($("<p></p>").text(msg_content)).append(msg_but));
   	$("body").append(msg_div);
}