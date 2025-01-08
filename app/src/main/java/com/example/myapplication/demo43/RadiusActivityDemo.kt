package com.example.myapplication.demo43

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlin.math.atan2

class RadiusActivityDemo : AppCompatActivity() {

    private lateinit var circleImageView: ImageView
    private var initialTouchAngle: Float = 0f
    private var initialRotationAngle: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activtiy_radius)

        circleImageView = findViewById(R.id.imageview)

        // 启用硬件加速
        circleImageView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        circleImageView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 记录初始触摸位置和圆形图像视图的初始角度
                    val centerX = view.width / 2f
                    val centerY = view.height / 2f
                    initialTouchAngle = Math.toDegrees(atan2((motionEvent.y - centerY).toDouble(), (motionEvent.x - centerX).toDouble())).toFloat()
                    initialRotationAngle = view.rotation
                }
                MotionEvent.ACTION_MOVE -> {
                    val centerX = view.width / 2f
                    val centerY = view.height / 2f
                    val newTouchAngle = Math.toDegrees(atan2((motionEvent.y - centerY).toDouble(), (motionEvent.x - centerX).toDouble())).toFloat()
                    val rotationDelta = newTouchAngle - initialTouchAngle

                    // 添加一个条件来检查手指移动的距离是否超过阈值，例如30度
                    if (Math.abs(rotationDelta) > 10) {
                        // 将旋转操作移到主线程之外
                        view.post {
                            // 设置圆形图像视图的旋转角度
                            view.rotation = initialRotationAngle + rotationDelta
                        }
                    }
                }

            }
            true
        }
    }
}