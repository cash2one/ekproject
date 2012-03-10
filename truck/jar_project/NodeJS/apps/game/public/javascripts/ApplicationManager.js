/**
 ApplicationManager用于管理应用
 @class
 */

function ApplicationManager() {

	/**
	 初始化对象
	 @return 对初始化对象的引用
	*/
	this.startupApplicationManager = function() {
		Debug("startupApplicationManager....",'ApplicationManager');
		//创建运行对象，游戏对象
		this.bounce = new Bounce().startUpBounce(g_image);
		return this;	
	}
}