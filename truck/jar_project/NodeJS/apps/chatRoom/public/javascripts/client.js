/*!
 * ChatService Client JavaScript Library v1.7.1
 * Copyright 2012,ethanlam
 *
 * Date: Thir Mar 08 21:11:03 2012 -0001
 *
 */

 
function ChatClient(){};

ChatClient.prototype = {
 
    sendMsg:function(){
	    var msg =  $("#msg").val();
		var nick = $("#nick").val();
	    $.get('/send?nick='+nick+"&msg="+msg,function(result){
		     alert('send ok');
		});
	}

}

var chatClient =  new ChatClient();

$(document).ready(function(){
    $("#sendBtn").click(chatClient.sendMsg);
});