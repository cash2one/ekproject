/*!
 * ChatService Client JavaScript Library v1.7.1
 * Copyright 2012,ethanlam
 * Date: Thir Mar 08 21:11:03 2012 -0001
 *
 */

 
 var CONFIG = { debug: false
             , nick: "#"   // set in onConnect
             , id: null    // set in onConnect
             , last_message_time: 1
             , focus: true //event listeners bound in onConnect
             , unread: 0 //updated in the message-processing loop
     };

var transmission_errors = 0;

util = {
  urlRE: /https?:\/\/([-\w\.]+)+(:\d+)?(\/([^\s]*(\?\S+)?)?)?/g, 

  //  html sanitizer 
  toStaticHTML: function(inputHtml) {
    inputHtml = inputHtml.toString();
    return inputHtml.replace(/&/g, "&amp;")
                    .replace(/</g, "&lt;")
                    .replace(/>/g, "&gt;");
  }, 

  //pads n with zeros on the left,
  //digits is minimum length of output
  //zeroPad(3, 5); returns "005"
  //zeroPad(2, 500); returns "500"
  zeroPad: function (digits, n) {
    n = n.toString();
    while (n.length < digits) 
      n = '0' + n;
    return n;
  },

  //it is almost 8 o'clock PM here
  //timeString(new Date); returns "19:49"
  timeString: function (date) {
    var minutes = date.getMinutes().toString();
    var hours = date.getHours().toString();
    return this.zeroPad(2, hours) + ":" + this.zeroPad(2, minutes);
  },

  //does the argument only contain whitespace?
  isBlank: function(text) {
    var blank = /^\s*$/;
    return (text.match(blank) !== null);
  }
};



function ChatClient(){};

ChatClient.prototype = {
 


     /*发送消息*/
    sendMsg:function(){
	    var msg =  $.trim($("#msg").val());
		var nick = $("#nick").val();
		if(!msg){
		  alert('不能发送空的信息...');
		  return;
		}
	    $.get('/send?nick='+nick+"&msg="+msg,function(data){
		       if(data){
			      data = eval(data);
			      ///alert(data.result);
				  $("#msg").val('');
			   }
               
		});
	},


    /*轮询服务器的信息*/
    longPoll:function(data){
	
	    if(data){
		   var msgHtml = "";
		   data = eval(data);
		   if(data)
           $.each(data,function(index){
               if (data[index].timestamp > CONFIG.last_message_time)
                   CONFIG.last_message_time = data[index].timestamp;
			   chatClient.appendMessage(data[index].nick,data[index].type,data[index].timestamp,data[index].msg);

		   });       
		}
        
		
         //make another request
        $.ajax({ cache: false
			 , type: "GET"
			 , url: "/msgs"
			 , dataType: "json"
			 , data: { since: CONFIG.last_message_time,nick: CONFIG.nick }
			 , error: function () {
				 chatClient.appendMessage("", "long poll error. trying again...", new Date(), "error");
				 transmission_errors += 1;
				 //don't flood the servers on error, wait 10 seconds before retrying
				 setTimeout(chatClient.longPoll, 10*1000);
			   }
			 , success: function (data) {
				 transmission_errors = 0;
				 //if everything went well, begin another request immediately
				 //the server will take a long time to respond
				 //how long? well, it will wait until there is another message
				 //and then it will return it to us and close the connection.
				 //since the connection is closed when we get data, we longPoll again
				 chatClient.longPoll(data);
			   }
         });

    	
	
	},


    /*显示信息*/
    appendMessage:function(nick,type,timestamp,msg){
         var msgHtml ="";
		 timestamp = util.timeString(new Date(timestamp));
              switch (type) {
				  case "msg":
					  msgHtml= "<tr><td>"+nick+"说("+timestamp+")：</td>"+
							   "<td>"+msg+"</td></tr>";
					break;
				  case "join":
					  msgHtml= "<tr><td>"+nick+"</td>"+
							   "<td>进入聊天室(At："+timestamp+")....</td></tr>";
					break;
				  case "part":
					  msgHtml= "<tr><td>"+nick+"</td>"+
							   "<td>离线...(At："+timestamp+")</td></tr>";
					break;
				}

	      msgHtml="<table>"+msgHtml+"</table>";
 	      $('#message_list').append(msgHtml); 
	}


}

var chatClient =  new ChatClient();

$(document).ready(function(){
    $("#sendBtn").click(chatClient.sendMsg);
	CONFIG.nick = $("#nick").val();
    chatClient.longPoll();
});