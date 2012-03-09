/**
 *
 *每秒多少帧
 */
var FPS = 15;

/** 两帧间间隔的秒数
 */
var SECONDS_BETWEEN_FRAMES = 1 / FPS;

/** GameObjectManager 实例的全局引用
 */
var g_GameObjectManager = null;

var DEBUG = true;

function start(){
    
    //g_GameObjectManager = new GameEngine();
    //g_GameObjectManager.startupGameEngine();
          
}


function Debug(msg,handle){
    if(DEBUG)
      console.log(msg+"  "+handle);
}

window.onload = start;
