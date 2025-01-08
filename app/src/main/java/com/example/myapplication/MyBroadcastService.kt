package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

class MyBroadcastService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val appWidgetId = MMKVUtil.getAppWidgetId().split("-")
        for (bean in appWidgetId) {
            if (bean.isNotBlank()) {
                val appWidgetManager =
                    context?.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager
                val views = RemoteViews(context.packageName, R.layout.new_app_widget)
                views.removeAllViews(R.id.muyu_rl)

                val remoteViews2 = RemoteViews(context.packageName, R.layout.anim_layout)
                remoteViews2.setImageViewResource(R.id.widget_muyu_iv, R.drawable.ic_launcher_logo)
                views.addView(R.id.muyu_rl, remoteViews2)
                views.setTextViewText(R.id.appwidget_text, "已敲${MMKVUtil.getClickAllCount()}次")
                appWidgetManager.updateAppWidget(bean.toInt(), views)

            }
        }
        MMKVUtil.saveClickAllCount()
    }

}
