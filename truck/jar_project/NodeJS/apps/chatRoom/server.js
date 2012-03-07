/*
 *  ChatRoom Online Demo
 *  ʵʱ�������� Demo
 *
 */
var sys = require("sys"),
    url = require("url"),
    qs = require("querystring");


/*����ĳ���*/
var MESSAGE_BACKLOG = 200,
    SESSION_TIMEOUT = 60 * 1000;


var sessions = {};


/*����ܵ���Ϣ*/
var channel = new function(){
      
	  /*��Ϣ��*/
	  var messages = [];

	  /*��ǰ�û��ܵ� - ���ߵ� */
	  var callBacks = [];
      
	  /*������Ϣ��Ϣ*/
	  this.appendMessage= function(nick,type,msg){
	      console.log("User %s[%s]: %s ",nick,type,msg);
	  }
      

}


/*�û����������ң�ע��*/
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
	  //�㲥������Ϣ
	  channel.appendMessage(nick,'join','Hello');
	  return session;
}



/*�ж��û�Session�Ƿ�ʱ�ˣ��ǵĻ�ֱ�����ٸ�Session*/
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



/*��������*/
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



/*��ȡ��ǰ���ߵ��û�*/
exports.onlineUsers = function(){
     var users = [];
	 for(var i in sessions){
	    users.push(sessions[i].nick);
	 }
	 return users;
}

