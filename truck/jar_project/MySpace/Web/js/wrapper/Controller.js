console.debug("import taas._base.Controller!");
if (!dojo._hasResource["taas._base.Controller"]) {
	dojo._hasResource["taas._base.Controller"] = true;
	dojo.provide("taas._base.Controller");
	dojo.declare("taas._base.Controller", null, {});
	taas._base.Controller.remoteUpdate = function(dataModelUri, topic,
			requestContent) {
		_topic = topic;
		_dataModelUri = dataModelUri;
		_requestContent = requestContent;
		console.debug(_requestContent);
		var doResponse = function(responseText) {
			console.debug("请求URL后获返回的数据："+responseText);
			dojo.forEach(_topic, function(item) {
				    ///console.debug("item:"+item);
				    var jsonObj = dojo.fromJson(responseText)[item];
				    ///console.debug("jsonObj:"+dojo.toJson(jsonObj));
					dojo.publish(item, [dojo.toJson(jsonObj)]);
				});
		};
		var getFormJson = function() {
			dojo.xhrGet( {
				url : _dataModelUri,
				preventCache : true,
				content : _requestContent,
				handleAs : "text",
				method : "get",
				load : doResponse
			});
		};
		var getFromForm = function() {
			dojo.xhrGet( {
				url : _dataModelUri,
				preventCache : true,
				form : _requestContent,
				handleAs : "text",
				method : "get",
				load : doResponse
			});
		};
		var doRequest = function() {
			if (dojo.isObject(_requestContent)) {
				getFormJson();
			} else if (dojo.isString(_requestContent)) {
				getFromForm();
			}
		};
		doRequest();
	}
}