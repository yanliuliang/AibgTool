package com.example.myapplication.demo7_5

import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.animation.TranslateAnimation
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityButterflyBinding
import java.util.*


class ButterflyActivity :BaseActivity<ActivityButterflyBinding>() {
    // 记录蝴蝶ImageView当前的位置
    private var curX = 0f
    private var curY = 30f

    // 记录蝴蝶ImageView下一个位置的坐标
    private var nextX = 0f
    private var nextY = 0f
    private var screenWidth = 0f

    override val layoutId: Int
        get() = com.example.myapplication.R.layout.activity_butterfly
    private val handle = object :Handler(Looper.myLooper()!!){
        override fun handleMessage(msg: Message) {
            if (msg.what == 0x123) {
                // 横向上一直向右飞
                if (nextX > screenWidth) {
                    nextX = 0f
                    curX = nextX
                } else {
                    nextX += 8f
                }
                // 纵向上可以随机上下
                nextY = curY + (Math.random() * 10 - 5).toFloat()
                // 设置显示蝴蝶的ImageView发生位移改变
                val anim = TranslateAnimation(curX, nextX, curY, nextY)
                curX = nextX
                curY = nextY
                anim.duration = 200
                // 开始位移动画
                binding.butterfly.startAnimation(anim) // ①
            }
        }
    }
    override fun initView(savedInstanceState: Bundle?) {
        val p = Point()
        // 获取屏幕宽度
        // 获取屏幕宽度
        windowManager.defaultDisplay.getSize(p)
        screenWidth = p.x.toFloat()
        // 获取显示蝴蝶的ImageView组件
        // 获取显示蝴蝶的ImageView组件
        val butterfly = binding.butterfly.background as AnimationDrawable
        binding.butterfly.setOnClickListener {
            // 开始播放蝴蝶振翅的逐帧动画
            butterfly.start() // ②
            // 通过定时器控制每0.2秒运行一次TranslateAnimation动画
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    handle.sendEmptyMessage(0x123)
                }
            }, 0, 200)
        }
    }
}