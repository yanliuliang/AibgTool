package com.example.myapplication.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityDashViewShowBinding

class DashViewShowActivity : BaseActivity<ActivityDashViewShowBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_dash_view_show

    override fun initView(savedInstanceState: Bundle?) {
    }

}