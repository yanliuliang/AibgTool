package com.example.myapplication.demo41

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.myapplication.DensityUtil
import com.example.myapplication.R
import kotlin.math.pow
import kotlin.math.sqrt

class FlowChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val linePaint: Paint = Paint()
    private val lineBgPaint = Paint()


    private val circleRadius = 100f

    //走过的剧情数量
    private var selectPlot = 3

    //短线
    private val shortLineLength = 200f

    //长线
    private var longLineLength = 400f



    //节点
    private val paintInner: Paint = Paint()
    private val paintOuter: Paint = Paint()

    private val paintSmallOval: Paint = Paint()

    private val nodeRadio = DensityUtil.dp2px(7f).toFloat()

    private val smallCircleRadio = 6f

    //文字顶部距离
    private val textHeight = 50

    //画线开始
    private val textY = 70

    private var bitmap: Bitmap? = null


    private val textList = arrayListOf<String>(
        "破裙子",
        "女人的质问",
        "转移话题",
        "赞美画册",
        "相识机缘",
        "赏画达人",
        "告别方式",
        "与谁共进早餐",
        "消失的记忆"
    )


    // 添加 List 用于存储圆的中心坐标
    private val circleCentersList: MutableList<Pair<Float, Float>> = mutableListOf()

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bom_f17)
        bitmap = getRoundedBitmap(bitmap, (circleRadius * 2).toInt())
        initPaints()
    }

    private fun initPaints() {
        // 为圆形创建画笔
        circlePaint.color = Color.BLACK
        circlePaint.strokeWidth = 5f
        circlePaint.style = Paint.Style.STROKE

        // 为文字创建画笔
        textPaint.color = Color.WHITE
        textPaint.textSize = DensityUtil.dp2px(16f).toFloat()

        val shadowColor = Color.parseColor("#9477FF")
        val shadowDx = 1.5f
        val shadowDy = 1.5f
        val shadowRadius = 2f
        textPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)

        // 设置文本样式为 bold
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)


        // 为线创建画笔
        linePaint.color = Color.parseColor("#8A6DFF")
        linePaint.strokeWidth = DensityUtil.dp2px(3f).toFloat()

        lineBgPaint.color = Color.parseColor("#C3B8FC")
        lineBgPaint.style = Paint.Style.FILL
        lineBgPaint.strokeWidth = DensityUtil.dp2px(6f).toFloat()

        // 内圈白色
        paintInner.color = Color.WHITE
        paintInner.style = Paint.Style.FILL

        // 外圈蓝色
        paintOuter.color = Color.parseColor("#9477FF")
        paintOuter.style = Paint.Style.FILL

        //为线的拐弯处补全
        paintSmallOval.color = Color.parseColor("#B6B6EE")
        paintSmallOval.style = Paint.Style.FILL


    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 计算两个圆心的坐标
        val centerX1 = circleRadius
        val centerY1 = circleRadius
        val centerX2 = width - 200f
        val centerY2 = centerY1 + 600

        // 绘制第一个圆
        bitmap?.let {
            canvas.drawBitmap(
                it,
                centerX1 - circleRadius,
                centerY1 - circleRadius,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(centerX1 - circleRadius, centerY1 - circleRadius))

        // 绘制第一个文字
        val textWidth1 = textPaint.measureText(textList[0])

        canvas.drawText(
            textList[0],
            centerX1 - textWidth1 / 2,
            centerY1 + circleRadius + textHeight,
            textPaint
        )

        // 连线
        drawLine(
            canvas,
            centerX1,
            centerY1 + circleRadius + textY,
            centerX1,
            centerY1 + circleRadius + shortLineLength,
            0
        )
        drawLine(
            canvas, centerX1,
            centerY1 + circleRadius + shortLineLength,
            centerX2,
            centerY1 + circleRadius + shortLineLength, 0
        )
        drawSmallCircle(canvas, centerX1 , centerY1 + circleRadius + shortLineLength , 0)

        drawLine(
            canvas, centerX2,
            centerY1 + circleRadius + shortLineLength,
            centerX2,
            centerY2 + circleRadius - circleRadius * 2, 0
        )
        drawSmallCircle(canvas, centerX2, centerY1 + circleRadius + shortLineLength, 0)


        // 绘制第二个圆
        bitmap?.let {
            canvas.drawBitmap(
                it,
                centerX2 - circleRadius,
                centerY2 - circleRadius,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(centerX2 - circleRadius, centerY2 - circleRadius))

        // 绘制第二个文字
        val textWidth2 = textPaint.measureText(textList[1])
        canvas.drawText(
            textList[1],
            centerX2 - textWidth2 / 2,
            centerY2 + circleRadius + 50,
            textPaint
        )

        drawLine(
            canvas,
            centerX2,
            centerY2 + circleRadius + textY,
            centerX2,
            centerX2 + circleRadius + shortLineLength,
            1
        )


        // 连线
        var xDistance = centerX2 - shortLineLength
        var yDistance = centerX2 + circleRadius + shortLineLength - nodeRadio / 2

        drawLine(canvas, centerX2, yDistance, xDistance, yDistance, 1)
        //绘制节点
        drawNode(canvas, centerX2, centerX2 + circleRadius + shortLineLength, 1)

        // 绘制第3个圆
        xDistance -= circleRadius * 2
        yDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        // 绘制第3个文字
        val textWidth3 = textPaint.measureText(textList[2])
        canvas.drawText(
            textList[2],
            xDistance + (circleRadius - textWidth3 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )
        circleCentersList.add(getCenter(xDistance, yDistance))

        // 连线
        yDistance += circleRadius

        drawLine(
            canvas, xDistance,
            yDistance,
            circleRadius,
            yDistance, 2
        )

        // 连线
        xDistance = circleRadius
        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance,
            yDistance + longLineLength, 2
        )

        drawSmallCircle(canvas, xDistance, yDistance, 2)

        // 绘制第4个圆
        yDistance += longLineLength
        xDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }

        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第4个文字
        val textWidth4 = textPaint.measureText(textList[3])
        canvas.drawText(
            textList[3],
            xDistance + (circleRadius - textWidth4 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )

        // 连线
        yDistance += circleRadius
        drawLine(
            canvas, xDistance + circleRadius * 2,
            yDistance,
            centerX2,
            yDistance, 3
        )
        xDistance = centerX2


        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance,
            yDistance + longLineLength, 3
        )

        if (selectPlot > 3) {
            paintOuter.color = Color.parseColor("#9477FF")
        } else {
            paintOuter.color = Color.parseColor("#B6B6EE")
        }

        // 绘制外圈圆
        canvas.drawCircle(xDistance, yDistance, nodeRadio + 10, paintOuter)
        // 绘制内圈圆
        canvas.drawCircle(xDistance, yDistance, nodeRadio, paintInner)


        // 绘制第5个圆
        yDistance += longLineLength
        xDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第5个文字
        val textWidth5 = textPaint.measureText(textList[4])
        canvas.drawText(
            textList[4],
            xDistance + (circleRadius - textWidth5 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )

        // 连线
        yDistance += circleRadius

        drawLine(
            canvas, xDistance,
            yDistance,
            circleRadius,
            yDistance,
            4
        )

        xDistance = circleRadius

        // 绘制第6个圆
        yDistance -= circleRadius
        xDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第6个文字
        val textWidth6 = textPaint.measureText(textList[5])
        canvas.drawText(
            textList[5],
            xDistance + (circleRadius - textWidth6 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )

        yDistance += circleRadius * 2 + textY
        xDistance += circleRadius

        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance,
            yDistance + shortLineLength, 5
        )

        yDistance += shortLineLength

        drawLine(
            canvas, xDistance,
            yDistance,
            centerX2,
            yDistance, 5
        )

        drawSmallCircle(canvas, xDistance, yDistance, 5)

        xDistance = centerX2

        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance,
            yDistance + shortLineLength, 5
        )
        drawSmallCircle(canvas, xDistance, yDistance, 5)

        yDistance += shortLineLength

        // 绘制第7个圆
        xDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第7个文字
        val textWidth7 = textPaint.measureText(textList[6])
        canvas.drawText(
            textList[6],
            xDistance + (circleRadius - textWidth7 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )

        xDistance += circleRadius

        yDistance += circleRadius

        // 连线
        drawLine(
            canvas, xDistance,
            yDistance + circleRadius + textY,
            xDistance,
            yDistance + circleRadius + longLineLength, 6
        )

        // 连线
        yDistance += circleRadius + longLineLength + nodeRadio / 2


        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance - shortLineLength,
            yDistance, 6
        )
        //绘制节点
        drawNode(canvas, xDistance, yDistance, 6)

        xDistance -= shortLineLength
        yDistance -= circleRadius

        // 绘制第8个圆
        xDistance -= circleRadius * 2
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第8个文字
        val textWidth8 = textPaint.measureText(textList[7])
        canvas.drawText(
            textList[7],
            xDistance + (circleRadius - textWidth8 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )

        yDistance += circleRadius

        drawLine(
            canvas, xDistance,
            yDistance,
            centerX1,
            yDistance, 7
        )


        xDistance = centerX1

        drawLine(
            canvas, xDistance,
            yDistance,
            xDistance,
            yDistance + shortLineLength, 7
        )

        drawSmallCircle(canvas, xDistance, yDistance, 7)
        yDistance += shortLineLength

        // 绘制第9个圆
        xDistance -= circleRadius
        bitmap?.let {
            canvas.drawBitmap(
                it,
                xDistance,
                yDistance,
                circlePaint
            )
        }
        circleCentersList.add(getCenter(xDistance, yDistance))
        // 绘制第9个文字
        val textWidth9 = textPaint.measureText(textList[8])
        canvas.drawText(
            textList[8],
            xDistance + (circleRadius - textWidth9 / 2),
            yDistance + circleRadius * 2 + 50,
            textPaint
        )
    }

    /**
     * 绘制实线拐弯的ui
     */
    private fun drawSmallCircle(canvas: Canvas, centerX1: Float, fl: Float, index: Int) {
        if (selectPlot > index) {
            paintSmallOval.color = Color.parseColor("#9477FF")
        } else {
            paintSmallOval.color = Color.parseColor("#B6B6EE")
        }
        canvas.drawCircle(centerX1, fl, smallCircleRadio +2, lineBgPaint)
        canvas.drawCircle(centerX1, fl, smallCircleRadio, paintSmallOval)
    }

    /**
     * 画线判断剧情走的进度
     */
    private fun drawLine(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float, index: Int) {
        if (selectPlot > index) {
            linePaint.color = Color.parseColor("#8A6DFF")
            canvas.drawLine(
                x1,
                y1,
                x2,
                y2,
                lineBgPaint
            )
        } else {
            linePaint.color = Color.parseColor("#B6B6EE")
        }
        canvas.drawLine(
            x1,
            y1,
            x2,
            y2,
            linePaint
        )


    }

    /**
     * 设置走到的故事性进度
     */
    fun setPlot(selectPlot: Int) {
        this.selectPlot = selectPlot
        invalidate()
    }

    private fun getCenter(top: Float, left: Float): Pair<Float, Float> {
        return Pair(top + circleRadius, left + circleRadius)
    }

    // 处理触摸事件，判断是否点击了圆
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                // 判断点击位置是否在圆内
                for (index in 1..circleCentersList.size) {
                    if (isInsideCircle(x, y, index, circleRadius) && index - 1 <= selectPlot) {
//                        Toast.makeText(context, "点击了$index 个圆", Toast.LENGTH_SHORT).show()
                        Log.d("打印yll", "onTouchEvent() + 点击了$index 个圆")
                    } else {
                        if (isInsideCircle(x, y, index, circleRadius)) {
                            Log.d("打印yll", "onTouchEvent() + 点击了$index 个圆 故事性没走到")
                        }

                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    // 判断点击位置是否在圆内
    private fun isInsideCircle(x: Float, y: Float, index: Int, radius: Float): Boolean {
        val centerX = circleCentersList[index - 1].first
        val centerY = circleCentersList[index - 1].second
        val distance = sqrt((x - centerX).pow(2) + (y - centerY).pow(2))
        return distance <= radius
    }

    private fun drawNode(canvas: Canvas, x: Float, y: Float, index: Int) {
        if (selectPlot > index) {
            paintOuter.color = Color.parseColor("#9477FF")
        } else {
            paintOuter.color = Color.parseColor("#B6B6EE")
        }

        // 绘制外圈圆
        canvas.drawCircle(x, y - nodeRadio / 2, nodeRadio + 10, paintOuter)
        // 绘制内圈圆
        canvas.drawCircle(x, y - nodeRadio / 2, nodeRadio, paintInner)

    }

    private fun getRoundedBitmap(bitmap: Bitmap?, radius: Int): Bitmap {
        val result = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = 0xff424242.toInt()

        val path = Path()
        path.addCircle(radius / 2f, radius / 2f, radius / 2f, Path.Direction.CCW)
        canvas.clipPath(path)
        bitmap?.let {
            canvas.drawBitmap(
                it,
                null,
                RectF(0f, 0f, radius.toFloat(), radius.toFloat()),
                paint
            )
        }

        return result
    }
}
