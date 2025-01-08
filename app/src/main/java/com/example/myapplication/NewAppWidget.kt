package com.example.myapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    var context: Context? = null
    var appWidgetManager: AppWidgetManager? = null
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (bean in appWidgetIds) {
            MMKVUtil.addAppWidgetId(bean)
        }
        Log.d("NewAppWidget", "onUpdate: ${MMKVUtil.getAppWidgetId()}")

        this.context = context
        this.appWidgetManager = appWidgetManager
        val packName = context.packageName
        val views = RemoteViews(packName, R.layout.new_app_widget)

        val intent = Intent("${packName}.MY_BROADCAST")
        intent.component =
            ComponentName(packName, "${packName}.MyBroadcastService")
        val servicePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.parent, servicePendingIntent)
        val componentName = ComponentName(context, NewAppWidget::class.java)
        views.setTextViewText(R.id.appwidget_text, "已敲${MMKVUtil.getClickAllCount()}次")
        // 调用AppWidgetManager将remoteViews添加到ComponentName中
        appWidgetManager.updateAppWidget(componentName, views)
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (appWidgetIds != null) {
            var appWidgetId = MMKVUtil.getAppWidgetId()
            for (bean in appWidgetIds) {

                Log.d("onDeleted", "onDeleted:删除前 ：${appWidgetId}  删除的id :$bean ")
                appWidgetId = appWidgetId.replace("$bean-", "")
                Log.d("onDeleted", "onDeleted:删除后 ${MMKVUtil.getAppWidgetId()}")
            }
            MMKVUtil.saveAppWidgetId(appWidgetId)
        }

    }
}
