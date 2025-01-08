package com.example.myapplication.demo10_8

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.LayoutMusicBinding


class MusicActivity : BaseActivity<LayoutMusicBinding>() {
    override val layoutId: Int
        get() = R.layout.layout_music
    companion object{
        const val CTL_ACTION = "org.crazyit.action.CTL_ACTION"
        const val UPDATE_ACTION = "org.crazyit.action.UPDATE_ACTION"
    }


    private var activityReceiver: ActivityReceiver? = null

    // 定义音乐的播放状态，0x11代表没有播放；0x12代表正在播放；0x13代表暂停
    var status = 0x11
    var titleStrs = arrayOf("心愿", "约定", "美丽新世界")
    var authorStrs = arrayOf("未知艺术家", "周蕙", "伍佰")
    override fun initView(savedInstanceState: Bundle?) {
        val listener: View.OnClickListener = View.OnClickListener { source ->
            // 创建Intent
            val intent = Intent(CTL_ACTION)
            intent.setPackage(packageName)
            when (source.id) {
                R.id.play -> intent.putExtra("control", 1)
                R.id.stop -> intent.putExtra("control", 2)
            }
            // 发送广播，将被Service组件中的BroadcastReceiver接收到
            sendBroadcast(intent)
        }
        // 为两个按钮的单击事件添加监听器
        binding.play.setOnClickListener(listener)
        binding.stop.setOnClickListener(listener)
        activityReceiver = ActivityReceiver()
        // 创建IntentFilter
        val filter = IntentFilter()
        // 指定BroadcastReceiver监听的Action
        filter.addAction(UPDATE_ACTION)
        // 注册BroadcastReceiver
        registerReceiver(activityReceiver, filter)
        val intent = Intent(this, MusicService::class.java)
        // 启动后台Service
        startService(intent)
    }

    // 自定义的BroadcastReceiver，负责监听从Service传回来的广播
    inner class ActivityReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // 获取Intent中的update消息，update代表播放状态
            val update = intent.getIntExtra("update", -1)
            // 获取Intent中的current消息，current代表当前正在播放的歌曲
            val current = intent.getIntExtra("current", -1)
            if (current >= 0) {
                binding.title.text = titleStrs[current]
                binding.author.text = authorStrs[current]
            }
            when (update) {
                0x11 -> {
                    binding.play.setImageResource(R.drawable.play)
                    status = 0x11
                }
                0x12 -> {
                    // 在播放状态下设置使用暂停图标
                    binding.play.setImageResource(R.drawable.pause)
                    // 设置当前状态
                    status = 0x12
                }
                0x13 -> {
                    // 在暂停状态下设置使用播放图标
                    binding.play.setImageResource(R.drawable.play)
                    // 设置当前状态
                    status = 0x13
                }
            }
        }
    }
}