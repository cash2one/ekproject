//validate.js
/**
 * 
 * 后台打印
 * Ethan Lam 2012-01-12
 * @return
 * 
 */
define(function(require, exports, module){

	 var $ = require('../base/jquery');
	  
	 /*火狐中可打印*/
     exports.Debug = function (message){
 	     if($.browser.mozilla)
  	        console.log("[DEBUG]:"+message);
     }

});

 

 

