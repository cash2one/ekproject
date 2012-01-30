seajs.config({
   /// base: './base/',
    alias: {
      'jquery': '../base/jquery',
      'validate': '../share/validate/validate'
    },
    charset: 'utf-8',
    timeout: 20000,
    debug: false
});



/*²âÊÔÄ£¿é*/
define(function(require, exports, module) {

	var $ = require('jquery');
	var validate = require('validate');
     
    exports.init = function() {
   
       $.each(['test_1','test_2'],function(data){
    		 $('.tul').append('<li>'+this+'</li>');
       });


       $("#sbBtn").click(function(){
		    validate.validate();
	   });


	   $(document).ready(function(){
           alert('dd');
		  
		});
	  
    }


});