/**
 * 游戏对象  Bounce
 * 定义 动作，更新的模式
 * 物体的运动方式
 *
 */
function Bounce() {

	this.deep = 1;

	/** x轴的运动方向*/
	this.xDirection = 1;

	/*y轴的运动方向*/
	this.yDirection = 1;

	/*运动速度*/
	this.speed = 10;

	/*对象初始化*/
	this.startUpBounce = function(image,x,y){
		this.startUpVisualGameObject(image,x,y,1);
		return this;
	};
	/*
	 *定义更新的动作
	 */
	this.update = function(/**Number*/dt, /**CanvasRenderingContext2D*/context, /**Number*/xScroll, /**Number*/yScroll) {
		this.x += dt * this.speed * this.xDirection;
		this.y += dt * this.speed * this.yDirection;
		
		if (this.x >= 450)
		{
			this.x = 450;
			this.xDirection = -1;
		}
		else if (this.x <= 0)
		{
			this.x = 0;
			this.xDirection = 1;
		}
		
		if (this.y >= 250)
		{
			this.y = 250;
			this.yDirection = -1;
		}
		else if (this.y <= 0)
		{
			this.y = 0;
			this.yDirection = 1;
		}
	}
}

/*此元素应该继承 此组件*/
Bounce.prototype = new VisualGameObject;
