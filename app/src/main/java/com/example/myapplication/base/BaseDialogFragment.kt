package com.example.myapplication.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.lang.reflect.Field

/**
 * @Description:dialogFragment基类
 * @Author: dick
 * @CreateDate: 2022/10/13
 * @Version:
 */
abstract class BaseDialogFragment<db : ViewDataBinding> : DialogFragment() {
    lateinit var binding: db
    abstract val layoutId: Int
    var canClick = false
    var backgroundColor = ColorDrawable(Color.TRANSPARENT)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initListener()
    }

    abstract fun initView(savedInstanceState: Bundle?)
    override fun onStart() {
        super.onStart()
        try {
            val window = dialog!!.window
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setBackgroundDrawable(backgroundColor)
            dialog!!.setCancelable(canClick)
            dialog!!.setCanceledOnTouchOutside(canClick)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun show(manager: FragmentManager, tag: String?) {
        if (null != manager && !manager.isDestroyed && !isAdded && !isDialogFragmentShowing(this)) {
            showAllowingStateLoss(manager, tag)
        }
    }

    open fun isDialogFragmentShowing(dialogFragment: DialogFragment?): Boolean {
        return dialogFragment != null && dialogFragment.dialog != null && dialogFragment.dialog?.isShowing == true
    }

    open fun isFragmentShowing(dialogFragment: DialogFragment?): Boolean {
        return dialogFragment?.fragmentManager != null
    }

    override fun dismissAllowingStateLoss() {
        if (isFragmentShowing(this)) {
            super.dismissAllowingStateLoss()
        }
    }

    open fun initListener() {}

    /**
     * 通过反射处理，防止异常情况
     * @param manager
     * @param tag
     */
    open fun showAllowingStateLoss(manager: FragmentManager, tag: String?) {
        try {
            val dismissed: Field = DialogFragment::class.java.getDeclaredField("mDismissed")
            dismissed.isAccessible = true
            dismissed.set(this, false)
            val shown: Field = DialogFragment::class.java.getDeclaredField("mShownByMe")
            shown.isAccessible = true
            shown.set(this, true)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }
}