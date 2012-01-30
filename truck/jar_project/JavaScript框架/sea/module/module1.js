//module1.js
define(function(require, exports, module) {
    
	var $ = require('../base/jquery');
    var m2 = require('./module2');
    var m3 = require('./module3');
     
    exports.run = function() {
    	
        $.each(['test_1','test_2'],function(data){
    		 $('.tul').append('<li>'+this+'</li>');
    	});
    	
        return  $('.orl_content').html()+ m2.run();    
    }


});