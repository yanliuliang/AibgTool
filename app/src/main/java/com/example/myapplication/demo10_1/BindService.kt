package com.example.myapplication.demo10_1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

 class BindService : Service() {
    companion object{
        var count = 0
        const val TAG = "BindService"
        var quit = false
    }
    var binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getCount(): Int = count
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: Service is Binded")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Service is Create")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "onCreate: 启动一个子线程")
            while (!quit) {
                delay(1000)
                count++
                Log.d(TAG, "onCreate: $count")
            }

        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: Service is Unbinded")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        quit = true
        Log.d(TAG, "onUnbind: Service is onDestroy ")
    }
}