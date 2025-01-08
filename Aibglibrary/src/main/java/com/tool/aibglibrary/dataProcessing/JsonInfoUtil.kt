package com.tool.aibglibrary.dataProcessing

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * Utility class for fetching and parsing JSON data from a URL.
 */
//   runBlocking {
//        // You can replace 'YourDataType' with the actual type you want to use
//        val jsonData: MutableList<YourDataType> = SpecialInfoUtil.getUrlJsonData()
//
//        // Now you can work with the jsonData as needed
//        println("Received JSON data: $jsonData")
//    }
/**
 * 获取json数据
 */
class JsonInfoUtil<T> private constructor() {

    companion object {
        private var path =
            "http://ossfile.pangbaoapp.com/d5959452-e413-48a0-b807-3f8ad0e563f9.json"

        suspend fun <T> getUrlJsonData(url: String = path): MutableList<T> {
            path = url
            return withContext(Dispatchers.IO) {
                val jsonInfoUtil = JsonInfoUtil<T>()
                jsonInfoUtil.getJsonInfo()
            }
        }
    }

    private var figureInfoList: MutableList<T>? = null

    suspend fun getJsonInfo(): MutableList<T> {
        return withContext(Dispatchers.IO) {
            figureInfoList?.apply {
                if (size > 0) {
                    Log.d("parseFigureInfo", "parseFigureInfo: 已有数据")
                    figureInfoList as MutableList<T>
                } else {
                    Log.d("parseFigureInfo", "parseFigureInfo: 获取数据")
                    val url = URL(path)
                    var bufferedReader: BufferedReader? = null
                    try {
                        bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
                        val sb = StringBuffer()
                        while (true) {
                            val line = bufferedReader.readLine() ?: break
                            sb.append(line)
                        }
                        val list: MutableList<T> =
                            Gson().fromJson(
                                sb.toString(),
                                object : TypeToken<MutableList<T>>() {}.type
                            )
                        figureInfoList = list
                        figureInfoList
                    } catch (e: Exception) {
                        e.printStackTrace()
                        mutableListOf()
                    } finally {
                        bufferedReader?.close()
                    }
                }
            } ?: mutableListOf()
        }
    }
}
