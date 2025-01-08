package com.example.myapplication

import android.app.Application
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LiveEventBus.config().autoClear(true).lifecycleObserverAlwaysActive(true)
        MMKV.initialize(this)

    }
}