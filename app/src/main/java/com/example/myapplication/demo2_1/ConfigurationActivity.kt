package com.example.myapplication.demo2_1

import android.content.res.Configuration
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityConfigurationBinding


class ConfigurationActivity : BaseActivity<ActivityConfigurationBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_configuration

    private val cfg = resources.configuration

    override fun initView(savedInstanceState: Bundle?) {
        val frontScale = cfg.fontScale //获取用户设置字体的缩放因子

        val locale = cfg.locale //获取用户的locale

        val mcc=  cfg .mcc //获取移动国家的国家码

    }

    /**
     * 响应系统设置更改
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}