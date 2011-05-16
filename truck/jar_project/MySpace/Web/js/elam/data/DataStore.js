if (!dojo._hasResource["elam.data.DataStore"]) {
	console.debug('使用elam.data.DataStore模块。');
	dojo._hasResource["elam.data.DataStore"] = true;
	dojo.provide("elam.data.DataStore");
	dojo.require("dojox.data.XmlStore");
	dojo.require("dojo.data.ItemFileReadStore");
	
	dojo.declare("elam.data.DataStore", null, {
		_dataStore:null,  //数据源
		_tableName:null,  //目标Id
		uri : null,  //数据源地址
		constructor : function(uri,tableId,dataType) {
			this.uri = uri;
			this._tableName = tableId;
			this._dataStore = new dojo.data.ItemFileReadStore({
			      url: this.uri 
			});
			this._dataStore.fetch({
			    onComplete:this.itemsLoadedT
		    });
		},
		itemsLoadedT:function(items,request){
			   console.debug('elam.data.DataStore.itemsLoadedT.items!'+items);
				var myTable = dojo.byId("myTestTable");
				if (myTable != null) {
					if (myTable.firstChild)
						myTable.removeChild(myTable.firstChild);

					var tableBody = dojo.doc.createElement("tbody");
					myTable.appendChild(tableBody);
					// 创建表头
					var tableHeader = dojo.doc.createElement("tr");
					tableBody.appendChild(tableHeader);
					console.debug('this._dataStore:'+this._dataStore);
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
		    },
	});
}


function showTableDataStore(dataUrl,tableId,type){ 
    console.debug('showTableDataStore!');
   
    var _dataStore;
    if(type=='xml'){
		_dataStore = new dojox.data.XmlStore({
 			url: dataUrl 
        }); 
    }else{
    	_dataStore = new dojo.data.ItemFileReadStore({
		      url: dataUrl 
		});
    }
	// 数据加载完毕，渲染表格或者更新
	var itemsLoaded = function(items,request){
			 var myTable = dojo.byId(tableId);
			 if (myTable != null) {
				 if (myTable.firstChild) 
					 myTable.removeChild(myTable.firstChild);
				 
				 var tableBody = dojo.doc.createElement("tbody");
				 myTable.appendChild(tableBody); 
										// 创建表头
				 var tableHeader = dojo.doc.createElement("tr");
				 tableBody.appendChild(tableHeader);
				 if (items && items[0]) {
					 var attributes = _dataStore.getAttributes(items[0]);
					 dojo.forEach(attributes, function(attr){
						 var th = dojo.doc.createElement("th");
						 tableHeader.appendChild(th);
						 th.appendChild(dojo.doc.createTextNode(attr));
					 });
				 }
			
				 // 渲染表体
				 var row = null;
				 dojo.forEach(items, function(item){
					 // 渲染每一行
					 row = dojo.doc.createElement("tr");
					 tableBody.appendChild(row);
					 var attributes = _dataStore.getAttributes(item);
					 dojo.forEach(attributes, function(attribute){
						 var td = dojo.doc.createElement("td");
						 row.appendChild(td);
						 td.appendChild(dojo.doc.createTextNode(
						_dataStore.getValue(item, attribute))) ;
					 });
				 });
			 }
			 
	};
    // 通过 fetch 方法获取数据
	_dataStore.fetch({
		    onComplete: itemsLoaded
	});
}




      
 