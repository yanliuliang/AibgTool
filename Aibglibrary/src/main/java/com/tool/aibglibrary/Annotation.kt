package com.tool.aibglibrary

object Annotation {
    /**
     * 时间单位
     */
    @Retention(AnnotationRetention.SOURCE)
    annotation class TimeTrans
    const val Year_Month = "yyyy-MM"
    const val Year_Month_Day = "yyyy-MM-dd"
    const val Year_Month_Day_House = "yyyy-MM-dd HH"
    const val Year_Month_Day_House_Minute = "yyyy-MM-dd HH:mm"
    const val Year_Month_Day_House_Minute_Second = "yyyy-MM-dd HH:mm:ss"
}