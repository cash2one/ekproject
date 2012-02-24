//validate.js
/**
 * 
 * 后台打印
 * Ethan Lam 2012-01-12
 * @return
 * 
 */
define(function(require, exports, module){

	  
	 /*火狐中可打印*/
     exports.Debug = function (message,module){
 	     if($.browser.mozilla)
  	        console.log("[DEBUG - "+module+"]:"+message);
     }

});

 

 

