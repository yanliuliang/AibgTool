package com.example.myapplication.demo39

import android.media.MediaRecorder
import com.example.myapplication.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/**
 * @Description:录音机
 * @Author: dick
 * @CreateDate: 2023/11/29
 * @Version:
 */
object AudioRecorder {

    private var mediaRecorder: MediaRecorder? = null

    private var timer: Timer? = null

    //录制时长
    private var recorderTime = 0

    /**
     * 存储路径
     */
    val absolutePath: String
        get() = App.instance.filesDir.absolutePath + "/${System.currentTimeMillis()}.mp3"

    private var savePath: String = ""


    fun startRecording(savePath: String, block: (time: Int) -> Unit) {
        this.savePath = savePath
        mediaRecorder = MediaRecorder()
        mediaRecorder?.apply {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(savePath)
            prepare()
            start()
            initTimer(block)
        }
    }

    private fun initTimer(block: (time: Int) -> Unit) {
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                recorderTime += 1
                CoroutineScope(Dispatchers.Main).launch {
                    block.invoke(recorderTime)
                }
            }
        }
        timer?.schedule(timerTask, 0, 1000)
    }

    fun stopRecording(block: (savePath: String) -> Unit) {
        mediaRecorder?.apply {
            stop()
            reset()
            release()
            timer?.cancel()
            timer = null
            recorderTime = 0
            block.invoke(savePath)
        }
    }
}
