package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.bottomDemo.BottomActivitity
import com.example.myapplication.colorful.CalendarActivity
import com.example.myapplication.dashboard.DashViewShowActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.demo10_1.BindActivity
import com.example.myapplication.demo10_3.TelephonyManagerActivity
import com.example.myapplication.demo10_6.VibratorActivity
import com.example.myapplication.demo10_8.MusicActivity
import com.example.myapplication.demo11_1.MediaPlayActivity
import com.example.myapplication.demo15_2.SensorActivity
import com.example.myapplication.demo15_3.CompassActivity
import com.example.myapplication.demo15_3.CrazyitActivity
import com.example.myapplication.demo16_3.GPSActivity
import com.example.myapplication.demo16_4.ProximityAlertActivity
import com.example.myapplication.demo17.SpeechRecognizerActivity
import com.example.myapplication.demo18.SpannableStringBuilderActivity
import com.example.myapplication.demo19.TextOnBitmapActivity
import com.example.myapplication.demo1_1.LiveWallPapersActivity
import com.example.myapplication.demo20.ChangeFragmentActivity
import com.example.myapplication.demo2_1.ConfigurationActivity
//import com.example.myapplication.demo3.FfmpegActivity
import com.example.myapplication.demo39.RecordAndSendMessagesAct
import com.example.myapplication.demo4.IntentActivity
import com.example.myapplication.demo41.NodeActivity
import com.example.myapplication.demo42.CeilingActivity
import com.example.myapplication.demo43.RadiusActivityDemo
import com.example.myapplication.demo5.ArrayActivity
import com.example.myapplication.demo7_1.ImageActivity
import com.example.myapplication.demo7_1.ImageMoveAcitivity
import com.example.myapplication.demo7_2.PinballActivity
import com.example.myapplication.demo7_3.MatrixActivity
import com.example.myapplication.demo7_3.MeShImageActivity
import com.example.myapplication.demo7_4.BlastActivity
import com.example.myapplication.demo7_5.ButterflyActivity
import com.example.myapplication.demo7_5.TweenTestActivity
import com.example.myapplication.demo7_6.BouncingActivity
import com.example.myapplication.demo7_7.FishActivity
import com.example.myapplication.demo8.TTSActivity
import com.example.myapplication.mpChat.MpChatActivity
import com.example.myapplication.xiechengDemo.Main2Activity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = ArrayList<String>()
        list.add("Android系统桌面") //1 动态壁纸
        list.add("Configuration") //2 响应系统设置的事件
        list.add("ffmpeg的基本使用")  //3
        list.add("ComponentName跳转") //4
        list.add("资源文件") //5资源文件的处理
        list.add("界面适配") //6
        list.add("图片处理") //7
        list.add("图片移动处理") //8
        list.add("弹球游戏") //9
        list.add("图片matrix切换")  //10
        list.add("扭曲的图片") //11
        list.add("指定点爆炸")  //12
        list.add("补间动画的实现")  //13
        list.add("蝴蝶飞舞动画") //14
        list.add("大珠小珠落玉盘动画")  //15
        list.add("游鱼动画-基于SurfaceView") //16
        list.add("底部导航切换按钮")  //17
        list.add("文字转语音") //18
        list.add("Binder链接") //19
        list.add("电话网络管理") //20
        list.add("手机震动")  //21
        list.add("简易的音乐播放器")  //22
        list.add("媒体播放界面")  //23
        list.add("一个测试") //24
        list.add("日历") //25
        list.add("统计图") //26
        list.add("android 传感器") //27
        list.add("指南针")  //28
        list.add("水平仪")  //29
        list.add("GPS定位") //30
        list.add("距离检测") //31
        list.add("闹钟管理") //32
        list.add("录音检测") //33
        list.add("SpannableStringBuilder实现富文本") //34
        list.add("图片添加文字实现截图") //35
        list.add("改变fragment") //36
        list.add("仪表盘绘制")  //37
        list.add("吸顶滑动") //38
        list.add("录音发消息") //39
        list.add("spine动画") //40
        list.add("自定义节点view") //41
        list.add("吸顶效果") //42
        list.add("生命周期监听") //43
        list.add("自定义view") //44
        binding.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            jumpToActivity(position + 1)
        }

        Log.d("TAG", "onCreate: ${getAndroidId()}")

    }

    @SuppressLint("HardwareIds")
    public fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }


    private fun jumpToActivity(position: Int) {
        when (position) {
            1 -> {
                startActivity<LiveWallPapersActivity>(this)
            }

            2 -> {
                startActivity<ConfigurationActivity>(this)
            }

            3 -> {
                "功能废弃".toast()
//                startActivity<FfmpegActivity>(this)
            }

            4 -> {
                startActivity<IntentActivity>(this)
            }

            5 -> {
                startActivity<ArrayActivity>(this)
            }

            7 -> {
                startActivity<ImageActivity>(this)
            }

            8 -> {
                startActivity<ImageMoveAcitivity>(this)
            }

            9 -> {
                startActivity<PinballActivity>(this)
            }

            10 -> {
                startActivity<MatrixActivity>(this)
            }

            11 -> {
                startActivity<MeShImageActivity>(this)
            }

            12 -> {
                startActivity<BlastActivity>(this)
            }

            13 -> {
                startActivity<TweenTestActivity>(this)
            }

            14 -> {
                startActivity<ButterflyActivity>(this)
            }

            15 -> {
                startActivity<BouncingActivity>(this)
            }

            16 -> {
                startActivity<FishActivity>(this)
            }

            17 -> {
                startActivity<BottomActivitity>(this)
            }

            18 -> {
                startActivity<TTSActivity>(this)
            }

            19 -> {
                startActivity<BindActivity>(this)
            }

            20 -> {
                startActivity<TelephonyManagerActivity>(this)
            }

            21 -> {
                startActivity<VibratorActivity>(this)
            }

            22 -> {
                startActivity<MusicActivity>(this)
            }

            23 -> {
                startActivity<MediaPlayActivity>(this)
            }

            24 -> {
                startActivity<SensorActivity>(this)
            }

            25 -> {
                startActivity<CalendarActivity>(this)
            }

            26 -> {
                startActivity<MpChatActivity>(this)
            }

            27 -> {
                startActivity<SensorActivity>(this)
            }

            28 -> {
                startActivity<CompassActivity>(this)
            }

            29 -> {
                startActivity<CrazyitActivity>(this)
            }

            30 -> {
                startActivity<GPSActivity>(this)
            }

            31 -> {
                startActivity<ProximityAlertActivity>(this)
            }

            33 -> {
                startActivity<SpeechRecognizerActivity>(this)
            }

            34 -> {
                startActivity<SpannableStringBuilderActivity>(this)
            }

            35 -> {
                startActivity<TextOnBitmapActivity>(this)
            }

            36 -> {
                startActivity<ChangeFragmentActivity>(this)
            }

            37 -> {
                startActivity<DashViewShowActivity>(this)
            }

            38 -> {
                startActivity<CoordinatorLayoutActivity>(this)
            }

            39 -> {
                startActivity<RecordAndSendMessagesAct>(this)
            }

            40 -> {
//                startActivity<SpineActivity>(this)
            }

            41 -> {
                startActivity<NodeActivity>(this)
            }

            42 -> {
                startActivity<CeilingActivity>(this)
            }

            43 -> {
                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                startActivityForResult(intent, 1001)
            }

            44 -> {
                startActivity<RadiusActivityDemo>(this)
            }
        }

    }

    private inline fun <reified T> startActivity(context: Context?) {
        val intent = Intent(context, T::class.java)
        context?.startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            lifecycleScope.launchWhenResumed {
                Log.d("打印yll", "onActivityResult() + launchWhenResumed")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("打印yll", "onResume() + ")
    }


}