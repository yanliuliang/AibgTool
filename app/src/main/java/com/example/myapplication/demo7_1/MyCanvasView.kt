package com.example.myapplication.demo7_1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MyCanvasView : View {
    private val path1 = Path()
    private val path2 = Path()
    private val path3 = Path()
    private val path4 = Path()
    private val path5 = Path()
    private val path6 = Path()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val mShader = LinearGradient(
        0f,
        0f,
        40f,
        60f,
        intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW), null, Shader.TileMode.REPEAT
    )

    //定义画笔
    private val paint = Paint()
    private val rect = RectF()

    //重写方法。进行绘图
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //把整张布绘制成白色
        canvas?.drawColor(Color.WHITE)
        //去锯齿
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        val viewWidth = this.width.toFloat()


        canvas?.apply {
            //绘制圆形
            drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10, viewWidth / 10 + 10, paint)
            //绘制正方形
            drawRect(10f, viewWidth / 5 + 20, viewWidth / 5 + 10, viewWidth * 2 / 5 + 20, paint)
            //绘制矩形
            drawRect(10f, viewWidth * 2 / 5 + 30, viewWidth / 5 + 10, viewWidth / 2 + 30, paint)
            rect.set(10f, viewWidth / 2 + 40, 10 + viewWidth / 5, viewWidth * 3 / 5 + 40)
//绘制圆角矩形
            canvas.drawRoundRect(rect, 15f, 15f, paint)
            rect.set(
                10f,
                viewWidth * 3 / 5 + 50,
                10 + viewWidth / 5,
                viewWidth * 7 / 10 + 50
            )//绘制圆
            canvas.drawOval(rect, paint)//使用一个Path对象封闭成一个三角形
            path1.moveTo(10f, viewWidth * 9 / 10 + 60)
            path1.lineTo(viewWidth / 5 + 10, viewWidth * 9 / 10 + 60)
            path1.lineTo(
                viewWidth / 10 + 10,
                viewWidth * 7 / 10 + 60
            )
            path1.close()//根据 Path进行绘制,绘制三角形
            canvas.drawPath(path1, paint)
//使用一个Path对象封闭成一个五边形
            path2.moveTo(
                10 + viewWidth / 15,
                viewWidth * 9 / 10 + 70
            )
            path2.lineTo(10 + viewWidth * 2 / 15, viewWidth * 9 / 10 + 70)
            path2.lineTo(
                10 + viewWidth / 5,
                viewWidth + 70
            )
            path2.lineTo(10 + viewWidth / 10, viewWidth * 11 / 10 + 70)
            path2.lineTo(
                10f, viewWidth + 70
            )
            path2.close()
//根据Path进行绘制,绘制五边形
            canvas.drawPath(path2, paint)//----------设置填充风格后绘制--
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
//绘制圆形
            canvas.drawCircle(
                viewWidth * 3 / 10 + 20, viewWidth / 10 + 10, viewWidth / 10, paint
            )
//绘制正方形
            canvas.drawRect(
                viewWidth / 5 + 20,
                viewWidth / 5 + 20,
                viewWidth * 2 / 5 + 20,
                viewWidth * 2 / 5 + 20,
                paint
            )
        }
    }

}