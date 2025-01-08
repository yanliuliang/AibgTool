package com.example.myapplication.demo4

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.loader.content.CursorLoader
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityIntentBinding
import com.example.myapplication.databinding.LayoutEmptyBinding
import com.tbruyelle.rxpermissions3.RxPermissions

class IntentActivity : BaseActivity<ActivityIntentBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_intent

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn.setOnClickListener {
            val rxPermissions = RxPermissions(this)
            rxPermissions
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe { granted ->
                    if (granted) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_PICK
                        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                        startActivityForResult(intent, 0)
                    }
                }
        }
        binding.btnBackHome.setOnClickListener {
            backToHome()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val contactData = data?.data
            val cursorLoader = contactData?.let { CursorLoader(this, it, null, null, null, null) }
            val cursor = cursorLoader?.loadInBackground()
            cursor?.let { queryCursor(it) }
        }
    }

    /**
     * 查询联系人的信息
     */
    @SuppressLint("Range")
    private fun queryCursor(cursor: Cursor) {
        if (cursor.moveToFirst()) {
            val contactId =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val name =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            var phoneNumber = "此联系人暂未输入电话号码"
            val phone = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                null,
                null
            )
            if (phone != null && phone.moveToFirst()) {
                phoneNumber =
                    phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
            phone?.close()
            Log.d("queryCursor", "queryCursor: name:$name phoneNumber :$phoneNumber  phone:$phone")

        }
        cursor.close()
    }

    /**
     * 模拟返回桌面
     */
    private fun backToHome(){
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)

    }
    fun componentNameJump() {
        val comp = ComponentName(this, MainActivity::class.java)
        val intent = Intent()
        intent.component = comp
        startActivity(intent)
    }

}