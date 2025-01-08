package com.example.myapplication.demo21

import android.util.Log


val httpdUdpService = HttpUdpServiceImpl

object HttpUdpServiceImpl : HttpdUdpService {
    override  fun getDate(): DemoBean {
        Log.d("TAG", "getDate:获取时间 ")
        return DemoBean("123")
    }

}