if (!dojo._hasResource["elam._base.DataModel"]) {
	dojo._hasResource["elam._base.DataModel"] = true;
	dojo.provide("elam._base.DataModel");
	dojo.declare("elam._base.DataModel", null, {
		_views : null,
		uri : null,
		constructor : function(uri) {
			this._views = new Array();
			this.uri = uri;
		},
		registerView : function(view) {
			view.bindModel(this);
			this._views.push(view);
		},
		unRegisterView : function(view) {
			var i = this._views.indexOf(view);
			if (i > 0)
				this._views.slice(i, 1);
		},
		notifyViews : function(json) {
			for ( var i = 0; i < this._views.length; i++) {
				this._views[i].refresh(json);
			}
		}
	});
}