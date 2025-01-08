package com.example.myapplication.demo9_2

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding

class ContentResolveActivity :BaseActivity<LayoutEmptyBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_empty

    override fun initView(savedInstanceState: Bundle?) {
    }
}