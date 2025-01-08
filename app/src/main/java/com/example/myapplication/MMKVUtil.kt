package com.example.myapplication

import com.tencent.mmkv.MMKV

/**
 * @Description:MMKV
 * @Author: dick
 * @CreateDate: 2022/10/13
 * @Version:
 */
object MMKVUtil {
    private val kv = MMKV.defaultMMKV()

    private fun saveKv(tag: String, msg: Boolean) {
        kv.encode(tag, msg)
    }

    private fun saveKv(tag: String, msg: String?) {
        kv.encode(tag, msg)
    }

    private fun saveKv(tag: String, msg: Int) {
        kv.encode(tag, msg)
    }

    private fun saveKv(tag: String, msg: Long) {
        kv.encode(tag, msg)
    }

    private fun getBoolKv(tag: String, msg: Boolean): Boolean = kv.decodeBool(tag, msg)
    private fun getStringKv(tag: String, msg: String? = ""): String =
        kv.decodeString(tag, msg).toString()

    private fun getIntKv(tag: String, default: Int = 0): Int = kv.decodeInt(tag, default)
    private fun getLongKv(tag: String): Long = kv.decodeLong(tag, 0L)

    private const val AppWidgetId = "appWidgetId"

    fun addAppWidgetId(id: Int) {
        val appWidgetId = getAppWidgetId()
        saveKv(AppWidgetId, "${appWidgetId}${id}-")
    }

    fun saveAppWidgetId(id: String) {
        saveKv(AppWidgetId, id)
    }


    fun getAppWidgetId(): String {
        return getStringKv(AppWidgetId, "")
    }

    private const val ClickAllCount = "clickAllCount"

    fun saveClickAllCount() {
        saveKv(ClickAllCount, getClickAllCount() + 1)
    }

    fun updateClickAllCount(count: Int) {
        saveKv(ClickAllCount, count)
    }

    fun getClickAllCount(): Int = getIntKv(ClickAllCount, 1)

}