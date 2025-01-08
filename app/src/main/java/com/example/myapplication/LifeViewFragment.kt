package com.example.myapplication

import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.LayoutEmptyBinding
import kotlinx.coroutines.launch

class LifeViewFragment :BaseFragment<LayoutEmptyBinding>() {
    private val TAG= "LifeViewFragment"
    override val layoutId: Int
        get() = R.layout.layout_empty

    override fun initView(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // This happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                Log.d(TAG, "initView: STARTED")
            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                // Trigger the flow and start listening for values.
                // This happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                Log.d(TAG, "initView: RESUMED")
            }
        }
    }
}