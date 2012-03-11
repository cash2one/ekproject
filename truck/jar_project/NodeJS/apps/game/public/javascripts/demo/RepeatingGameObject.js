/**
 这个类可以重复显示纹理图像，支持纹理图像在x轴或y轴偏移
 @class
 */
function RepeatingGameObject() {
	/** 最终图像占据的宽度
	 @type Number
	 */
	this.width = 0;
	/** 最终图像占据的高度
	 @type Number
	 */
	this.height = 0;
	/** 绘制时应用多少scrollX和scrollY
	 @type Number
	 */
	this.scrollFactor = 1;

	/**
	 初始化对象
	 @return 对初始化对象的引用
	 */
	this.startupRepeatingGameObject = function(image, x, y, z, width, height, scrollFactor) {
		this.startupVisualGameObject(image, x, y, z);
		this.width = width;
		this.height = height;
		this.scrollFactor = scrollFactor;
		return this;
	}
	/**
	 清理对象
	 */
	this.shutdownstartupRepeatingGameObject = function() {
		this.shutdownVisualGameObject();
	}
	/**
	 把当前元素绘制到后台缓冲
	 @param dt 自上一帧绘制起经过的秒数
	 @param context 绘制上下文
	 @param xScroll x轴的全局滚动值
	 @param yScroll y轴的全局滚动值

	 */
	this.draw = function(dt, canvas, xScroll, yScroll) {
		var areaDrawn = [0, 0];

		for(var y = 0; y < this.height; y += areaDrawn[1]) {
			for(var x = 0; x < this.width; x += areaDrawn[0]) {
				// 绘制下一张贴片左上角的点
				var newPosition = [this.x + x, this.y + y];
				// 剩余的绘制空间
				var newFillArea = [this.width - x, this.height - y];
				// 第一次必须从图像的中央开始绘制
				// 后续贴片从上方或左侧绘制
				var newScrollPosition = [0, 0];
				if(x == 0)
					newScrollPosition[0] = xScroll * this.scrollFactor;
				if(y == 0)
					newScrollPosition[1] = yScroll * this.scrollFactor;
				areaDrawn = this.drawRepeat(canvas, newPosition, newFillArea, newScrollPosition);
			}
		}
	}

	this.drawRepeat = function(canvas, newPosition, newFillArea, newScrollPosition) {
		// 找到重复绘制纹理图像的起点（左上角）
		var xOffset = Math.abs(newScrollPosition[0]) % this.image.width;
		var yOffset = Math.abs(newScrollPosition[1]) % this.image.height;
		var left = newScrollPosition[0] < 0 ? this.image.width - xOffset : xOffset;
		var top = newScrollPosition[1] < 0 ? this.image.height - yOffset : yOffset;
		var width = newFillArea[0] < this.image.width - left ? newFillArea[0] : this.image.width - left;
		var height = newFillArea[1] < this.image.height - top ? newFillArea[1] : this.image.height - top;

		// 绘制图像
		canvas.drawImage(this.image, left, top, width, height, newPosition[0], newPosition[1], width, height);

		return [width, height];
	}
}

RepeatingGameObject.prototype = new VisualGameObject();
