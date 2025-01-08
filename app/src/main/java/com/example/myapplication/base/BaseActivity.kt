package com.example.myapplication.base

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Description:activity的基类
 * @Author: dick
 * @CreateDate: 2022/10/13
 * @Version:
 */
abstract class BaseActivity<db : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: db
    abstract val layoutId: Int
    var immersion = false
    var immersionBottomBar = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        initView(savedInstanceState)
        val window: Window = window
        if (immersionBottomBar) {
            window.navigationBarColor = Color.BLACK
        } else {
            window.navigationBarColor = Color.WHITE
        }
        binding.lifecycleOwner = this
        initListener()
    }

    open fun initListener() {}

    abstract fun initView(savedInstanceState: Bundle?)
}