if (!dojo._hasResource["elam._base.View"]) {
	dojo._hasResource["elam._base.View"] = true;
	dojo.require("dijit._Widget");
	dojo.require("dijit._Templated");
	dojo.provide("elam._base.View");
	dojo.declare("elam._base.View", null, {
		_model : null,
		topic : "",
		_taasSrcPath : dojo.moduleUrl("elam", ""),
		responseObject : null,
		refresh : function(object) {
			this.responseObject = object;
			if (this.updateView != undefined
					&& typeof this.updateView == "function") {
				this.updateView(this.responseObject);
			}
		},
		bindModel : function(dataModel) {
			this._model = dataModel;
		},
		_bindTopic : function() {
			if (this.topic != undefined && this.topic != "") {
				dojo.subscribe(this.topic, this, "refresh");
			}
		}
	});
}