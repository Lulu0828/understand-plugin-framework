package com.lulu.intercept_activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class TargetActivity: Activity() {

    val TAG = "TargetActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with " + "savedInstanceState = [" + savedInstanceState + "]")

        val textView = TextView(this)
        textView.text = "TargetActivity 启动成功!!!"
        setContentView(textView)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called with " + "")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called with " + "");
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called with " + "");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called with " + "");
    }
}