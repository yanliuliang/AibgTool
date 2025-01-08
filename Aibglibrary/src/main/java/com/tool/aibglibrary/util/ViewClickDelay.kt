package com.tool.aibglibrary.util

import android.util.Log
import android.view.View

object ViewClickDelay {
   private var hash: Int = 0
   private var lastClickTime: Long = 0
   private var SPACE_TIME: Long = 400


    /**
     * 防止连续点击
     * @receiver View
     * @param clickAction Function0<Unit>
     */
    infix fun View.clickDelay(clickAction: () -> Unit) {
        this.setOnClickListener {
            Log.d("打印yll", "clickDelay: ")
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > SPACE_TIME) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }
            }
        }
    }

}