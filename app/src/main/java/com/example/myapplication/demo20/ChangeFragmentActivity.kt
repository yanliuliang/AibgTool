package com.example.myapplication.demo20

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityFragmentChangeBinding
import com.example.myapplication.demo21.ViewMedioDemo

class ChangeFragmentActivity : BaseActivity<ActivityFragmentChangeBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_fragment_change


    private val viewMedioDemo by lazy {
        ViewModelProvider(this)[ViewMedioDemo::class.java]
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn.setOnClickListener {
            viewMedioDemo.demo()
        }
    }
}