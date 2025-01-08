package com.example.myapplication.demo39

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Vibrator
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityRecordAndSendMessagesBinding
import com.example.myapplication.toast
import com.tbruyelle.rxpermissions3.RxPermissions

class RecordAndSendMessagesAct : BaseActivity<ActivityRecordAndSendMessagesBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_record_and_send_messages

    private var vibrator: Vibrator? = null

    override fun initView(savedInstanceState: Bundle?) {
        // 获取 Vibrator 实例
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    @SuppressLint("ClickableViewAccessibility", "CheckResult")
    override fun initListener() {
        super.initListener()
        binding.btnSend.setOnLongClickListener {

            val rxPermissions = RxPermissions(this)
            rxPermissions
                .request(Manifest.permission.RECORD_AUDIO)
                .subscribe { granted ->
                    if (granted) {
                        vibratorStart()
                        AudioRecorder.startRecording(AudioRecorder.absolutePath) {
                            binding.btnSend.text = "开始录制 $it s"
                        }
                    }
                }


            true
        }

        binding.btnSend.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    // 在按钮松手时触发的操作
                    // 此处可以处理按钮松手后的逻辑
                    AudioRecorder.stopRecording {
                        "发送成功".toast()
                        binding.btnSend.text = "长按录音发送"
                        addView(it)
                    }

                    true
                }

                else -> false
            }
        }
    }

    private fun addView(it: String) {
        val textView = TextView(this)
        textView.text = it
        val layoutParams = LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = layoutParams
        binding.linear.addView(textView)
    }

    private fun vibratorStart() {
        // 停止震动
        vibrator?.cancel()
        // 短震动
        vibrator?.vibrate(400)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}