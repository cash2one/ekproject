/*²âÊÔÓÃÀı*/

net = require("net");   
var sockets = [];   
var s = net.Server(function(socket) { 
	                console.log('Server have acept new connnetion ');
					sockets.push(socket);   
					socket.on("data", function(d) {   
					for (var i=0; i < sockets.length; i++ ) {   
						sockets[i].write(d);   
					}   
	            });   
        });
		
s.listen(8080); 