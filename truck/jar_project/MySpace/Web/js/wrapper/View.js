if (!dojo._hasResource["taas._base.View"]) {
	dojo._hasResource["taas._base.View"] = true;
	dojo.require("dijit._Widget");
	dojo.require("dijit._Templated");
	dojo.provide("taas._base.View");
	dojo.declare("taas._base.View", null, {
		_model : null,
		topic : "",
		_taasSrcPath : dojo.moduleUrl("taas", ""),
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