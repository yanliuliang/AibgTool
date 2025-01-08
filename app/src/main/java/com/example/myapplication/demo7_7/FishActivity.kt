package com.example.myapplication.demo7_7

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityFishBinding

class FishActivity :BaseActivity<ActivityFishBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_fish

    override fun initView(savedInstanceState: Bundle?) {

    }
}