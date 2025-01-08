package com.example.myapplication.demo43

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.example.myapplication.R

class RadiusShapeTextView(mContext: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(mContext, attrs) {

    init {
        // 获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadiusShapeTextView)
        val solidColor = typedArray.getColor(R.styleable.RadiusShapeTextView_aibg_solidColor, Color.TRANSPARENT)
        val radius = typedArray.getDimensionPixelSize(R.styleable.RadiusShapeTextView_aibg_radius, 0)
        val strokeColor = typedArray.getColor(R.styleable.RadiusShapeTextView_aibg_strokeColor, Color.TRANSPARENT)
        val strokeWidth = typedArray.getDimensionPixelSize(R.styleable.RadiusShapeTextView_aibg_strokeWidth, 0)
        val textViewName = typedArray.getString(R.styleable.RadiusShapeTextView_aibg_textViewName)
        typedArray.recycle()

        // 设置背景
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(solidColor)
        backgroundDrawable.cornerRadius = radius.toFloat()
        backgroundDrawable.setStroke(strokeWidth, strokeColor)
        background = backgroundDrawable

        // 设置文字
        text = textViewName
    }
}