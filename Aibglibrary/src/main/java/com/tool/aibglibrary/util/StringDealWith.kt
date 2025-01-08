package com.tool.aibglibrary.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import java.text.DecimalFormat


/**
 *复制内容到剪贴板
 */
fun String.replicate(context: Context) {
    //获取剪切板管理器
    val cm: ClipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    //设置内容到剪切板
    cm.setPrimaryClip(ClipData.newPlainText(null, this))
}

/**
 * 保留位数
 */
fun Any.decimalFormat(decimalFormat: String = "00"): Any {
    val dm = DecimalFormat(decimalFormat)
    return dm.format(this)
}

