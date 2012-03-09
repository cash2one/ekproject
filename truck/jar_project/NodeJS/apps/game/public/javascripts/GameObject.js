/*
 *  GameObject 游戏引擎中的实例对象
 *
 */

function GameObject() {

    /*显示的图层，越小代表 越早输出到屏幕上*/
    this.deep = 1;

    /*显示图片*/
    this.image = "demo";

    /*X坐标*/
    this.xRoll = 0;

    /*Y坐标*/
    this.yRoll = 0;

    this.update = function() {
        Debug("test:  " + this.image + " " + this.deep,'GameObject');
    };

    this.draw = function() {

    }
};

/*  */
GameObject.prototype = {
    test2 : function() {
        alert("test2:  " + this.image);
    }
}