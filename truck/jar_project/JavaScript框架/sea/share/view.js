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
     

    /*Page Render*/
    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		   CONSOLE.Debug('TestModule ready....');

           

    }





});