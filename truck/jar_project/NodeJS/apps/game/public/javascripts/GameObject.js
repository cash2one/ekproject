/*
 *  GameObject 游戏引擎中的实例对象
 *
 */

function GameObject() {

	/*显示的图层，越小代表 越早输出到屏幕上*/
	this.deep = 1;

	/*x轴的坐标*/
	this.x = 0;

	/*Y坐标*/
	this.y = 0;


	this.startupGameObject = function(/**Number*/x, /**Number*/y, /**Number*/deep) {
		this.deep = deep;
		this.x = x;
		this.y = y;
		Debug('startupGameObject x:'+x+",y"+y,'GameObject');
		Debug('startupGameObject this.x:'+this.x+",this.y"+this.y,'GameObject');
		g_GameObjectManager.addGameObject(this);
		return this;

	}
	
	/* 
	 *清理当前对象，将其从GameObjectManager维护的对象列表中删除
	 */
	this.shutdownGameObject = function() {
		g_GameObjectManager.removeGameObject(this);
	}
	
};

/* 扩展方法  */
GameObject.prototype = {
	test2 : function() {
		Debug("test2:  " + this.image);
	}
}