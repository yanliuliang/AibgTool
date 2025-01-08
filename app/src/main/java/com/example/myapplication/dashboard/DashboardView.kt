package com.example.myapplication.dashboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.px
import kotlin.math.cos
import kotlin.math.sin


/**
 * @Description:仪表盘
 * @Author: dick
 * @CreateDate: 2023/8/29
 * @Version:
 */

//半径
val RADIUS = 150f.px
const val OPEN_ANGLE = 120f
val LENGTH = 120f.px

val DASH_WIDTH = 2f.px
val DASH_LENGTH = 10f.px

class DashboardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    //刻度规则
    private var pathEffect: PathDashPathEffect? = null

    //轨迹
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //刻度
    private val dash = Path()

    private val path = Path()

    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(path, false)

        val advance = (pathMeasure.length - DASH_WIDTH) / 20f
        pathEffect = PathDashPathEffect(dash, advance, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //画弧
            canvas.drawPath(path, paint)
            paint.pathEffect = pathEffect
            //画刻度
            canvas.drawPath(path, paint)
            paint.pathEffect = null

            //假设第5个刻度
            val scale = 5

            val value =
                Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20 * scale).toDouble())


            canvas.drawLine(
                width / 2f,
                height / 2f,
                width / 2f + LENGTH * cos(value).toFloat(),
                height / 2f + LENGTH * sin(value).toFloat(), paint
            )
        }

    }
}