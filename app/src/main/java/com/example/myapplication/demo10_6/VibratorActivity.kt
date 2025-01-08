package com.example.myapplication.demo10_6

import android.os.Bundle
import android.os.Vibrator
import android.view.GestureDetector
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding

class VibratorActivity :BaseActivity<LayoutEmptyBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_empty

    override fun initView(savedInstanceState: Bundle?) {
    }

    private lateinit var vibrator :Vibrator
    private var detector :GestureDetector ?=null

//    override fun initView(savedInstanceState: Bundle?) {
//        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        detector = GestureDetector(this,object :GestureDetector.SimpleOnGestureListener(){
//            override fun onLongPress(e: MotionEvent?) {
//                super.onLongPress(e)
//                "手机震动".toast()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    vibrator.vibrate(VibrationEffect.createOneShot(2000L,180))
//                }
//            }
//        })
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return detector?.onTouchEvent(event)!!
//    }
}