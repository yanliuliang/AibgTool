package com.example.myapplication.demo7_5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityTweenTestBinding
import java.util.*

/**
 * @Description:补间动画 鲜花图片
 * @Author: dick
 * @CreateDate: 2023/2/15
 * @Version:
 */
class TweenTestActivity :BaseActivity<ActivityTweenTestBinding>() {
    companion object{
        private var reverse: Animation? = null
    }

    var handler: Handler = object :Handler(Looper.myLooper()!!){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123)
            {
                binding.flower.startAnimation(reverse)
            }
        }
    }
    override val layoutId: Int
        get() = R.layout.activity_tween_test

    override fun initView(savedInstanceState: Bundle?) {
        // 加载第一份动画资源
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        // 设置动画结束后保留结束状态
        anim.fillAfter = true
        // 加载第二份动画资源
        reverse = AnimationUtils.loadAnimation(this, R.anim.reverse)
        // 设置动画结束后保留结束状态
        reverse?.fillAfter = true

        binding.bn.setOnClickListener { view ->
            binding.flower.startAnimation(anim)
            // 设置3.5秒后启动第二个动画
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    handler.sendEmptyMessage(0x123)
                }
            }, 3500)
        }
    }
}