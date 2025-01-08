package com.example.myapplication.sms

import android.net.Uri
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding

class GetSmsActivity :BaseActivity<LayoutEmptyBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_empty

    private val SMS_INBOX: Uri = Uri.parse("content://sms/")
    override fun initView(savedInstanceState: Bundle?) {

    }
}