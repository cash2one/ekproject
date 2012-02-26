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

	var validate = require('validate');
	var CONSOLE = require('console');
     

	/**
	 * 将指定form节点的所有可提交元素组合成字符串返回
	 */
	exports._getFormValues =  function(/*string*/formId) {
		var formNode = $('#' + formId)[0];
		if ((!formNode) || (!formNode.tagName) || (!formNode.tagName.toLowerCase() == "form")) {
			alert('请指定一个正确的form节点！');
			return null;
		}
		var values = [];
		for (var i = 0; i < formNode.elements.length; i++) {
			var elm = formNode.elements[i];
			if (!elm || elm.tagName.toLowerCase() == "fieldset" || !this._formFilter(elm)) {
				continue;
			}
			var name = elm.name;
			var type = elm.type.toLowerCase();
			if (type == "select-multiple") {
				CONSOLE.Debug("base._getFormValues[ select-multiple 控件 ]:options.length:"+elm.options.length);
				for (var j = 0; j < elm.options.length; j++) {
					if (elm.options[j].selected) {
						values.push(name + "=" + elm.options[j].value);
					}
				}
			} else if(type == "textarea"){
				values.push(name + "=" + $('#'+elm.id).val());
			} else {
				CONSOLE.Debug("base._getFormValues[ radio, checkbox 控件 ]");
				if ($.inArray(type, ["radio", "checkbox"]) > -1) {
					if (elm.checked) {
						values.push(name + "=" + elm.value);
					}
				} else {
					values.push(name + "=" + elm.value);
				}
			}
			
		}
		var inputs = $("input");
		for (var i = 0; i < inputs.length; i++) {
			var input = inputs[i];
			if (input.type.toLowerCase() == "image" && input.form == formNode && this._formFilter(input)) {
				var name = input.name;
				values.push(name + "=" + input.value);
				values.push(name + ".x=0");
				values.push(name + ".y=0");
			}
		}
		
		return values.join("&");
	};



    /**
	 * 表单可提交元素过滤器.
	 */
	_formFilter = function(/*object*/node) {
		var type = (node.type || "").toLowerCase();
		return !node.disabled && node.name && !($.inArray(type, ["file", "submit", "image", "reset", "button"]) > -1);
	},




});