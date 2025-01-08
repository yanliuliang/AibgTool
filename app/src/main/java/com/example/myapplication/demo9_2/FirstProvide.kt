package com.example.myapplication.demo9_2

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

class FirstProvide : ContentProvider() {
    companion object {
        const val TAG = "FirstProvide"
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: ")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        //实现查询方法，该方法应该返回查询得到的Cursor
        Log.d(TAG, "query: 该方法被调用")
        Log.d(TAG, "query: where 参数为 :$selection")
        return null
    }

    override fun getType(uri: Uri): String? {
        //该方法的返回值代表了该ContentProvider所提供数据的MIME类型
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert: 该方法被调用")
       return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete: 该方法被调用")
       return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(TAG, "update: 该方法被调用")
       return  0
    }
}