package com.tool.aibglibrary.requestPs

import androidx.fragment.app.Fragment

class UseTip {

    fun user(fragment: Fragment) {

        RequestPermission.newIntent(fragment = fragment).apply {
            requestCameraPermission("", requestListener = object : RequestListener {
                override fun requestResult(result: Boolean) {
                    hindPermissionTipDialog()
                }

                override fun isFirstRequest(first: Boolean) {
                    if (first) {
                        showPermissionTipDialog("", "")
                    }
                }

                override fun allAgree() {

                }
            })
        }


    }
}