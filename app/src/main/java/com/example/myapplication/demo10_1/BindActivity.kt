package com.example.myapplication.demo10_1

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutBindServiceBinding

class BindActivity :BaseActivity<LayoutBindServiceBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_bind_service
    private val TAG = "BindService"
    private var binder :BindService.MyBinder?=null
    private val conn = object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            binder = service as BindService.MyBinder?
            binder?.getCount()
            Log.d(TAG, "onServiceConnected: Service的count值为:${binder?.getCount()}")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
        }
    }
    
    override fun initView(savedInstanceState: Bundle?) {
        val intent = Intent(this,BindService::class.java)
        binding.btnBind.setOnClickListener {
            bindService(intent,conn,Service.BIND_AUTO_CREATE)
        }
        binding.btnUnBind.setOnClickListener { 
            unbindService(conn)
        }
    }
}