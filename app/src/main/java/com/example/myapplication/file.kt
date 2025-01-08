package com.example.myapplication

import android.widget.Toast

fun String.toast(){
    Toast.makeText(App.instance,this,Toast.LENGTH_SHORT).show()
}