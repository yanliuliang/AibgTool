package com.example.myapplication.demo16_4

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityProximityAlertBinding
import java.util.jar.Manifest

class ProximityAlertActivity : BaseActivity<ActivityProximityAlertBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_proximity_alert

    private lateinit var locationManager: LocationManager
    override fun initView(savedInstanceState: Bundle?) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0x123)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x123 && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val longitude = binding.etLongitude.text.toString().toDouble()
            val latitude = binding.etLatitude.text.toString().toDouble()

            //定义半径5公里
            val radius= 5000f
            val intent = Intent(this,ProximityAlertReciever::class.java)
            val pi = PendingIntent.getBroadcast(this,-1,intent,PendingIntent.FLAG_MUTABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager.addProximityAlert(latitude,longitude,radius,-1,pi)
        }
    }
}