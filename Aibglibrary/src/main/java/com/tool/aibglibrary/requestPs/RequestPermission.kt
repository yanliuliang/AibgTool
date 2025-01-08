package com.tool.aibglibrary.requestPs

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.permissionx.guolindev.PermissionX


/**
 * @Description:权限申请
 * @Author: dick
 * @CreateDate: 2024/7/11
 * @Version:
 */
class RequestPermission private constructor() {


    /**
     * 获取是否申请过权限
     */
    private var sharedPreferences: SharedPreferences? = null

    private var mActivity: FragmentActivity? = null

    private var mFragment: Fragment? = null

    private var fragmentManager: FragmentManager? = null

    /**
     * 申请弹窗
     */
    private var dialog: PermissionTipAibgDialog? = null

    companion object {

        private const val REQUEST_PERMISSION = "request_permission"

        /**
         * 通过 FragmentActivity 初始化 RequestPermission
         */
        fun newIntent(activity: FragmentActivity): RequestPermission {
            val requestPermission = RequestPermission()
            requestPermission.mActivity = activity
            requestPermission.fragmentManager = activity.supportFragmentManager
            return requestPermission
        }

        /**
         * 通过 Fragment 初始化 RequestPermission
         */
        fun newIntent(fragment: Fragment): RequestPermission {
            val requestPermission = RequestPermission()
            requestPermission.mFragment = fragment
            requestPermission.fragmentManager = fragment.childFragmentManager
            return requestPermission
        }
    }


    /**
     * 申请定位权限
     */
    fun requestCameraPermission(vararg permissions: String, requestListener: RequestListener) {

        if (isRequestLocationPermission(*permissions)) {
            requestListener.allAgree()
            return
        }

        sharedPreferences = mActivity?.getSharedPreferences(REQUEST_PERMISSION, MODE_PRIVATE)

        val firstOpen = sharedPreferences?.getBoolean(REQUEST_PERMISSION, false)

        requestListener.isFirstRequest(firstOpen ?: true)

        val permissionX = if (mActivity == null) {
            PermissionX.init(mFragment)
        } else {
            PermissionX.init(mActivity)
        }

        permissionX.permissions(*permissions).request { allAgree, _, _ ->

            sharedPreferences?.edit()?.putBoolean(REQUEST_PERMISSION, true)?.apply()

            requestListener.requestResult(allAgree)
        }
    }


    /**
     * 展示申请弹窗
     */
    fun showPermissionTipDialog(titleContent: String, desc: String) {
        dialog = PermissionTipAibgDialog()

        dialog?.setTitle(titleContent)
        dialog?.setContent(desc)

        fragmentManager?.let { dialog?.show(it, "permission") }
    }

    /**
     * 关闭申请弹窗
     */
    fun hindPermissionTipDialog() {
        dialog?.dismiss()
    }

    /**
     * 是否申请过定位权限
     */
    /**
     * 是否申请过定位权限
     */
    fun isRequestLocationPermission(vararg permissions: String): Boolean {
        return mActivity?.let { activity ->
            permissions.all { permission ->
                ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        } ?: false
    }

}