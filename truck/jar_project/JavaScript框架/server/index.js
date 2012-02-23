/*²âÊÔÓÃÀı*/

var http = require("http");

http.createServer(function(request, response) {
	  response.writeHead(200, {"Content-Type": "text/plain"});
	  response.write("{'pageSize':10,'currentPage':2,'totalPages':100,'allRecords':1000,'items':'{{'a':1,'b':2},{'a':12,'b':22}}'}");
	  response.end();
}).listen(8080);