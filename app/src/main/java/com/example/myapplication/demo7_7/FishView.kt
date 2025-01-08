//package com.example.myapplication.demo7_7
//
//import android.R.drawable
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Matrix
//import android.view.SurfaceHolder
//import android.view.SurfaceView
//import java.util.*
//
//
//class FishView(val context: Context) : SurfaceView(context), SurfaceHolder.Callback {
//    private var updateThread: UpdateViewThread? = null
//    private var hasSurface = false
//    private var back: Bitmap? = null
//    private var fishs = arrayOfNulls<Bitmap>(10)
//    private var fishIndex // 定义变量记录绘制第几张鱼的图片
//            = 0
//
//    // 下面定义两个变量，记录鱼的初始位置
//    private var initX = 0f
//    private var initY = 500f
//
//    // 下面定义两个变量，记录鱼的当前位置
//    private var fishX = 0f
//    private var fishY = initY
//    private var fishSpeed = 12f // 鱼的游动速度
//
//    // 定义鱼游动的角度
//    private var fishAngle: Int = Random().nextInt(60)
//    var matrix: Matrix = Matrix()
//
//    init {
//        holder.addCallback(this)
//        back = BitmapFactory.decodeResource(context.resources, com.example.myapplication.R.drawable.fishbg)
//        for (i in 0..9){
//            try {
//                val fishId = drawable::class.java.getField("fish$i")[null] as Int
//                fishs[i] =BitmapFactory.decodeResource(context.resources,fishId)
//            }catch (e:java.lang.Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//    private fun resume(){
//        // 创建和启动图像更新线程
//        if (updateThread ==null){
//            updateThread = UpdateViewThread()
//            if (hasSurface){
//                updateThread?.start()
//            }
//        }
//    }
//
//    private fun pause(){
//        // 停止图像更新线程
//        if (updateThread != null)
//        {
//            updateThread?.requestExitAndWait()
//            updateThread = null
//        }
//    }
//
//
//
//    inner class UpdateViewThread :Thread(){
//        // 定义一个记录图像是否更新完成的旗标
//        private var done = false
//        override fun run() {
//            val surfaceHolder: SurfaceHolder = this@FishView.holder
//            while (!done) {
//                // 锁定SurfaceView，并返回到要绘图的Canvas
//                val canvas: Canvas = surfaceHolder.lockCanvas() // ①
//                // 绘制背景图片
//                back?.let { canvas.drawBitmap(it, 0f, 0f, null) }
//                // 如果鱼“游出”屏幕之外，重新初始化鱼的位置
//                if (fishX < -100) {
//                    fishX = initX
//                    fishY = initY
//                    fishAngle = Random().nextInt(60)
//                }
//                if (fishY < -100) {
//                    fishX = initX
//                    fishY = initY
//                    fishAngle = Random().nextInt(60)
//                }
//                // 使用Matrix来控制鱼的旋转角度和位置
//                matrix.reset()
//                matrix.setRotate(fishAngle.toFloat())
//                fishX -= (fishSpeed * Math.cos(Math.toRadians(fishAngle.toDouble()))).toFloat()
//                fishY -= (fishSpeed * Math.sin(Math.toRadians(fishAngle.toDouble()))).toFloat()
//                matrix.postTranslate(fishX, fishY)
//                fishs[fishIndex++ % fishs.size]?.let { canvas.drawBitmap(it, matrix, null) }
//                // 解锁Canvas，并渲染当前图像
//                surfaceHolder.unlockCanvasAndPost(canvas) // ②
//                try {
//                    sleep(60)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//
//        }
//        fun requestExitAndWait() {
//            // 把这个线程标记为完成，并合并到主程序线程中
//            done = true
//            try {
//                join()
//            } catch (ex: InterruptedException) {
//                ex.printStackTrace()
//            }
//        }
//
//        fun onWindowResize(w: Int, h: Int) {
//            // 处理SurfaceView的大小改变事件
//            println("w:$w===h:$h")
//        }
//    }
//
//    override fun surfaceCreated(holder: SurfaceHolder) {
//        initX = (width + 50).toFloat()
//        fishX = initX
//        hasSurface = true
//        resume()
//    }
//
//    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//        if (updateThread != null) updateThread?.onWindowResize(width, height)
//    }
//
//    override fun surfaceDestroyed(holder: SurfaceHolder) {
//        hasSurface = false
//        pause()
//    }
//}
//
