package com.lulu.binder_hook

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            BinderHookHelper.hookClipboardService()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val editText = EditText(this)
        setContentView(editText)
    }
}
