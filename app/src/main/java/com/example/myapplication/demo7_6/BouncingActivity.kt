package com.example.myapplication.demo7_6

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityBouncingBinding


class BouncingActivity :BaseActivity<ActivityBouncingBinding>() {
    companion object{
        // 定义小球大小的常量
        val BALL_SIZE = 50f

        // 定义小球从屏幕上方下落到屏幕底端的总时间
        val FULL_TIME = 1000f
    }

    override val layoutId: Int
        get() = R.layout.activity_bouncing

    override fun initView(savedInstanceState: Bundle?) {
        // 设置该窗口显示MyAnimationView组件
        binding.container.addView(MyAnimationView(this))
    }
    class MyAnimationView(context: Context) : View(context) {
        val balls = ArrayList<ShapeHolder>()
        init {
            // 加载动画资源
            val colorAnim = (AnimatorInflater.loadAnimator(context, R.animator.color_anim) as ObjectAnimator)
            colorAnim.setEvaluator(ArgbEvaluator())
            // 对该View本身应用属性动画
            colorAnim.target = this
            // 开始指定动画
            colorAnim.start()
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent?): Boolean {
            // 如果触碰事件不是按下、移动事件
            if (event?.action != MotionEvent.ACTION_DOWN &&
                event?.action != MotionEvent.ACTION_MOVE)
            {
                return false
            }
            // 在事件发生点添加一个小球（用一个圆形代表）
            val  newBall = addBall(event.x, event.y)
            // 计算小球下落动画开始时的y坐标
            val  startY = newBall.y
            // 计算小球下落动画结束时的y坐标（落到屏幕最下方，就是屏幕高度减去小球高度）
            val  endY = height - BALL_SIZE
            // 获取屏幕高度
            val  h = height
            val  eventY = event.y
            // 计算动画的持续时间
            val  duration = (FULL_TIME * ((h - eventY) / h))
            // 定义小球“落下”的动画
            // 让newBall对象的y属性从事件发生点变化到屏幕最下方
            val  fallAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY)
            // 设置fallAnim动画的持续时间
            fallAnim.duration = duration.toLong()
            // 设置fallAnim动画的插值方式：加速插值
            fallAnim.interpolator = AccelerateInterpolator()
            // 定义小球“压扁”的动画：该动画控制小球的x坐标“左移”半个球
            val  squashAnim1 = ObjectAnimator.ofFloat(newBall, "x", newBall.getX(), newBall.getX() - BALL_SIZE / 2)
            // 设置squashAnim1动画的持续时间
            squashAnim1.duration = (duration / 4).toLong()
            // 设置squashAnim1动画重复1次
            squashAnim1.repeatCount = 1
            // 设置squashAnim1动画的重复方式
            squashAnim1.repeatMode = ValueAnimator.REVERSE
            // 设置squashAnim1动画的插值方式：减速插值
            squashAnim1.interpolator = DecelerateInterpolator()
            // 定义小球“压扁”的动画：该动画控制小球的宽度加倍
            val  squashAnim2 = ObjectAnimator.ofFloat(newBall, "width",
            newBall.width, newBall.width + BALL_SIZE)
            // 设置squashAnim2动画的持续时间
            squashAnim2.duration = (duration / 4).toLong()
            // 设置squashAnim2动画重复1次
            squashAnim2.repeatCount = 1
            // 设置squashAnim2动画的重复方式
            squashAnim2.repeatMode = ValueAnimator.REVERSE
            // 设置squashAnim2动画的插值方式：减速插值
            squashAnim2.interpolator = DecelerateInterpolator()
            // 定义小球“拉伸”的动画：该动画控制小球的y坐标“下移”半个球
            val  stretchAnim1 = ObjectAnimator.ofFloat(newBall, "y", endY, endY + BALL_SIZE / 2)
            // 设置stretchAnim1动画的持续时间
            stretchAnim1.duration = (duration / 4).toLong()
            // 设置stretchAnim1动画重复1次
            stretchAnim1.repeatCount = 1
            // 设置stretchAnim1动画的重复方式
            stretchAnim1.repeatMode = ValueAnimator.REVERSE
            // 设置stretchAnim1动画的插值方式：减速插值
            stretchAnim1.interpolator = DecelerateInterpolator()
            // 定义小球“拉伸”的动画：该动画控制小球的高度减半
            val  stretchAnim2 = ObjectAnimator.ofFloat(newBall, "height", newBall.getHeight(), newBall.getHeight() - BALL_SIZE / 2)
            // 设置stretchAnim2动画的持续时间
            stretchAnim2.duration = (duration / 4).toLong()
            // 设置squashAnim2动画重复1次
            stretchAnim2.repeatCount = 1
            // 设置squashAnim2动画的重复方式
            stretchAnim2.repeatMode = ValueAnimator.REVERSE
            // 设置squashAnim2动画的插值方式：减速插值
            stretchAnim2.interpolator = DecelerateInterpolator()
            // 定义小球“弹起”的动画
            val bounceBackAnim = ObjectAnimator.ofFloat(newBall, "y", endY, startY)
            // 设置持续时间
            bounceBackAnim.duration = duration.toLong()
            // 设置动画的插值方式：减速插值
            bounceBackAnim.interpolator = DecelerateInterpolator()
            // 使用AnimatorSet按顺序播放“下落/压扁&拉伸/弹起动画
            val  bouncer = AnimatorSet()
            // 定义在squashAnim1动画之前播放fallAnim下落动画
            bouncer.play(fallAnim).before(squashAnim1)
            // 由于小球在“屏幕”下方弹起时，小球要被压扁
            // 即：宽度加倍、x坐标左移半个球，高度减半、y坐标下移半个球
            // 因此此处指定播放squashAnim1的同时
            // 还播放squashAnim2、stretchAnim1、stretchAnim2
            bouncer.play(squashAnim1).with(squashAnim2)
            bouncer.play(squashAnim1).with(stretchAnim1)
            bouncer.play(squashAnim1).with(stretchAnim2)
            // 指定播放stretchAnim2动画之后，播放bounceBackAnim弹起动画
            bouncer.play(bounceBackAnim).after(stretchAnim2)
            // 定义对newBall对象的alpha属性执行从1到0的动画（即定义渐隐动画）
            val  fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f)
            // 设置动画持续时间
            fadeAnim.duration = 250
            // 为fadeAnim动画添加监听器
            fadeAnim.addListener(object :AnimatorListenerAdapter()
            {
//                override fun onAnimationEnd(animation: Animator?) {
//                    super.onAnimationEnd(animation)
//                    balls.remove((animation as ObjectAnimator).getTarget())
//                }
            })
            // 再次定义一个AnimatorSet来组合动画
            val  animatorSet = AnimatorSet()
            // 指定在播放fadeAnim之前，先播放bouncer动画
            animatorSet.play(bouncer).before(fadeAnim)
            // 开始播放动画
            animatorSet.start()
            return true

        }

        private fun addBall(x: Float, y: Float): ShapeHolder {
            // 创建一个椭圆
            val circle = OvalShape()
            // 设置该椭圆的宽、高
            circle.resize(BALL_SIZE, BALL_SIZE)
            // 将椭圆包装成Drawable对象
            val drawable = ShapeDrawable(circle)
            // 创建一个ShapeHolder对象
            val shapeHolder = ShapeHolder(drawable)
            // 设置ShapeHolder的x、y坐标
            shapeHolder.x = x - BALL_SIZE / 2
            shapeHolder.y = y - BALL_SIZE / 2
            val red = (Math.random() * 255).toInt()
            val green = (Math.random() * 255).toInt()
            val blue = (Math.random() * 255).toInt()
            // 将red、green、blue三个随机数组合成ARGB颜色
            val color = -0x1000000 + red shl 16 or (green shl 8) or blue
            // 获取drawable上关联的画笔
            val paint: Paint = drawable.paint
            // 将red、green、blue三个随机数除以4得到商值组合成ARGB颜色
            val darkColor = -0x1000000 or (red / 4 shl 16) or (green / 4 shl 8) or blue / 4
            // 创建圆形渐变
            val gradient = RadialGradient(
                37.5f, 12.5f, BALL_SIZE,
                color, darkColor, Shader.TileMode.CLAMP
            )
            paint.setShader(gradient)
            // 为shapeHolder设置paint画笔
            shapeHolder.paint = paint
            balls.add(shapeHolder)
            return shapeHolder
        }

        override fun onDraw(canvas: Canvas?) {
            for (shapeHolder in balls) {
                // 保存canvas的当前坐标系统
                canvas!!.save()
                // 坐标变换：将画布坐标系统平移到shapeHolder的X、Y坐标处
                canvas.translate(shapeHolder.x, shapeHolder.y)
                // 将shapeHolder持有的圆形绘制在Canvas上
                shapeHolder.shape.draw(canvas)
                // 恢复Canvas坐标系统
                canvas.restore()
            }
        }
    }
}