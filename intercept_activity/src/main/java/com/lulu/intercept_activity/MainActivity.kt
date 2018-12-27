package com.lulu.intercept_activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lulu.intercept_activity.hook.AMSHookHelper
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = Button(this)
        button.text = "启动TargetActivity"

        button.setOnClickListener {
            startActivity(Intent(this@MainActivity, TargetActivity::class.java))
        }

        setContentView(button)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

        try {
            AMSHookHelper.hookActivityManagerNative()
            AMSHookHelper.hookActivityThreadHandler()
        } catch (throwable: Throwable) {
            throw RuntimeException("hook failed!", throwable)
        }
    }
}