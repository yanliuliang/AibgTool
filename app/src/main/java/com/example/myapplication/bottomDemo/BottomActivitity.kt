package com.example.myapplication.bottomDemo

import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityBottomBinding

class BottomActivitity :BaseActivity<ActivityBottomBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_bottom

    override fun initView(savedInstanceState: Bundle?) {

        binding.expandableBottomBar.onItemSelectedListener = { view, menuItem,b ->
            Toast.makeText(this, "${menuItem.id}", Toast.LENGTH_SHORT).show()
        }
    }
}