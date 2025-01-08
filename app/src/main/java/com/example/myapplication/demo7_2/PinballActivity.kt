package com.example.myapplication.demo7_2

import android.content.Context
import android.graphics.*
import android.os.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityPinballBinding
import java.lang.ref.WeakReference
import java.util.*

class PinballActivity : AppCompatActivity() {


    //小球的大小
    companion object {    //屏幕的高度
        private var tableHeight: Int = 0

        //球拍的垂直位置
        private var racketY: Float = 0f

        //下面定义球拍的高度和宽度
        private var racketHeight: Float = 0f
        private var racketWidth: Float = 0f
        var ballSize: Float = 0f

        //游戏是否结束的旗标
        private var isLose = false

        //屏幕的宽度
        private var tableWidth: Int = 0
        private val random = Random()

        //ballX和ballY代表小球的坐标
        private var ballX = random.nextInt(200) + 20f
        private var ballY = random.nextInt(10) + 60f

        //小球横向的运行速度
        private var ySpeed = 15


        //返回一个-0.5 ~ 0.5的比率，用于控制小球的运行方向
        private var xyRate = random.nextDouble() - 0.5

        //小球横向的运行速度
        private var xSpeed = ySpeed * xyRate * 2.0


        //racketX代表球拍的水平位置
        private var racketX = random.nextInt(200)
    }


    private lateinit var gameView: GameView

    private class MyHandler(val activity: WeakReference<PinballActivity>) :
        Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123) {
                activity.get()?.gameView?.invalidate()
            }
        }
    }

    private val handler = MyHandler(WeakReference(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        racketHeight = resources.getDimension(R.dimen.racket_height)
        racketWidth = resources.getDimension(R.dimen.racket_width)
        ballSize = resources.getDimension(R.dimen.ball_size)
        //全屏显示
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //创建GameView组件
        gameView = GameView(this)
        setContentView(gameView)
        //获取窗口管理器
        val windowManager = windowManager
        val display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        //获取屏幕宽和高
        tableWidth = metrics.widthPixels
        tableHeight = metrics.heightPixels
        racketY = (tableHeight - 80).toFloat()
        gameView.setOnTouchListener { _, event ->
            event?.apply {
                if (this.x <= tableWidth / 10) {
                    if (racketX > 0) racketX -= 10
                }
                if (this.x >= tableWidth * 9 / 10) {
                    if (racketX < tableWidth - racketWidth) racketX += 10
                }
            }
            true
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (ballX < ballSize || ballX >= tableWidth - ballSize) {
                    xSpeed = -xSpeed
                }
                //如果小球高度超出球拍位置，且横向不在球拍范围内，游戏结束
                if (ballY >= racketY - ballSize && (ballX < racketX || ballX > racketWidth)) {
                    timer.cancel()
                    isLose = true
                } else if (ballX > ballSize || (ballY > racketY - ballSize && ballX > racketX && ballX <= racketX + racketWidth)) {
                    ySpeed = -ySpeed
                }
                ballX += xSpeed.toInt()
                ballY += ySpeed
                handler.sendEmptyMessage(0x123)
            }

        }, 0, 100)
    }

    class GameView : View {
        private val paint = Paint()
        private val mShader = RadialGradient(
            -ballSize / 2f,
            -ballSize / 2f,
            ballSize.toFloat(),
            Color.WHITE,
            Color.RED,
            Shader.TileMode.CLAMP
        )

        constructor(context: Context?) : this(context, null)
        constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        ) {
            paint.style = Paint.Style.FILL
            paint.isAntiAlias = true
        }

        //重写view的ondraw方法 实现绘画
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            //如果游戏已经结束
            if (isLose) {
                paint.color = Color.RED
                paint.textSize = 40f
                canvas?.drawText("游戏已结束", tableWidth / 2f - 100, 200f, paint)
            }
            //如果游戏还没开始
            else {
                canvas?.save() //保存坐标系统
                canvas?.translate(ballX, ballY)
                //设置渐变，并绘制小球
                paint.shader = mShader
                canvas?.drawCircle(0f, 0f, ballSize, paint)
                canvas?.restore()
                paint.shader = null
                paint.color = Color.rgb(80, 80, 200)
                canvas?.drawRect(
                    racketX.toFloat(),
                    racketY,
                    racketX + racketWidth,
                    racketY + racketHeight,
                    paint
                )
            }
        }
    }

}