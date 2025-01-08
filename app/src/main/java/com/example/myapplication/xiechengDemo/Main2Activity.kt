package com.example.myapplication.xiechengDemo

import android.app.Activity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityDemoBinding


class Main2Activity : BaseActivity<ActivityDemoBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_demo

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


}