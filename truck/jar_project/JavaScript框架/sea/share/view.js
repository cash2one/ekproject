seajs.config({
    alias: {
      'jquery': '../base/jquery',
	  'mustache':'../base/mustache',
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
	var mustache = require('mustache');
	var validate = require('validate');
	var CONSOLE = require('console');
     

    /*View Render*/
    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		 
		 CONSOLE.Debug('[View]: Test render Function....');
            
         var view = {
				  "header": "模版技术",
				  "items": [
					  {"name": "百度", "link": true, "url": "http://www.baidu.com"},
					  {"name": "新浪", "link": true, "url": "http://www.sina.com.cn"},
					  {"name": "网易", "link": true, "url": "http://www.163.com"}
				  ],
				  "empty": false
		 };
				  
        var template = $('#render_template').html();  
        var html = mustache.to_html(template, view); 
        $('#render_template').html(html);
		CONSOLE.Debug(html);
    }

    
    
    



});