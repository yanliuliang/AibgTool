package com.example.myapplication.demo41

data class FlowNode(
    val id: Int = 0,
    val nextId: Int,

    val name: String = "",

    var type: Int = 1
)
