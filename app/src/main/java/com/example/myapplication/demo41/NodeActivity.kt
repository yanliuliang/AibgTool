package com.example.myapplication.demo41

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class NodeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node)

        findViewById<FlowChartView>(R.id.FlowChartView).setPlot(3)
    }
}