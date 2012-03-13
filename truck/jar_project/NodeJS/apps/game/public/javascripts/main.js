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


var g_back0 = new Image();
g_back0.src = "images/b0.png";
/** An image to be used by the application  
    @type Image
*/
var g_back1 = new Image();
g_back1.src = "images/b1.png";
/** An image to be used by the application  
    @type Image
*/
var g_back2 = new Image();
g_back2.src = "images/b2.png";

var DEBUG = true;

function start(){
	
    /*实例化引擎*/
    g_GameObjectManager = new GameObjectsManager();
    g_GameObjectManager.startupGameObjectsManager();
    
          
}


function Debug(msg,handle){
    if(DEBUG){
      console.log(msg+"  "+handle);
      ////$('#msg').append("<li>"+msg+"  "+handle+"</li>")
    }
}

window.onload = start;
