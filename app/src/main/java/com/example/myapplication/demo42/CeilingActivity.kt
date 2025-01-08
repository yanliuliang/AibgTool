package com.example.myapplication.demo42


import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.myapplication.DensityUtil
import com.example.myapplication.R
import com.gyf.immersionbar.ImmersionBar


class CeilingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ceiling)
        ImmersionBar.with(this).statusBarDarkFont(true).init()

        val height = (findViewById<ImageView>(R.id.image).drawable as BitmapDrawable).bitmap.height
        val width = (findViewById<ImageView>(R.id.image).drawable as BitmapDrawable).bitmap.width
        Log.d("打印yll", "onCreate() + $width  $height")

        loadCloseUpImageAndAnimate("",findViewById<ImageView>(R.id.image))
    }

    private fun loadCloseUpImageAndAnimate(imageUrl: String?, imageView: ImageView) {
        try {
            val width = (imageView.drawable as BitmapDrawable).bitmap.width
            val height = (imageView.drawable as BitmapDrawable).bitmap.height
            Log.d("测试日志", "PlotActivity loadCloseUpImageAndAnimate: $width  $height")
            val screenWidth = DensityUtil.getScreenWidth(this)
            val valueAnimator =
                ValueAnimator.ofInt(screenWidth, (screenWidth - width))
            valueAnimator.addUpdateListener { valueAnimator ->
                val viewLeft = valueAnimator.animatedValue as Int
                imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    marginStart = viewLeft
                }
            }
            valueAnimator.addListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    val valueAnimator1 =
                        ValueAnimator.ofInt((screenWidth - width), (screenWidth - width) / 2)
                    valueAnimator1.addUpdateListener { valueAnimator ->
                        val viewLeft = valueAnimator.animatedValue as Int
                        imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            marginStart = viewLeft
                        }
                    }
                    valueAnimator1.duration = 2500
                    valueAnimator1.start()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
            valueAnimator.duration = 8000
            valueAnimator.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}

