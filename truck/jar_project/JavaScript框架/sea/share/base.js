seajs.config({
    alias: {
      'jquery': '../base/jquery',
	  'console': '../share/console',
      'validate': '../share/validate/validate'
    },
    charset: 'utf-8',
    timeout: 20000,
    debug: false
});



/*测试模块*/
define(function(require, exports, module) {

	var $ = require('jquery');
	var validate = require('validate');
	var CONSOLE = require('console');
     

    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/) {
		
		 $("#sbBtn").click(function(){    
			

		 });

		 CONSOLE.Debug('TestModule ready....');
		 
    }


});