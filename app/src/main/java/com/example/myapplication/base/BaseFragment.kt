package com.example.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @Description:fragment基类
 * @Author: dick
 * @CreateDate: 2022/10/13
 * @Version:
 */
abstract class BaseFragment <db :ViewDataBinding> :Fragment() {
    lateinit var binding: db
    abstract val layoutId: Int

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
        initView(view)
        initListener()
    }

    override fun onResume() {
        super.onResume()

    }
    open fun initListener() {}

    abstract fun initView(view:View)


    interface OnItemClick{
        fun click()
    }
}