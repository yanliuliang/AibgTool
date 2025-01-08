package com.example.myapplication.demo17

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivitySpeechRecognizerBinding
import java.util.Locale


class SpeechRecognizerActivity : BaseActivity<ActivitySpeechRecognizerBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_speech_recognizer
    private var speechRecognizer: SpeechRecognizer? = null
    override fun initView(savedInstanceState: Bundle?) {
        binding.tvStart.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            speechRecognizer!!.startListening(intent)
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                // 当识别器准备好接收语音输入时调用
                Log.d("TAG", "onReadyForSpeech:当识别器准备好接收语音输入时调用 ")
            }

            override fun onBeginningOfSpeech() {
                // 当用户开始说话时调用
                Log.d("TAG", "onBeginningOfSpeech: 当用户开始说话时调用")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d("TAG", "onRmsChanged: rmsdB:$rmsdB")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d("TAG", "onBufferReceived:buffer:$buffer ")
            }

            override fun onEndOfSpeech() {
                //
                Log.d("TAG", "onEndOfSpeech: 当用户停止说话时调用")
            }

            override fun onError(error: Int) {
                Log.d("TAG", "onError: $error")
            }

            override fun onResults(results: Bundle?) {
                Log.d("TAG", "onResults: results$results")
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d("TAG", "onPartialResults: partialResults:partialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d("TAG", "onEvent: eventType$eventType params$params")
            }
        })
    }

}