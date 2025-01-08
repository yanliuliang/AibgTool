package com.example.myapplication.demo8

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityTtsBinding
import java.io.File
import java.util.*


class TTSActivity : BaseActivity<ActivityTtsBinding>() {
    private lateinit var tts: TextToSpeech
    private val TAG ="TTSActivity"
    override val layoutId: Int
        get() = com.example.myapplication.R.layout.activity_tts

    override fun initView(savedInstanceState: Bundle?) {

        // 初始化TextToSpeech对象
        tts = TextToSpeech(this) { status ->
            // 如果装载TTS引擎成功
            Log.d(TAG, "initView: $status")
            if (status == TextToSpeech.SUCCESS) {
                tts.setPitch(1.3f) //设置语调，值越大声音越尖锐，越小声音越低沉
                tts.setSpeechRate(0.6f) //设置语速，较低的值会减慢语音（0.5是正常语速的一半），更大的值会加速它（2.0是正常语速的两倍）

                // 设置使用美式英语朗读
                val result = tts.setLanguage(Locale.US)
                // 如果不支持所设置的语言
                if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                    Log.d(TAG, "initView: TTS暂时不支持这种语言的朗读。")
                } else {
                    binding.btnPlay.setOnClickListener {
                    // 执行朗读
                     speak()
                    }
                    binding.btnSave.setOnClickListener {
                        // 将朗读文本的音频记录到指定文件中
                        tts.synthesizeToFile(
                            binding.etInput.text.toString(), null,
                            File("$filesDir/sound.wav"), "record"
                        )
                        Log.d(TAG, "initView: 声音记录成功！")
                    }
                }
            }

        }
    }

    private fun speak() {
        tts.speak(
            binding.etInput.text.toString(),
            TextToSpeech.QUEUE_ADD, null, "speech"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}