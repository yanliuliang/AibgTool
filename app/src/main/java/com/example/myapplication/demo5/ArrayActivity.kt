package com.example.myapplication.demo5

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding
import com.example.myapplication.databinding.LayoutImageBinding
import java.util.*

class ArrayActivity : BaseActivity<LayoutImageBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_image

    override fun initView(savedInstanceState: Bundle?) {
        val drawable = binding.imageView.drawable

        class MyHandler : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0x1233) {
                    drawable.level = drawable.level + 50
                }
            }
        }

        val handler = MyHandler()
        val time = Timer()
        time.schedule(object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = 0x1233
                handler.sendMessage(message)
                if (drawable.level >= 10000) {
                    time.cancel()
                }
            }

        }, 0, 20)
        startAnimal()
    }

    private fun startAnimal() {
//        val anim = AnimationUtils.loadAnimation(this, R.anim.my_anim)
//        anim.fillAfter = true
//        binding.btn.setOnClickListener {
//            binding.btn.startAnimation(anim)
//        }
    }
}