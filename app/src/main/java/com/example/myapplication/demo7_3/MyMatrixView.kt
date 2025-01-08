package com.example.myapplication.demo7_3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R

class MyMatrixView(context: Context) : View(context) {
    //初始的图片资源
    private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.tianzhushan)

    //Matrix实例
    private val bmMatrix = Matrix()

    //设置倾斜度
    var sx = 0f

    //位图宽和高
    var bmWidth = bitmap.width
    var bmHeight = bitmap.height

    //缩放比例
    var scale = 0.1f

    //判断缩放还是旋转
    var isScale = false
    private var bitmap2: Bitmap

    init {
        bmMatrix.reset()
        if (!isScale) {
            //倾斜Matrix
            bmMatrix.setSkew(sx, 0f)
        } else {
            bmMatrix.setScale(scale, scale)
        }
        //根据原始位图和Matrix创建新图片
        bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bmWidth, bmHeight, bmMatrix, true)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //重置Matrix

        //绘制新位图
        canvas?.drawBitmap(bitmap2, bmMatrix, null)
    }
}