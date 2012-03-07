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
	  var callbacks = [];
      
	  /*������Ϣ��Ϣ*/
	  this.appendMessage = function(nick,type,msg){

	      console.log("User %s[%s]: %s ",nick,type,msg);

          var m = { nick: nick
				    , type: type // "msg", "join", "part"
				    , msg: msg
				    , timestamp: (new Date()).getTime()
                   };

		 
		  /*������Ϣ*/
		  messages.push( m );

          /*��Ϣ�����Ƿ����*/ 
		  while (messages.length > MESSAGE_BACKLOG)
            messages.shift();
           

		  /*�����еĶ���*/
          while (callbacks.length > 0) {
             callbacks.shift().callback([m]);
          }

	  };


      /*��ѯ���µ���Ϣ*/
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
      

	  /*����Ƿ���������ȴ����߳�Ӧ��*/
	  setInterval(function () {
			var now = new Date();
			while (callbacks.length > 0 && now - callbacks[0].timestamp > 30*1000) {
			  callbacks.shift().callback([]);
			}
	  }, 3000);

}


/*�û����������ң�ע��*/
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
       console.log("UNfound User %s in onlineUserList ",sessions[i].nick);
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



/*��ѯ���µ�������Ϣ*/
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
