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
	module._name = "dataGrid";


    /* 分页属性 */
	var PageModel={
	     "currentPage:":1,
         "totalPage:":0,
	     "pageSize:":20,
	     "allRecords:":0,
         "items":''
	};


    //记录模版信息
    var _divGrids = {};
     

    /*初始化grid*/
    exports.init = function(divGrid/**/,columns/*列定义*/){
	        //根据信息，生成对应的模版（列表）,模版加工操作
		 
		    _divGrids.divGrid = {}; //存储某个模版的信息
            _divGrids.divGrid.view = columns;  //存储改模版的定义列
			var reqTime = "?reqTemp="+new Date().getTime();

		    //加载基础模版
			$.get("../template/dataGrid.htm"+reqTime,function(template){
		  		
				console.Debug("加载模版..."+template,module._name);   
				//生成模版的外观
                loadFace(divGrid,template);
                //加载数据
                renderData(divGrid);

			});

	};


    /*加载Grid外观*/
	loadFace = function(divGrid,template){
	  
	            var view  = _divGrids.divGrid.view;

                //加载grid的外观
				template = mustache.to_html(template, view);
                ///console.Debug("模版初始化第一次处理后..."+template,module._name);
				template = template.replace(/\[/g,"{{");
	            template = template.replace(/\]/g,'}}');
			    //预先把样式加载呈现，再进一步把数据加载
                $('#'+divGrid).html(mustache.to_html(template, view));
				console.Debug("模版初始化第二次处理后[loadFace]..."+template,module._name);
				
				//记录模版
				_divGrids.divGrid.gridTemplate = template;
	};


    /*显示数据内容*/
   	renderData = function(divGrid){
  
		   	var data ={
						"items": [
							{"id": "001","name": "百度", "link": true, "url": "http://www.baidu.com"},
							{"id": "002","name": "新浪", "link": true, "url": "http://www.sina.com.cn"},
							{"id": "003","name": "网易", "link": true, "url": "http://www.163.com"}
						]
					 };
           var template = _divGrids.divGrid.gridTemplate;
		   var html = mustache.to_html(template,data);
		   $('#'+divGrid).html(html);
		   console.Debug("最终合并后的结果"+html,module._name);	    
	};

     

    


    /*DataGrid Render */
    exports.render = function(formId/*from Id*/,url/*requestUrl*/,params/* request params*/,callBack/*回调函数*/) {
	   
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