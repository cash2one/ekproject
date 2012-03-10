/**
 *
 *每秒多少帧
 */
var FPS = 30;


/** 两帧间间隔的秒数
 */
var SECONDS_BETWEEN_FRAMES = 1 / FPS;


/** GameObjectManager 实例的全局引用
 */
var g_GameObjectManager = null;


var g_image = new Image();
g_image.src = "images/demo.jpg";


var DEBUG = true;

function start(){
	
    /*实例化引擎*/
    g_GameObjectManager = new GameEngine();
    g_GameObjectManager.startupGameEngine();
    
          
}


function Debug(msg,handle){
    if(DEBUG){
      console.log(msg+"  "+handle);
      ////$('#msg').append("<li>"+msg+"  "+handle+"</li>")
    }
}

window.onload = start;
