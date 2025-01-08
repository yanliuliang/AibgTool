package com.example.myapplication.demo10_3

import android.Manifest
import android.R
import android.app.ListActivity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutEmptyBinding


class TelephonyManagerActivity : ListActivity() {

    // 声明代表手机状态的集合
    private val statusValues: MutableList<String> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(
            arrayOf<String>(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,

            ), 0x123
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x123 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // 获取系统的TelephonyManager对象
            val tManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // 获取代表状态名称的数组
            val statusNames =
                resources.getStringArray(com.example.myapplication.R.array.statusNames)
            // 获取代表SIM卡状态的数组
            val simState = resources.getStringArray(com.example.myapplication.R.array.simState)
            // 获取代表电话网络类型的数组
            val phoneType = resources.getStringArray(com.example.myapplication.R.array.phoneType)
            // 获取设备编号
//            statusValues.add(tManager.imei)
            // 获取系统平台的版本
            (if (tManager.deviceSoftwareVersion != null) tManager.deviceSoftwareVersion else "未知")?.let {
                statusValues.add(
                    it
                )
            }
            // 获取网络运营商代号
            statusValues.add(tManager.networkOperator)
            // 获取网络运营商名称
            statusValues.add(tManager.networkOperatorName)
            // 获取手机网络类型
            statusValues.add(phoneType[tManager.phoneType])
            // 获取设备的蜂窝状态信息（包括位置）
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            statusValues.add(if (tManager.allCellInfo != null) tManager.allCellInfo.toString() else "未知信息")
            // 获取SIM卡的国别
            statusValues.add(tManager.simCountryIso)
            // 获取SIM卡序列号
//            statusValues.add(tManager.simSerialNumber)
            // 获取SIM卡状态
            statusValues.add(simState[tManager.simState])
            // 获得ListView对象
            val status: MutableList<Map<String, String?>> = ArrayList()
            // 遍历statusValues集合，将statusNames、statusValues
            // 的数据封装到List<Map<String , String>>集合中
            for (i in statusValues.indices) {
                val map: MutableMap<String, String?> = HashMap()
                map["name"] = statusNames[i]
                map["value"] = statusValues[i]
                status.add(map)
            }
            // 使用SimpleAdapter封装List数据
            val adapter = SimpleAdapter(
                this,
                status,
                com.example.myapplication.R.layout.line,
                arrayOf<String>("name", "value"),
                intArrayOf(
                    com.example.myapplication.R.id.name,
                    com.example.myapplication.R.id.value
                )
            )
            // 为ListActivity设置Adapter
            listAdapter = adapter
        } else {
            Toast.makeText(
                this, com.example.myapplication.R.string.permission_tip,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}