  // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
     
	  
      var  webAgentName = "WebAgent";
      var  targetDivComp = "flashContent";
	  

function inintWebAgent(){
	 
	  var swfVersionStr = "10.2.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
       var xiSwfUrlStr = "playerProductInstall.swf";
       var flashvars = {};
       var params = {};
       params.quality = "high";
	   params.wmode ="transparent";
       params.bgcolor = "#ffffff";
       params.allowscriptaccess = "sameDomain";
       params.allowfullscreen = "true";

       var attributes = {};
       attributes.id = "WebAgent";
       attributes.name ="WebAgent";
       attributes.align = "middle";
       swfobject.embedSWF(
       webAgentName+".swf",targetDivComp, 
       "160px", "170px", 
       swfVersionStr, xiSwfUrlStr, 
       flashvars, params, attributes);
       // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
       swfobject.createCSS("#flashContent", "display:block;text-align:left;");
}

 


/*实现DIV拖拽效果*/
function fDragging(obj, e){
	  
			 if(!e)
			    e=window.event;
			 
			 var x=parseInt(obj.style.left);
			 var y=parseInt(obj.style.top);
			 var x_=e.clientX-x;
			 var y_=e.clientY-y;
			 
			 if(document.addEventListener){
			    document.addEventListener('mousemove', inFmove, true);
			    document.addEventListener('mouseup', inFup, true); 
			 }else if(document.attachEvent){
			    document.attachEvent('onmousemove', inFmove);
			    document.attachEvent('onmouseup', inFup);
			 }

			 inFstop(e);
			 inFabort(e)
			 
			 function inFmove(e){
			  var evt;
			  if(!e)
			    e=window.event;
			  obj.style.left=e.clientX-x_+'px';
			  obj.style.top=e.clientY-y_+'px';
			  inFstop(e);
			 }

			 // shawl.qiu script 
			 function inFup(e){
				  var evt;
				  if(!e)
				     e=window.event;
				  if(document.removeEventListener){
				     document.removeEventListener('mousemove', inFmove, true);
				     document.removeEventListener('mouseup', inFup, true);
				  }else if(document.detachEvent){
				     document.detachEvent('onmousemove', inFmove);
				     document.detachEvent('onmouseup', inFup);
				  }
				  inFstop(e);
			 }
			 // shawl.qiu script
			 
			 function inFstop(e){
	  		     if(e.stopPropagation) 
			        return e.stopPropagation();
			     else
				  return e.cancelBubble=true;
			 } 
			 
			 // shawl.qiu script
			 function inFabort(e){
				  if(e.preventDefault) 
					 return e.preventDefault();
				  else
					return e.returnValue=false;
			 }
    // shawl.qiu script
}


