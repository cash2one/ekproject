if (!dojo._hasResource["elam.data.DataStore"]) {
	console.debug('使用elam.data.DataStore模块。');
	dojo._hasResource["elam.data.DataStore"] = true;
	dojo.provide("elam.data.DataStore");
	dojo.require("dojox.data.XmlStore");
	dojo.require("dojo.data.ItemFileReadStore");
	
    var _dataStore = null;
	dojo.declare("elam.data.DataStore", null, {
		_tableName:null,  //目标Id
		uri : null,  //数据源地址
		constructor : function(uri,tableId,dataType) {
			this.uri = uri;
			this._tableName = tableId;
			_dataStore = new dojo.data.ItemFileReadStore({
			      url: this.uri 
			});
			_dataStore.fetch({
			    onComplete:itemsLoadedT
		    });
		}
	});
}


 function itemsLoadedT(items,request){
	console.debug('elam.data.DataStore.itemsLoadedT!');
	var myTable = dojo.byId("myTestTable");
	if (myTable != null) {
		if (myTable.firstChild)
			myTable.removeChild(myTable.firstChild);

		var tableBody = dojo.doc.createElement("tbody");
		myTable.appendChild(tableBody);
		// 创建表头
		var tableHeader = dojo.doc.createElement("tr");
		tableBody.appendChild(tableHeader);
		
		if (items && items[0]) {
			var attributes = this._dataStore.getAttributes(items[0]);
			dojo.forEach(attributes, function(attr) {
				var th = dojo.doc.createElement("th");
				tableHeader.appendChild(th);
				th.appendChild(dojo.doc.createTextNode(attr));
			});
		}
		// 渲染表体
		var row = null;
		dojo.forEach(items, function(item) {
			// 渲染每一行
				row = dojo.doc.createElement("tr");
				tableBody.appendChild(row);
				var attributes = this._dataStore.getAttributes(item);
				dojo.forEach(attributes, function(attribute) {
					var td = dojo.doc.createElement("td");
					row.appendChild(td);
					td.appendChild(dojo.doc.createTextNode(this._dataStore
							.getValue(item, attribute)));
				});
			});
	}
}


      
 