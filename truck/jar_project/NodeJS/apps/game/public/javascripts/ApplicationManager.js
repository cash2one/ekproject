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
	//	bounce1 = new Bounce().startUpBounce(g_image,20,20);
		//bounce2 = new Bounce().startUpBounce(g_image,150,150);
	 //   bounce3 = new Bounce().startUpBounce(g_image,300,300);
		
		this.startupGameObject();
		this.background3 = new RepeatingGameObject().startupRepeatingGameObject(g_back2, 0, 100, 3, 600, 320, 1);
        this.background2 = new RepeatingGameObject().startupRepeatingGameObject(g_back1, 0, 100, 2, 600, 320, 0.75);        
        this.background = new RepeatingGameObject().startupRepeatingGameObject(g_back0, 0, 0, 1, 600, 320, 0.5);
		
		 
		
		return this;	
	},
	
	/**
        Updates the object
        @param dt The time since the last frame in seconds
        @param context The drawing context 
        @param xScroll The global scrolling value of the x axis  
        @param yScroll The global scrolling value of the y axis 
    */
    this.update = function(/**Number*/ dt, /**CanvasRenderingContext2D*/ context, /**Number*/ xScroll, /**Number*/ yScroll)
    {
		g_GameObjectManager.xScroll += 50 * dt;
	}
}
ApplicationManager.prototype = new GameObject