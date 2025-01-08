package com.example.myapplication.demo5

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.myapplication.R
import java.lang.ref.WeakReference
import java.util.*

class AlphaImageView : AppCompatImageView {
    //每隔多少毫秒透明度改变一次
    private val speed = 300

    //图片透明度每次改变的大小
    private var alphaDelta = 0

    //记录图片当前的透明度
    private var curAlpha = 0
    private var timer: Timer? = null

    class MyHandle(val image: WeakReference<AlphaImageView>) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0x123) {
                //每次增加curAlpha
                image.get()?.let {
                    it.curAlpha += it.alphaDelta
                    if (it.curAlpha > 255) {
                        it.curAlpha = 255
                    }
                    it.imageAlpha = it.curAlpha
                }
            }
        }
    }

    private val handler = MyHandle(WeakReference(this))

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaImageView)
        val duration = typeArray.getInt(R.styleable.AlphaImageView_duration, 0)
        typeArray.recycle()
        //计算图片透明度每次改变的大小
        alphaDelta = 255 * speed / duration
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                val msg = Message()
                msg.what = 0x123
                if (curAlpha >= 255) {
                    timer?.cancel()
                } else {
                    handler.sendMessage(msg)
                }
            }
        }, 0L, speed.toLong())
    }

    override fun onDraw(canvas: Canvas?) {
        this.imageAlpha = curAlpha
        super.onDraw(canvas)
    }
}