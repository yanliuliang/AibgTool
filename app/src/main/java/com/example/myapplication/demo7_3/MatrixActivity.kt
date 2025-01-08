package com.example.myapplication.demo7_3

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMatrixBinding

class MatrixActivity :BaseActivity<ActivityMatrixBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_matrix

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn1.setOnClickListener {
            binding.parentLayout.removeAllViews()
            val myMatrixView = MyMatrixView(this)
            myMatrixView.sx =-10f
            myMatrixView.isScale =true
            binding.parentLayout.addView(myMatrixView)
        }
        binding.btn2.setOnClickListener {
            binding.parentLayout.removeAllViews()
            val myMatrixView = MyMatrixView(this)
            myMatrixView.sx =10f
            myMatrixView.isScale =true
            binding.parentLayout.addView(myMatrixView)
        }
        binding.btn3.setOnClickListener {
            binding.parentLayout.removeAllViews()
            val myMatrixView = MyMatrixView(this)
            myMatrixView.scale = 1.5f
            myMatrixView.isScale =false
            binding.parentLayout.addView(myMatrixView)
        }
        binding.btn4.setOnClickListener {
            binding.parentLayout.removeAllViews()
            val myMatrixView = MyMatrixView(this)
            myMatrixView.scale = 0.1f
            myMatrixView.isScale =false
            binding.parentLayout.addView(myMatrixView)
        }
    }
}