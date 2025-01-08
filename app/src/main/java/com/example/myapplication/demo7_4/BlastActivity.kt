package com.example.myapplication.demo7_4

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import java.lang.reflect.Field


class BlastActivity : AppCompatActivity() {

    companion object{
        val BLAST_WIDTH = 240
        val BLAST_HEIGHT = 240
        private var myView: MyView? = null
        private lateinit var  anim: AnimationDrawable
        private var bomb: MediaPlayer? = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frame = FrameLayout(this)
        setContentView(frame)
        // 设置使用背景
        // 设置使用背景
        frame.setBackgroundResource(R.drawable.back)
        // 加载音效
        // 加载音效
        bomb = MediaPlayer.create(this, R.raw.bomb)
        myView = MyView(this)
        // 设置myView用于显示blast动画
        // 设置myView用于显示blast动画
        myView?.setBackgroundResource(R.drawable.blast)
        // 设置myView默认为隐藏
        // 设置myView默认为隐藏
        myView?.visibility = View.INVISIBLE
        // 获取动画对象
        // 获取动画对象
        anim = myView?.background as AnimationDrawable
        frame.addView(myView)
        frame.setOnTouchListener { source: View?, event: MotionEvent ->
            // 只处理按下事件（避免每次产生两个动画效果）
            if (event.action == MotionEvent.ACTION_DOWN) {
                // 先停止动画播放
                anim.stop()
                val x = event.x
                val y = event.y
                // 控制myView的显示位置
                myView?.setLocation(y.toInt() - BLAST_HEIGHT, x.toInt() - BLAST_WIDTH / 2)
                myView?.visibility = View.VISIBLE
                // 启动动画
                anim.start()
                // 播放音效
                bomb!!.start()
            }
            false
        }
    }


    // 定义一个自定义View，该自定义View用于播放“爆炸”效果
    internal class MyView(context: Context) : androidx.appcompat.widget.AppCompatImageView(context) {
        // 定义一个方法，该方法用于控制MyView的显示位置
        fun setLocation(top: Int, left: Int) {
            this.setFrame(left, top, left + BLAST_WIDTH, top + BLAST_HEIGHT)
        }

        // 重写该方法，控制如果动画播放到最后一帧时，隐藏该View
        override fun onDraw(canvas: Canvas?) // ①
        {
            try {
                val field: Field = AnimationDrawable::class.java.getDeclaredField("mCurFrame")
                field.isAccessible = true
                // 获取anim动画的当前帧
                val curFrame: Int = field.getInt(anim)
                // 如果已经到了最后一帧
                if (curFrame == anim.numberOfFrames - 1) {
                    // 让该View隐藏
                    visibility = View.INVISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            super.onDraw(canvas)
        }
    }
}