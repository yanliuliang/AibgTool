package com.example.myapplication.demo8_2

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding
import java.io.FileInputStream
import java.io.FileOutputStream

class FileActivity :BaseActivity<LayoutEmptyBinding>() {
    override val layoutId: Int
        get() = R .layout.layout_empty

    override fun initView(savedInstanceState: Bundle?) {
        //打开应用程序下的数据文件夹下的name文件对应的输入流
        val openFileInput = FileInputStream("")
        //打开应用程序的数据文件下对应的输出流
        val openFileOutPut = FileOutputStream("",)
    }
}