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

	var mustache = require('mustache');
	///var validate = require('validate');
	//var console = require('console');
     

    /* 分页属性 */
	var pageModel={
	     "currentPage:":1,
         "totalPage:":0,
	     "pageSize:":20,
	     "allRecords:":0,
         "items":''
	};
   

    /*View Render*/
    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		

        $('#render_template').html(html);		
    };



    /*DataGrid Render */
    exports.dataGrid = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		
    
       //异步请求处理
       $.ajax({
			type: "POST",
			url: url,
			data: params,
			success: function(result){
				   // console.Debug(result);
					if(result&&result!=''){
						var dataView = toJSON(result);
						pageModel.items = dataView.items;
						pageModel.currentPage = dataView.currentPage;
						pageModel.totalPage = dataView.totalPage;
						pageModel.allRecords = dataView.allRecords;
						pageModel.pageSize = dataView.pageSize;
					}
					if(callBack){
						callBack(result);
					}
				},
			 error: function(result){
					//console.Debug('出现未知异常，操作失败！');
				},
			//dataType: type
		});
      	

		
    };



    /*View Render*/
    exports.templateRender = function(template/*基础模版*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		 
		// console.Debug('[View]: Test render Function....');
            
         var view = {
				  "header": "模版技术",
				  "items": [
					  {"name": "百度", "link": true, "url": "http://www.baidu.com"},
					  {"name": "新浪", "link": true, "url": "http://www.sina.com.cn"},
					  {"name": "网易", "link": true, "url": "http://www.163.com"}
				  ],
				  "empty": false
		 };

         var html = mustache.to_html(template, view); 
         $('#render_template').html(html);
    };

    
	/**
	 * 将字符串转化为JSON对象转换成js中的对象
	 */
	toJSON = function(/*string*/jsonString) {
	    try {
			//console.Debug('View JSON String:'+jsonString);
			return eval('(' + jsonString + ')');
		} catch (ex) {
			return null;
		}
	};
	
    
    



});