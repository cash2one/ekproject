function ajax(){};

ajax.prototype={
    
	btnId:null,

    req:function(callBack){

         this.httpload(function(){
			 var result = ' req finished。。。';
		     callBack(result);
		 });


	},

	httpload:function(callBack){
	   // alert('ajax.test run');
         callBack();
	}

}

 
var btnObj = function(){};


btnObj.prototype = {
    
	btnId : null,
  
	action : function(){
	    var a=new ajax();
	    a.req(this.callBack);
	},

	callBack : function(data){
	   alert('btnObj callBack run '+data);
	}

}
