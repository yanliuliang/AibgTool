package com.tool.aibglibrary.view

import android.util.Log
import androidx.fragment.app.FragmentManager

/**
 * @Description:loading动画
 * @Author: dick
 * @CreateDate: 2023/5/4
 * @Version:
 */
object LoadingUtil {
    private val dialog = LoadingDialog.newIntent()

    fun showLoading(manage: FragmentManager) {
        try {
            Log.d("打印yll", "showLoading() + ")
            dialog.show(manage, "loadingDialog")
        } catch (e: Exception) {
            Log.d("打印yll", "showLoading() + $e")
            e.printStackTrace()
        }
    }

    fun dismissLoading() {
        Log.d("打印yll", "dismissLoading() + ")
        dialog.dismiss()
    }

}