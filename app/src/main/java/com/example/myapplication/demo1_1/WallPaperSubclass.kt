package com.example.myapplication.demo1_1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myapplication.R
import kotlin.random.Random

class WallPaperSubclass : WallpaperService() {
    //记录用户触碰点的位图
    private var heart: Bitmap? = null

    override fun onCreateEngine(): Engine {
        heart = BitmapFactory.decodeResource(resources, R.drawable.heart)
        return MyEngine()
    }

    inner class MyEngine : Engine() {
        //记录程序界面是否可见
        private var mVisible: Boolean = true

        //记录当前用户动作事件的发生位置
        private var mTouchX = -1f
        private var mTouchY = -1f

        //记录当前需要绘制的矩形耳朵数量
        private var count = 1

        //记录绘制第一个矩形所需坐标变换的X，Y坐标的偏移
        private var originX = 0f
        private var originY = 100f
        private var cubeHeight = 0f
        private var cubeWidth = 0f

        //定义画笔
        private val mPaint = Paint()

        //定义一个handler
        private var mHandle = Handler()

        //用于记录屏幕大小
        private var dis = DisplayMetrics()

        //定义一个周期性执行的任务
        private val drawTarget = this::drawFrame


        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            //初始化画笔
            mPaint.setARGB(76, 0, 0, 255)
            mPaint.isAntiAlias = true
            mPaint.style = Paint.Style.FILL
            //根据屏幕宽度设置动态壁纸的起点位置的x坐标
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(dis)
            originX = dis.widthPixels / 3f
            originY = dis.heightPixels / 10f
            cubeHeight = dis.widthPixels / 4f
            cubeWidth = dis.widthPixels / 8f
            setTouchEventsEnabled(true)
        }

        override fun onDestroy() {
            super.onDestroy()
            mHandle.removeCallbacks(drawTarget)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            mVisible = visible
            if (visible) {
                drawFrame()
            } else {
                mHandle.removeCallbacks(drawTarget)
            }
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            drawFrame()

        }

        override fun onTouchEvent(event: MotionEvent?) {
            if (event?.action == MotionEvent.ACTION_MOVE) {
                mTouchX = event.x
                mTouchY = event.y
            } else {
                mTouchX = -1f
                mTouchY = -1f
            }
            super.onTouchEvent(event)
        }

        /**
         * 定义绘制图形状的工具方法
         */
        private fun drawFrame() {
            //获取壁纸的surfaceHolder
            val holder = surfaceHolder
            var c: Canvas? = null
            try {
                c = holder.lockCanvas()
                c?.drawColor(-0x1)
                drawTouchPoint(c)
                mPaint.alpha = 76
                c?.translate(originX, originY)
                for (i in 0..count) {
                    c?.translate((cubeHeight * 2 / 3), 0f)
                    c?.scale(0.95f, 0.95f)
                    c?.rotate(20f)
                    c?.drawRect(0f, 0f, cubeHeight, cubeWidth, mPaint)
                }
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c)
                }
            }
            mHandle.removeCallbacks(drawTarget)
            if (mVisible) {
                count++
                if (count > 50) {
                    originX = Random.nextInt(dis.heightPixels * 2 / 3).toFloat()
                    originY = Random.nextInt(dis.heightPixels / 5).toFloat()
                    count = 1
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                mHandle.postDelayed(drawTarget, 100)
            }
        }

        private fun drawTouchPoint(c: Canvas?) {
            if (mTouchX >= 0 && mTouchY >= 0) {
                //设置画笔的透明度
                mPaint.alpha = 255
                heart?.let { c?.drawBitmap(it, mTouchX, mTouchY, mPaint) }
            }
        }
    }

}