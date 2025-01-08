package com.example.myapplication.demo7_1

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityImageBinding

class ImageActivity :BaseActivity<ActivityImageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_image

    override fun initView(savedInstanceState: Bundle?) {
    }
}