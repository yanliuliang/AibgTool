package com.example.myapplication.demo18

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivitySpannableStringBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.suspendCoroutine

class SpannableStringBuilderActivity : BaseActivity<ActivitySpannableStringBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_spannable_string

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView(savedInstanceState: Bundle?) {
        agreePrivate()

        val build = buildSpannedString {
            append("我已详细阅读并同意")
            var start = length
            color(Color.parseColor("#0099FF")) {
                append("《隐私政策》").setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Log.d("TAG", "onClick:1111 ")
                    }
                }, start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            append("和")
            start = length
            color(Color.parseColor("#0099FF")) {
                append("《用户协议》").setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Log.d("TAG", "onClick:2222 ")
                    }
                }, start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        binding.tv2.movementMethod = LinkMovementMethod.getInstance()
        binding.tv2.text = build

        val file = File("")
        file.toUri()

        val layoutParams = binding.tv1.layoutParams as LinearLayout.LayoutParams
        layoutParams.leftMargin = 10
        binding.tv1.layoutParams = layoutParams

        binding.tv1.updateLayoutParams<LinearLayout.LayoutParams> {
            leftMargin = 10
        }
        val list = mutableListOf("1", "2", "3" , "1" ,"2" ,"4")
        val TAG = "TAG"

        val demo1 = list.all { it.length >= 4 }
        Log.d(TAG, "initView: $demo1")

        val demo2 = list.any { it == "2" } // true
        Log.d(TAG, "initView: $demo2")

        val demo3 = list.count { it == "2" }
        Log.d(TAG, "initView: $demo3")

        val demo4 = list.distinct()
        Log.d(TAG, "initView: $demo4")

        //根据条件过滤掉集合中的元素，返回一个新集合，实际是通过 HashSet 保证元素不重复
        val demo5 =list.distinctBy { it.length }
        Log.d(TAG, "initView: $demo5")

        //过滤集合中前 n 个 元素，返回新的集合。n < 0 时抛出 IllegalArgumentException 异常，n = 0 时返回原集合，n >= 集合长度时返回空集合
        val demo6 =list.drop(2)
        Log.d(TAG, "initView: $demo6")


        val demo7 = list.elementAtOrNull(10)
        Log.d(TAG, "initView: $demo7")





        check(list) { }


        binding.btn.setOnClickListener {

        }


    }

    private fun check(list: MutableList<String>, lazyMessage: () -> Unit) {
        lifecycleScope.launch {
            val start = System.currentTimeMillis()
            CoroutineScope(Dispatchers.IO).launch {
                list.forEach {

                }
            }
        }
    }


    private suspend fun String.checkText(): Boolean {
        return suspendCoroutine {

        }

    }

    private fun agreePrivate() {
        val builder = SpannableStringBuilder()
        val text = "我已详细阅读并同意《隐私政策》"
        builder.append(text)
        //设置span点击事件
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //do some thing
            }
        }
        builder.setSpan(clickableSpan, 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置span无下划线
        val noUnderlineSpan = NoUnderlineSpan()
        builder.setSpan(noUnderlineSpan, 9, 15, Spanned.SPAN_MARK_MARK)
        //设置span文字颜色
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#0099FF"))
        builder.setSpan(foregroundColorSpan, 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置可点击
        binding.tv1.movementMethod = LinkMovementMethod.getInstance()
        binding.tv1.text = builder
    }

    class NoUnderlineSpan : UnderlineSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ds.linkColor
            ds.isUnderlineText = false
        }
    }

}