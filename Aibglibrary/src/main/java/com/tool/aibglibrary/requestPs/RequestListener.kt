package com.tool.aibglibrary.requestPs

interface RequestListener {

    /**
     * 申请结果
     */
    fun  requestResult(result:Boolean)

    /**
     * 是否为第一次申请
     */
    fun isFirstRequest(first:Boolean)

    /**
     * 已经拥有权限
     */
    fun allAgree()
}