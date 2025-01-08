package com.example.myapplication.demo7_3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.MotionEvent
import android.view.View
import kotlin.math.sqrt

class MeshView : View {
    companion object {
        private const val WIDTH = 40
        private const val HEIGHT = 40
        private const val COUNT = (WIDTH + 1) * (HEIGHT + 1)
        private val verts = FloatArray(COUNT * 2)
        private val orig = FloatArray(COUNT * 2)

    }

    private var bitmap: Bitmap

    constructor(context: Context?, drawableId: Int) : super(context) {

        bitmap = BitmapFactory.decodeResource(resources, drawableId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable = FOCUSABLE_AUTO
        }
        val bitmapWidth = bitmap.width *2
        val bitmapHeight = bitmap.height *2
        var index = 0
        for (y in 0..HEIGHT) {
            val fy = bitmapHeight * y / HEIGHT
            for (x in 0..WIDTH) {
                val fx = bitmapWidth * x / WIDTH
                verts[index * 2] = fx.toFloat()
                orig[index * 2] = verts[index * 2]
                verts[index * 2 + 1] = fy.toFloat()
                orig[index * 2 + 1] = verts[index * 2 + 1]
                index += 1
            }
        }
        setBackgroundColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            warp(event.x, event.y)
        }

        return true
    }

    private fun warp(cx: Float, cy: Float) {
        for (i in 0 until COUNT * 2 step 2) {
            val dx = cx - orig[i]
            val dy = cy - orig[i + 1]
            val dd = dx * dx + dy * dy
            val d = sqrt(dd.toDouble())
            val pull = 200000 / (dd * d)
            if (pull >= 1) {
                verts[i] = cx
                verts[i + 1] = cy
            } else {
                verts[i] = (orig[i] + dx * pull).toFloat()
                verts[i + 1] = (orig[i + 1] + dy * pull).toFloat()
            }
        }
        invalidate()
    }


}