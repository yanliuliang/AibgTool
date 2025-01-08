package com.tool.aibglibrary.view

import android.content.Context
import android.util.TypedValue


class DensityUtil private constructor() {
    companion object {
        /**
         * dp转px
         */
        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.resources.displayMetrics
            ).toInt()
        }

        /**
         * sp转px
         */
        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spVal, context.resources.displayMetrics
            ).toInt()
        }

        /**
         * @Description 根据手机的分辨率将 sp 值转换为 px 值，保证文字大小不变
         */
        fun sp2pxInt(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px转dp
         */
        fun px2dp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }

        fun px2sp(context: Context, pxValue: Int): Float {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return pxValue / fontScale + 0.5f
        }

        /**
         * px转sp
         */
        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }

        /**
         * 获取状态栏高度
         */
        fun getStateBarHeight(context: Context): Int {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resourceId > 0) context.resources
                .getDimensionPixelSize(resourceId) else 0
        }

        fun getScreenHeight(context: Context): Int {
            val dm = context.resources.displayMetrics
            return dm.heightPixels
        }

        fun getScreenWidth(context: Context): Int {
            val dm = context.resources.displayMetrics
            return dm.widthPixels
        }
    }

}