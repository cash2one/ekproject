/*
*  Demo 程序
*
*/
//全局变量
var backgroundForestImg = new Image();
//森林背景图
var mushroomImg = new Image();
//蘑菇
var ctx;
//2d画布
var screenWidth;
//画布宽度
var screenHeight;
//画布高度

//公用 定义一个游戏物体戏对象
function GameObject() {
	this.x = 0;
	this.y = 0;
	this.image = null;
}

//定义蘑菇Mushroom 继承游戏对象GameObject
function Mushroom() {
};

Mushroom.prototype = new GameObject();
//游戏对象GameObject

//蘑菇实例
var mushroom = new Mushroom();
//循环描绘物体
function gameLoop() {
	//清除屏幕
	ctx.clearRect(0, 0, screenWidth, screenHeight);
	ctx.save();
	//绘制背景
	ctx.drawImage(backgroundForestImg, 0, 0);
	//绘制蘑菇
	ctx.drawImage(mushroom.image, mushroom.x, mushroom.y);
	ctx.restore();
}


//加载图片
function loadImages() {
	mushroomImg.src = "images/mushroom.png";
	//蘑菇
	backgroundForestImg.src = "images/forest1.jpg";
	//森林背景图
}



//初始化
$(window).ready(function() {
	loadImages();
	ctx = document.getElementById('canvas').getContext('2d');
	//获取2d画布
	screenWidth = parseInt($("#canvas").attr("width"));
	//画布宽度
	screenHeight = parseInt($("#canvas").attr("height"));
	mushroom.image = mushroomImg;
	mushroom.x = parseInt(screenWidth / 2);
	// 蘑菇X坐标
	mushroom.y = screenHeight - 40;
	//蘑菇Y坐标
	setInterval(gameLoop, 10);
});
