/**
 * Module dependencies.
 *
 */

var express = require('express')
  , routes = require('./routes')
  , qs = require("querystring")
  , url = require("url")
  , chatServer = require('./chat');

var app = module.exports = express.createServer();

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

app.configure('production', function(){
  app.use(express.errorHandler());
});

// Routes

/*默认的首页*/
app.get('/', routes.index);


/*新用户登录到Chat服务中*/
app.get('/join', function(req, res){
    var nick = qs.parse(url.parse(req.url).query).nick;
    chatServer.createSession(nick);
	var users = chatServer.onlineUsers( );
    console.log('user length:'+users);
    res.render('index', { title: nick,users:users });

});


/*新用户登录到Chat服务中*/
app.get('/send', function(req, res){
    var nick = qs.parse(url.parse(req.url).query).nick;
	var msg = qs.parse(url.parse(req.url).query).msg;
    console.log("server listening  %s send a msg is : %s  ",nick,msg);
    chatServer.sendMessage(nick,msg);
    res.send({ result: '发送成功' });

});


/*获取当前在线的用户*/
app.get('/users', function(req, res){
    var users = chatServer.onlineUsers( );
   // res.simpleJSON(200, {users:users});
	res.render('index', { users:users});
});


/*获取消息*/
app.get('/msgs', function(req,res){
    
    if (!qs.parse(url.parse(req.url).query).since) {
       res.send({ error: "Must supply since parameter" });
       return;
    }
	
	var nick = qs.parse(url.parse(req.url).query).nick;
	var since = qs.parse(url.parse(req.url).query).since;
	/*查询最新的信息记录*/
	chatServer.query(nick,since,function(data){
         /*等待服务返回信息列表，再向客户端发送*/
	     res.send(data);
	
	});
    		 

});


app.listen(3000);
console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
