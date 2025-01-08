package com.example.myapplication.demo14;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.myapplication.R;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */


/**
 * 桌面时间控件
 */
public class LedClock extends AppWidgetProvider {
    private Timer timer = new Timer();

    private Long startTime = 1529856000000L;
    private AppWidgetManager appWidgetManager;
    private Context context;
    // 将0~9的液晶数字图片定义成数组

    static class MyHandler extends Handler {
        private WeakReference<LedClock> ledClock;

        public MyHandler(WeakReference<LedClock> ledClock) {
            this.ledClock = ledClock;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                RemoteViews views = new RemoteViews(ledClock.get().context.getPackageName(), R.layout.clock);
                // 定义SimpleDateFormat对象
                long nowTime = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String timeStr = df.format(new Date(nowTime));
                views.setTextViewText(R.id.tvTime, timeStr);

//                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm.SSS");
//
//                String date1 = df1.format(new Date(System.currentTimeMillis()));
//                String date2 = df1.format(new Date(ledClock.get().startTime));
//
//                try {
//                    Date d1 = df1.parse(date2);
//                    Date d2 = df1.parse(date1);
//                    views.setTextViewText(R.id.tvLoveTime, "到现在已经:"
//                            + ((((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000))) / 365) + "年"
//                            + ((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000 ))%12 + "月"
//                            + ((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000)) + "天"
//                            + (((d2.getTime() - d1.getTime()) / (60 * 60 * 1000)) % 24) + "小时"
//                            + ((d2.getTime() - d1.getTime()) / (60 * 1000)) % 60 + "分钟"
//                            + ((d2.getTime() - d1.getTime()) / 1000) % 60 + "秒"
//
//                            + (((d2.getTime() - d1.getTime())) % 1000) + "毫秒");
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }


                // 将AppWidgetProvider子类实例包装成ComponentName对象
                ComponentName componentName = new ComponentName(ledClock.get().context, LedClock.class);
                // 调用AppWidgetManager将remoteViews添加到ComponentName中
                ledClock.get().appWidgetManager.updateAppWidget(componentName, views);
            }
            super.handleMessage(msg);
        }
    }

    private Handler handler = new MyHandler(new WeakReference<>(this));

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.appWidgetManager = appWidgetManager;
        this.context = context;
        // 定义计时器
        timer = new Timer();
        // 启动周期性调度
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 发送空消息，通知界面更新
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 1000);
    }
}
