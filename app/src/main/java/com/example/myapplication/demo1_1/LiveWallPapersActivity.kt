package com.example.myapplication.demo1_1

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.LayoutLiveWallpapersBinding

class LiveWallPapersActivity : AppCompatActivity(){
    private lateinit var binding:LayoutLiveWallpapersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLiveWallpapersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRequest.setOnClickListener {

        }
    }
}