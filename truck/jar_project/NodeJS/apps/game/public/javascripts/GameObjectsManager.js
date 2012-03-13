/*
 * 游戏引擎类
 * 负责与 Canvan 交互
 *
 */

function GameObjectsManager() {

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
	this.xScroll = 0;

	/*y轴的全局滚动值*/
	this.yScroll = 0;

	/*最后刷新时间*/
	var lastFrame = null;

	var applicationManager = null;

	/*引擎初始化操作*/
	this.startupGameObjectsManager = function() {
		//g_GameObjectManager = this;

		Debug("startupGameEngine....", 'GameEngine');
		canvas = document.getElementById('canvas');
		context2D = canvas.getContext('2d');
		backBuffer = document.createElement('canvas');
		backBuffer.width = canvas.width;
		backBuffer.height = canvas.height;
		backBufferContext2D = backBuffer.getContext('2d');
		lastFrame = new Date().getTime();

		/*装载游戏对象*/
		applicationManager = new ApplicationManager().startupApplicationManager();

		/*定时去进行渲染*/
		setInterval(function() {
			g_GameObjectManager.draw();
		}, SECONDS_BETWEEN_FRAMES);
		// Debug("startupGameEngine init finished!",'GameEngine');

		//setTimeout(this.draw,5000);
	};
	/*向引擎中添加 游戏对象*/
	this.addGameObject = function(gameObject) {
		Debug("addGameObject...." + gameObject, 'GameEngine');
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

		Debug("draw....", 'GameEngine');

		// 计算从上一帧到现在的时间
		var thisFrame = new Date().getTime();
		var dt = (thisFrame == lastFrame) ? 0.1 : (thisFrame - lastFrame) / 1000;
		lastFrame = thisFrame;

		// 清理绘制上下文
		backBufferContext2D.clearRect(0, 0, backBuffer.width, backBuffer.height);
		context2D.clearRect(0, 0, canvas.width, canvas.height);

		/*更新对象状体*/
		for(var i in gameObjects ) {
			if(gameObjects[i].update)
				gameObjects[i].update(dt, backBufferContext2D, this.xScroll, this.yScroll);
		}

		/*更新对象状体*/
		for(var i in gameObjects ) {
			if(gameObjects[i].draw)
				gameObjects[i].draw(dt, backBufferContext2D, this.xScroll, this.yScroll);
		}

		// 将后台缓冲复制到当前显示的画布
		context2D.drawImage(backBuffer, 0, 0);
		Debug("draw finished.", 'GameEngine');

	}
}