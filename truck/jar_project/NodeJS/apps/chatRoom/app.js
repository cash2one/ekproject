
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , qs = require("querystring")
  , url = require("url")
  , chatServer = require('./server');

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

/*Ĭ�ϵ���ҳ*/
app.get('/', routes.index);


/*���û���¼��Chat������*/
app.get('/join', function(req, res){
    var nick = qs.parse(url.parse(req.url).query).nick;
    chatServer.createSession(nick);
	var users = chatServer.onlineUsers( );
    console.log('user length:'+users);
    res.render('index', { title: nick,users:users });

});


/*���û���¼��Chat������*/
app.get('/send', function(req, res){
    var nick = qs.parse(url.parse(req.url).query).nick;
	var msg = qs.parse(url.parse(req.url).query).msg;
    chatServer.sendMessage(nick,msg);
    res.render('index', { title: 'sendMsgSuc' });

});


/*��ȡ��ǰ���ߵ��û�*/
app.get('/users', function(req, res){
    var users = chatServer.onlineUsers( );
   // res.simpleJSON(200, {users:users});
	res.render('index', { users:users});
});



app.listen(3000);
console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
