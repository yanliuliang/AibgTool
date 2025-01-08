package com.tool.aibglibrary

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.TouchDelegate
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


inline fun <reified T> startActivity(context: Context?, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.startActivity(intent)
}

inline fun <reified T> startActivity(context: Context?) {
    val intent = Intent(context, T::class.java)
    context?.startActivity(intent)
}

inline fun <reified VM : ViewModel> Fragment.viewModels(
): VM {
    return ViewModelProvider(this)[VM::class.java]
}

/**
 * 扩展方法，扩大点击区域
 * NOTE: 需要保证目标targetView有父View，否则无法扩大点击区域
 *
 * @param expandSize 扩大的大小，单位px
 */
fun View.expandTouchView(expandSize: Int = 10) {
    val parentView = (parent as? View)
    parentView?.post {
        val rect = Rect()
        getHitRect(rect) //getHitRect(rect)将视图在父容器中所占据的区域存储到rect中。
        Log.d("打印yll", "expandTouchView() +  $rect")
        rect.left -= expandSize
        rect.top -= expandSize
        rect.right += expandSize
        rect.bottom += expandSize
        Log.d("打印yll", "expandRect() +  $rect")
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}



