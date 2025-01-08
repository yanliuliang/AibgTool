package com.example.myapplication.demo16_4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import android.widget.Toast

class ProximityAlertReciever :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isEnter = intent?.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,false)
        if (isEnter==true){
            Log.d("onReceive", "onReceive: 你已靠近这附近")
            Toast.makeText(context, "你已靠近这附近", Toast.LENGTH_SHORT).show()
        }else{
            Log.d("onReceive", "onReceive: 你已离开这附近")
            Toast.makeText(context, "你已离开这附近", Toast.LENGTH_SHORT).show()

        }
    }
}