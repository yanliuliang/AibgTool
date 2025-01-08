package com.example.myapplication.demo19

import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityTextOnBitmapBinding

class TextOnBitmapActivity : BaseActivity<ActivityTextOnBitmapBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_text_on_bitmap

    override fun initView(savedInstanceState: Bundle?) {

        binding.btn.setOnClickListener {
            val drawable = resources.getDrawable(R.drawable.back).toBitmap()
            val text = "12345678910"
            val stringText = StringBuffer()
            for (i in 0..100) {
                stringText.append(text)
            }
            val draw =ImageUtil.drawTextToLeftTop(
                this,
                drawable,
                stringText.toString(),
                10,
                R.color.white,
                10,
                10
            )
            binding.image.setImageBitmap(draw)
        }
    }
}