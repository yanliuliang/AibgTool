package com.tool.aibglibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.tool.aibglibrary.R

class FunctionView(mContext: Context?, attrs: AttributeSet?) : LinearLayout(mContext, attrs) {
    /**
     * 左边图标
     */
    private var image: Int? = null

    /**
     * 文案
     */
    private var text: String? = null

    /**
     * 是否展示下划线
     */
    private var showBottomLine: Boolean = false

    /**
     * 文案提示
     */
    private var tvNextTip: TextView? = null

    private var iconType: ImageView? = null

    private var iconNext: ImageView? = null

    private var tvType: TextView? = null

    private var bottomLine: View? = null

    init {
        mContext?.withStyledAttributes(attrs, R.styleable.functionView) {
            image = getResourceIdOrThrow(R.styleable.functionView_functionResId)
            text = getStringOrThrow(R.styleable.functionView_tvTextFunction)
            showBottomLine = getBoolean(R.styleable.functionView_functionShowLine, false)
        }

        val view = LayoutInflater.from(mContext).inflate(R.layout.aige_view_function, this, true)
        tvType = view.findViewById(R.id.tvText)
        iconType = view.findViewById(R.id.imageSetting)
        iconNext = view.findViewById(R.id.imageNext)
        bottomLine = view.findViewById(R.id.viewLine)
        tvNextTip = view.findViewById(R.id.tvNextTip)
        tvType?.text = text
        image?.let { iconType?.setImageResource(it) }
        if (showBottomLine) {
            bottomLine?.isVisible = true
        } else {
            bottomLine?.isInvisible = true
        }
    }

    /**
     * 设置文案提示
     */
    fun setNextTip(text: String) {
        tvNextTip?.text = text
    }

    /**
     * 设置文案字体颜色
     */
    fun setNextTipColor(color: Int) {
        tvNextTip?.setTextColor(color)
    }

    /**
     * 隐藏next箭头
     */
    fun hindNextIcon() {
        iconNext?.isInvisible = true
    }

    /**
     * 隐藏下划线
     */
    fun hindLineView() {
        bottomLine?.isInvisible = true
    }

    /**
     * 显示下划线
     */
    fun showLineView(){
        bottomLine?.isVisible = true
    }
}