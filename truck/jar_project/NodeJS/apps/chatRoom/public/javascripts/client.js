/*!
 * ChatService Client JavaScript Library v1.7.1
 * Copyright 2012,ethanlam
 *
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

function ChatClient(){};

ChatClient.prototype = {
 


     /*发送消息*/
    sendMsg:function(){
	    var msg =  $("#msg").val();
		var nick = $("#nick").val();
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
		 timestamp = new Date(timestamp);
              switch (type) {
				  case "msg":
					  msgHtml= "<tr><td>"+nick+" 说：</td>"+
							   "<td>"+msg+"</td>"+
							   "<td>"+timestamp+"</td></tr>";
					break;
				  case "join":
					  msgHtml= "<tr><td>"+nick+"</td>"+
							   "<td>加入讨论</td>"+
							   "<td>"+timestamp+"</td></tr>";
					break;
				  case "part":
					  msgHtml= "<tr><td>"+nick+"</td>"+
							   "<td>离开状态</td>"+
							   "<td>"+timestamp+"</td></tr>";
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