seajs.config({
   /// base: './base/',
    alias: {
      'jquery': '../base/jquery',
	  'console': '../share/console',
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
	var CONSOLE = require('console');
     
    exports.init = function() {
   
		   $.each(['test_1','test_2'],function(data){
				 $('.tul').append('<li>'+this+'</li>');
		   });

		   //init	 
		   validate.init();

		   $("#sbBtn").click(function(){    
				//validate.validate();
				validate.validate("testForm");
		   });


		   CONSOLE.Debug('TestModule ready....');
		   $(document).ready(function(){
				CONSOLE.Debug('document ready');
			});
	  
    }


});