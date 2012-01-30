/**
 * 
 * 验证器
 * Ethan Lam 2012-01-12
 * @return
 * 
 */

function Validator() {};
Validator.prototype = {

    validate:function(){
	    var validatorMsg = "";
		///Debug("autoMacthValidateHandle found:"+$("input[validate]").length);
        var allRight = true;

        this.removeErrTip();//删除上次生成的验证信息

		$("input[validate]").each(function(index){
	    	  var validates = $(this).attr("validate");
			  var data = $.trim($(this).val());
			  var inputObj  = $(this)[0];
			 
			  $.each(validates.split(/\s/),function(index){  
				  validate = this;
                  if('required'==validate || data!=''){
					  validatorMsg = validator.runInputCheckValidate(inputObj,validate,data);
					  if($.trim(validatorMsg)!=''){
						  Debug("validatorMsg:"+validatorMsg);
						  validator.appendErrMsgAfterInput(inputObj,validatorMsg);
						  allRight = false;
					  }
                  }
			  });

			 

		});

		return allRight;
    },


    /**
	* 执行对应的验证器
	*/
    runInputCheckValidate:function(element,validate,data){
	   //Debug("runInputCheckValidate:"+validate);
	    if(this.Methods[validate]){
			//验证不通过的返回对应的提示信息
		   msg = this.Methods[validate].call(this,data,element);
		   return (msg==0&&this.Message[validate])?this.Message[validate]:(msg!=1?msg:'');
		}
	},
 
    
	/**
	* 删除所有的提示信息框 
	*/
    removeErrTip:function(){
	    $("span[class='validateMsg']").remove();  
	    $("div[class='validateTip']").remove();
	},


   /**
	* 在对应的控件后面显示对应的验证结果
	*/
    appendErrMsgAfterInput:function(element,msg){
      $(element).parent().after("<div class='validateTip' ><span  class='validateMsg'>"+msg+"</span></div>");	 
	},


	/**
	* 具体验证器
    */
    Methods:{
    	
    	    /**
    	     * 不能为空
    	     */
		    required:function(data,element){

                if($.trim(data)=='')
					return 0;

                length = data.length;
				if($(element).attr("minLength")&&length<$(element).attr("minLength"))
					return "长度不能小于"+$(element).attr("minLength");
				if($(element).attr("maxLength")!=-1&&length>$(element).attr("maxLength"))
					return "长度不能大于"+$(element).attr("maxLength");
				return 1;

		    },
		    
		    /**
		     * 只能输入数字
		     */
		    number:function(data,element){

				 if(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(data)){
					//假如输入的是数字
                    if($(element).attr("min")&&parseInt(data)<parseInt($(element).attr("min")))
					   return "不能小于"+$(element).attr("min");
				    if($(element).attr("max")&&parseInt(data)>parseInt($(element).attr("max")))
					   return "不能大于"+$(element).attr("max");
					return 1;
				 }
				 return 0;

		    },
		    
		    /**
		     * 检查邮箱地址是否正确
		     */
		    email:function(data,element){
		    	
                if(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(data))
		    	   return 1;
				else
                   return 0;

		    },
		    
		    /**
		     * 检查输入的号码是否正常
		     */
		    phone:function(data,element){
                if(data.length==11&&/^(135|137|136|138|189)\d{8}/.test(data)){
		    	   return 1;
				}else
				   return 0;
		    }
		    
    },
		
   /**
	* 具体验证器提示信息
    */
	Message:{
	     required:"此处不能为空，必填信息",
         number:"非法的数字",
	     email:"非法的邮件地址",
         phone:"非法的手机号码"
	}
		

	
		
}

function Debug(message){
	   if($.browser.mozilla)
	      console.log("[DEBUG]:"+message);
}

//生成validator类实例
var validator = new Validator();