/*
 * 出现在游戏中的所有元素的基类
 * 主要定义如何去绘图
 *
 */
function VisualGameObject() {

	/*
	 * 由当前对象显示的图像
	 * 
	 */
	this.image = null;

     
    /*对象的渲染处理*/
	this.draw = function(/**Number*/dt, /**CanvasRenderingContext2D*/context, /**Number*/xScroll, /**Number*/yScroll){
		//Debug('draw....','VisualGameObject');
		Debug('this.x:'+this.x+",this.y"+this.y,'VisualGameObject');
		context.drawImage(this.image, this.x - xScroll, this.y - yScroll);
	};
	
				
	/*初始化当前对象*/
	this.startUpVisualGameObject = function(image,x,y,deep){
	     this.image = image;
	     this.startupGameObject(x,y,deep);	
	     return this;
	};
	
	
	/* 清理当前对象*/
	this.shutdownVisualGameObject  = function(){
		this.shutdownGameObject();	
	}
	
	
	
}


/*继承*/
VisualGameObject.prototype = new GameObject;