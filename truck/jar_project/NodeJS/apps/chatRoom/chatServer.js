/*
 *  ChatRoom Online Demo
 *  实时的聊天室 Demo
 *  Copyright 2012,ethanlam
 *
 */
var sys = require("sys"),
    url = require("url"),
    qs = require("querystring");


/*定义的常量*/
var MESSAGE_BACKLOG = 1000,
    SESSION_TIMEOUT = 60 * 1000;


var sessions = {};


/*处理管道消息*/
var channel = new function(){
      
	  /*消息盒*/
	  var messages = [];

	  /*当前用户管道 - 在线的 */
	  var callbacks = [];
      
	  /*发布消息消息*/
	  this.appendMessage = function(nick,type,msg){

	      console.log("User %s[%s]: %s ",nick,type,msg);

          var m = { nick: nick
				    , type: type // "msg", "join", "part"
				    , msg: msg
				    , timestamp: (new Date()).getTime()
                   };

		 
		  /*保存信息*/
		  messages.push( m );

          /*消息队列是否过长*/ 
		  while (messages.length > MESSAGE_BACKLOG)
            messages.shift();
           

		  /*阻塞中的队列*/
          while (callbacks.length > 0) {
             callbacks.shift().callback([m]);
          }

	  };


      /*查询最新的消息*/
	  this.query = function(since,callback){

			var matching = [];
			for (var i = 0; i < messages.length; i++) {
			  var message = messages[i];
			  if (message.timestamp > since)
				matching.push(message);
			}

			if (matching.length != 0) {
			  callback(matching);
			} else {
			  callbacks.push({ timestamp: new Date(), callback: callback });
			}
	  };
      

	  /*检测是否存在阻塞等待的线程应用*/
	  setInterval(function () {
			var now = new Date();
			while (callbacks.length > 0 && now - callbacks[0].timestamp > 30*1000) {
			  callbacks.shift().callback([]);
			}
	  }, 3000);

}


/*用户进入聊天室，注册*/
exports.createSession = function(nick){
      
	  if(!nick)
        return;

	  for (var i in sessions) {
		var session = sessions[i];
		console.log("Current User online list %s ",session.nick);
		if (session && session.nick === nick) return null;
	  }

      console.log(" New User %s Join to chat Room ",nick);
	  var session = { 
		nick: nick, 
		id: Math.floor(Math.random()*99999999999).toString(),
		timestamp: new Date(),

		poke: function () {
		  session.timestamp = new Date();
		},

		destroy: function () {
		  channel.appendMessage(session.nick, "part");
		  delete sessions[session.id];
		}
	  };
      
	  sessions[session.id] = session;
	  //广播加入信息
	  channel.appendMessage(nick,'join','Hello');
	  return session;
}


/*判断用户Session是否超时了，是的话直接销毁改Session*/
setInterval(function () {
  var now = new Date();
  for (var id in sessions) {
    if (!sessions.hasOwnProperty(id)) continue;
    var session = sessions[id];

    if (now - session.timestamp > SESSION_TIMEOUT) {
      session.destroy();
    }
  }
}, 1000);



/*发表留言*/
exports.sendMessage = function(nick,msg){
   var session = null;
   for (var i in sessions) {
      console.log("Find User list %s ",sessions[i].nick);
	  if (sessions[i] && sessions[i].nick === nick) 	
		 session = sessions[i];
   }
   if(session){
	   session.poke();
	   channel.appendMessage(session.nick,'msg',msg);
   }else{
       console.log("UNfound User %s in onlineUserList ",sessions[i].nick);
   }
}



/*获取当前在线的用户*/
exports.onlineUsers = function(){
     var users = [];
	 for(var i in sessions){
	    users.push(sessions[i].nick);
	 }
	 return users;
}



/*查询最新的留言信息*/
exports.query = function(nick,since,callback){
	 if(!nick)
		return;
	 
	 var session = null;
     for (var i in sessions) {
		  if (sessions[i] && sessions[i].nick === nick) 	
			 session = sessions[i];
     } 

	 if(session){
		 console.log("User %s checkMsgs ",session.nick); 
		 since = parseInt(since, 10);
		 channel.query(since,callback);
	 }

}
