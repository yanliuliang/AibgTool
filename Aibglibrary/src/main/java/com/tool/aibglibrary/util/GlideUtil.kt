package com.tool.aibglibrary.util


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.tool.aibglibrary.view.DensityUtil


/**
 * glide加载图片
 *
 * @param path :图片地址
 * @param radius :圆角
 * @param width :图片宽度
 * @param height :图片高度
 * @param placeholder :占位图片
 * @param errorPlaceholder :加载失败图片
 * @param block :加载状态的回调
 */
fun ImageView.loadByGlide(
    path: String?,
    radius: Float = 0f,
    width: Int = 0,
    height: Int = 0,
    placeholder: Int = 0,
    errorPlaceholder: Int = 0,
    block: ((glideStatus: Boolean) -> Unit?)? = null
) {

    var glide = Glide.with(this.context).load(path)
    if (radius != 0f) {
        glide.applyRadius(this.context, radius)
    }

    if (placeholder != 0) {
        glide = glide.placeholder(placeholder)
    }

    if (errorPlaceholder != 0) {
        glide = glide.error(errorPlaceholder)
    }

    glide.setWH(width, height)

    glide.setListener(block)

    glide.into(this)
}

/**
 * 设置加载监听
 */
fun RequestBuilder<Drawable>.setListener(block: ((glideStart: Boolean) -> Unit?)?): RequestBuilder<Drawable> {
    return this.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            p0: GlideException?,
            p1: Any?,
            p2: Target<Drawable>?,
            p3: Boolean
        ): Boolean {
            block?.invoke(false)
            return false
        }

        override fun onResourceReady(
            p0: Drawable?,
            p1: Any?,
            p2: Target<Drawable>?,
            p3: DataSource?,
            p4: Boolean
        ): Boolean {
            block?.invoke(true)
            return false
        }
    })
}


/**
 * 设置图片大小
 */
fun RequestBuilder<Drawable>.setWH(width: Int, height: Int): RequestBuilder<Drawable>? {
    return if (width == 0) {
        Log.e("Glide", "setWH: 宽度未设置值,不生效")
        null
    } else if (height == 0) {
        Log.e("Glide", "setWH: 高度未设置值,不生效")
        null
    } else {
        this.override(
            width,
            height
        )
    }

}

/**
 * 加载圆角
 */
fun RequestBuilder<Drawable>.applyRadius(
    context: Context,
    radius: Float
): RequestBuilder<Drawable> {
    return this.apply(
        RequestOptions.bitmapTransform(
            RoundedCorners(
                DensityUtil.dp2px(
                    context,
                    radius
                )
            )
        )
    )
}