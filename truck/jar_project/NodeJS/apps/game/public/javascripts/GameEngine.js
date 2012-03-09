/*
 * 游戏引擎类
 * 负责与 Canvan 交互
 *
 */

function GameEngine() {

    /*管理所有的 对象*/
    var gameObjects = new Array();

    /*屏幕显示的画布*/
    var canvas = null;

    /*屏幕显示画布对应的上下文对象*/
    var context2D = null;

    /*缓存画布*/
    var backBuffer = null;

    /*缓存画布的上下文对象*/
    var backBufferContext2D = null;

     /*x轴的全局滚动值*/
    this.xScrol = 0;
    
    /*y轴的全局滚动值*/
    this.yScrol = 0;
    
    /*最后刷新时间*/
    var lastFrame = new Date().getTime();


    /*引擎初始化操作*/
    this.startupGameEngine = function() {
        Debug("startupGameEngine....",'GameEngine');
        canvas = document.createElement('canvas');
        context2D = canvas.getContext('2d');
        backBuffer = document.createElement('canvas');
        backBuffer.width = canvas.width;
        backBuffer.height = canvas.height;
        backBufferContext2D = backBuffer.getContext('2d');

        /*定时去进行渲染*/
        setInterval(this.draw,SECONDS_BETWEEN_FRAMES);
        Debug("startupGameEngine init finished!",'GameEngine');
    };
    
    /*向引擎中添加 游戏对象*/
    this.addGameObject = function(gameObject) {
        Debug("addGameObject...."+gameObject,'GameEngine');
        if(gameObject) {
            gameObjects.push(gameObject);

            /*对象图层排序*/
            gameObjects.sort(function(a, b) {
                return a.deep - b.deep;
            });
        }

    };
    
    
    /*移除某个游戏对象*/
    this.removeGameObject = function(gameObject) {
        gameObjects.removeObject(gameObject);
    };
    
    
    
    /*渲染图像*/
    this.draw = function() {
        
        
        Debug("draw....",'GameEngine');
        // 清理绘制上下文
        backBufferContext2D.clearRect(0, 0, backBuffer.width,backBuffer.height);
        context2D.clearRect(0, 0,canvas.width,canvas.height);

        /*更新对象状体*/
        for(var i in gameObjects ) {
            if(gameObjects[i].update)
                gameObjects[i].update();
        }

        /*更新对象状体*/
        for(var i in gameObjects ) {
            if(gameObjects[i].draw)
                gameObjects[i].draw();
        }

        // 将后台缓冲复制到当前显示的画布
        context2D.drawImage(backBuffer, 0, 0);
        Debug("draw finished.",'GameEngine');
        
        
    }
}