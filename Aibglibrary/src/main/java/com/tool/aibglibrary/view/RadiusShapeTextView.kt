package com.tool.aibglibrary.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.tool.aibglibrary.R

/**
 * @Description: 自定义textView带圆角背景
 * @Author: dick
 * @CreateDate: 2024/4/28
 * @Version:
 */
/**
 *   <com.nongchanpin.aibglibrary.view.RadiusShapeTextView
 *             android:layout_width="wrap_content"
 *             android:layout_height="wrap_content"
 *             android:padding="20dp"
 *             android:textColor="@color/white"
 *             app:aibg_radius="20dp"
 *             app:aibg_solidColor="@color/black"
 *             app:aibg_strokeWidth="2dp"
 *             app:aibg_textViewName="你好"
 *             app:layout_constraintStart_toStartOf="parent"
 *             app:layout_constraintTop_toTopOf="parent" />
 */
class RadiusShapeTextView(mContext: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(mContext, attrs) {

    init {
        // 获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadiusShapeTextView)
        val solidColor =
            typedArray.getColor(R.styleable.RadiusShapeTextView_aibg_solidColor, Color.TRANSPARENT)
        val radius =
            typedArray.getDimensionPixelSize(R.styleable.RadiusShapeTextView_aibg_radius, 0)
        val strokeColor =
            typedArray.getColor(R.styleable.RadiusShapeTextView_aibg_strokeColor, Color.TRANSPARENT)
        val strokeWidth =
            typedArray.getDimensionPixelSize(R.styleable.RadiusShapeTextView_aibg_strokeWidth, 0)
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