package com.example.myapplication.demo16_3

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.net.wifi.rtt.RangingRequest
import android.net.wifi.rtt.RangingResult
import android.net.wifi.rtt.RangingResultCallback
import android.net.wifi.rtt.WifiRttManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityGpsBinding
import java.util.concurrent.Executors

class GPSActivity : BaseActivity<ActivityGpsBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gps

    /**
     * GPS管理器
     */
    private lateinit var locManager: LocationManager

    /**
     * Wifi管理器
     */
    private lateinit var wifiRttManage: WifiRttManager

    inner class WifiChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION == intent?.action) {
//                startWifiLoc()
            }
        }

    }


    override fun initView(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0x123)
        }

        val wifiFilter = IntentFilter()
        wifiFilter.apply {
            addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            //为intnetFilter注册broadcaseReceiver
            registerReceiver(WifiChangeReceiver(), wifiFilter)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x123 && grantResults.size == 1) {
            locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            updateView(location)
            locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000,
                8f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        updateView(location)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        super.onStatusChanged(provider, status, extras)
                        updateView(locManager.getLastKnownLocation(provider!!))
                    }

                    override fun onProviderDisabled(provider: String) {
                        super.onProviderDisabled(provider)
                        updateView(null)
                    }
                })
        }
    }

    private fun updateView(location: Location?) {
        binding.tvGps.text = ""

        location?.apply {
            val sb = "GPS 实时的位置信息：\n" +
                    "经度 :${location.longitude}" +
                    "\n纬度:${location.latitude}" +
                    "\n高度 ：${location.altitude}" +
                    "\n速度 ：${location.speed}" +
                    "\n方向 ：${location.bearing}"
            Log.d("updateView", "updateView: $sb")
            binding.tvGps.text = sb
        }
    }

    private fun startWifiLoc() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val hasRtt = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_RTT)
        Toast.makeText(this, "是否具有wifi定位功能 $hasRtt", Toast.LENGTH_SHORT).show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wifiRttManage = getSystemService(Context.WIFI_RTT_RANGING_SERVICE) as WifiRttManager
            val request = RangingRequest.Builder().addAccessPoints(wifiManager.scanResults).build()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            wifiRttManage.startRanging(
                request,
                Executors.newCachedThreadPool(),
                object : RangingResultCallback() {
                    override fun onRangingFailure(p0: Int) {
                        Log.e(
                            "onRangingResults",
                            "onRangingResults: wifi的错误 $p0}",
                        )
                    }

                    override fun onRangingResults(p0: MutableList<RangingResult>) {
//                        for (bean in p0) {
//                            Log.e(
//                                "onRangingResults",
//                                "onRangingResults: wifi的距离是:${bean.distanceMm}",
//                            )
//                        }
                        Log.e(
                            "onRangingResults",
                            "onRangingResults: wifi的距离是:${p0[0].distanceMm}",
                        )
                    }

                })
        }
    }
}