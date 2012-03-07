/*
 *  ChatRoom Online Demo
 *  实时的聊天室 Demo
 *
 */
var sys = require("sys"),
    url = require("url"),
    qs = require("querystring");


/*定义的常量*/
var MESSAGE_BACKLOG = 200,
    SESSION_TIMEOUT = 60 * 1000;


var sessions = {};


/*处理管道消息*/
var channel = new function(){
      
	  /*消息盒*/
	  var messages = [];

	  /*当前用户管道 - 在线的 */
	  var callBacks = [];
      
	  /*发布消息消息*/
	  this.appendMessage= function(nick,type,msg){
	      console.log("User %s[%s]: %s ",nick,type,msg);
	  }
      

}


/*用户进入聊天室，注册*/
exports.createSession = function(nick){
	  
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
       console.log("unfound User %s in onlineUserList ",sessions[i].nick);
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

