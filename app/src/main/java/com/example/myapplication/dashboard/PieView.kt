package com.example.myapplication.dashboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.px
import kotlin.math.cos
import kotlin.math.sin


class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val RADIUS = 150f.px
    private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
    private val COLORS = listOf(
        Color.parseColor("#468124"),
        Color.parseColor("#553831"),
        Color.parseColor("#c00a51"),
        Color.parseColor("#00a2bc")
    )

    private val OFFSET_LENGTH = 20f.px

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            canvas?.save()
            val value = Math.toRadians((startAngle + angle / 2f).toDouble()).toFloat()
            canvas?.translate(OFFSET_LENGTH * cos(value), OFFSET_LENGTH * sin(value))
            canvas?.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            startAngle += angle
            canvas?.restore()
        }

    }
}