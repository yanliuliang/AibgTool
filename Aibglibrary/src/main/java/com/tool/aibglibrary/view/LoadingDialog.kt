package com.tool.aibglibrary.view

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.tool.aibglibrary.R
import com.tool.aibglibrary.base.BaseDialogFragment
import com.tool.aibglibrary.databinding.AigeDialogLoadingBinding


class LoadingDialog : BaseDialogFragment<AigeDialogLoadingBinding>() {
    override val layoutId: Int
        get() = R.layout.aige_dialog_loading

    companion object {
        private var dialogLoading: LoadingDialog? = null
        fun newIntent(): LoadingDialog {
            if (dialogLoading == null) {
                dialogLoading = LoadingDialog()
            }
            return dialogLoading as LoadingDialog
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d("打印yll", "initView() + LoadingDialog")
        val operatingAnim: Animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.rotate)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        binding.image.animation = operatingAnim
    }
}