package com.tool.aibglibrary.requestPs

import android.os.Bundle
import com.tool.aibglibrary.R
import com.tool.aibglibrary.base.BaseDialogFragment
import com.tool.aibglibrary.databinding.DialogTheonePermissTipAibgBinding

class PermissionTipAibgDialog : BaseDialogFragment<DialogTheonePermissTipAibgBinding>() {
    override val layoutId: Int
        get() = R.layout.dialog_theone_permiss_tip_aibg

    private var title: String = ""

    private var content: String = ""

    fun setTitle(title:String) {
        this.title=title
    }

    fun setContent(content:String) {
        this.content=content
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvTitle.text = title
        binding.tvDesc.text = content
    }
}