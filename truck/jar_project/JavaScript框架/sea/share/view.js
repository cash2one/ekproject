seajs.config({
    alias: {
      'jquery': '../base/jquery',
	  'base': './base',
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
	var validate = require('validate');    
    var console = require('console');
	module._name = "view";


    /* 分页属性 */
	var PageModel={
	     "currentPage:":1,
         "totalPage:":0,
	     "pageSize:":20,
	     "allRecords:":0,
         "items":''
	};

   
    var _formsPageModel = {};


    /*View Render*/
    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		
        $('#render_template').html(html);		
    };



    /*初始化grid*/
    exports.initGrid = function(formId/**/,titles/*列标题*/,columns/*列定义*/){
	        //根据信息，生成对应的模版（列表）,模版加工操作
		    var template = "";
			$.get("../template/dataGrid.htm",function(result){
			    console.Debug("加载模版..."+result,module._name);
				template = result;
                    
                var view = {
					"header": "模版技术",
				    "columns":[{"title":"ID","name":"id"},{"title":"名字","name":"name"}]
				};

				var template = mustache.to_html(template, view);
                console.Debug("模版初始化第一次处理后..."+template,module._name);
               
				template = template.replace(new RegExp(/([)/g),'{{');
	            template = template.replace(new RegExp(/(])/g),'}}');
                console.Debug("模版初始化第二次处理后..."+template,module._name);
				
				var data ={
				     "items": [
					  {"id": "001","name": "百度", "link": true, "url": "http://www.baidu.com"},
					  {"id": "002","name": "新浪", "link": true, "url": "http://www.sina.com.cn"},
					  {"id": "003","name": "网易", "link": true, "url": "http://www.163.com"}
				     ]
				};

			    var html = mustache.to_html(template,data);
				console.Debug("最终合并后的结果"+html,module._name);

			});
	};






    /*DataGrid Render */
    exports.dataGrid = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
	   
	   //提交的参数
	   var formParamsStr = (formId&&formId!='')?base._getFormValues(formId):"";
       url += formParamsStr;
	   
	  
	   //异步请求处理
       $.ajax({
			type: "POST",
			url: url,
			data: params,
		    contentType: "text/plain",
			success: function(result){
				   console.Debug("请求地址["+url+"]后返回的数据："+result,module._name);
				   var dataView = toJSON($.trim(result));
					if(result&&result!=''&&dataView){
                        var pageModel = {};
						pageModel.currentPage = dataView.currentPage;
						pageModel.totalPage = dataView.totalPage;
						pageModel.allRecords = dataView.allRecords;
						pageModel.pageSize = dataView.pageSize;
						pageModel.items = dataView.items;
					    _formsPageModel.formId = pageModel; 

                        var html = mustache.to_html($('#render_template').html(),dataView); 
                        $('#render_template').html(html);

					}else{
						console.Debug("toJSON失败....",module._name);
					}
					console.Debug("测试PageMode数据："+_formsPageModel.formId.items[0].name,module._name);
					if(callBack){
						var dataView = toJSON($.trim(result));
						callBack(dataView);
					}
			        },
			 error: function(result){
					//console.Debug('出现未知异常，操作失败！');
					 console.Debug("error",module._name);
				   }
			//dataType: type
		});
      	
    };



    /*View Render*/
    exports.templateRender = function(template/*基础模版*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
		 
		 console.Debug('Test templateRender Function....',module._name);   
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


	exports.testTemplate = function(callBack/*回调函数*/) {
		 
		 console.Debug('Test testTemplate Function....',module._name);   
         var view = {
				  "header": "模版技术",
				  "items": [
					  {"name": "baidu", "link": true, "url": "http://www.baidu.com"},
					  {"name": "sina", "link": true, "url": "http://www.sina.com.cn"},
					  {"name": "ceshi", "link": true, "url": "http://www.163.com"}
				  ],
				  "empty": false
		 };
         var template = $('#render_template').html();
         var html = mustache.to_html(template, view); 
         $('#render_template').html(html);
    };

    
	/**
	 * 将字符串转化为JSON对象转换成js中的对象
	 */
	toJSON = function(/*string*/jsonString) {
	    try {
			console.Debug('JSON String:'+jsonString,module._name);
			return eval('(' + jsonString + ')');
		} catch (ex) {
			return null;
		}
	};
	
    
    



});