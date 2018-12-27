package com.lulu.intercept_activity.hook

import android.content.Intent
import android.os.Handler
import android.os.Message

class ActivityThreadHandlerCallback(val base: Handler): Handler.Callback {
    override fun handleMessage(msg: Message?): Boolean {
        when (msg!!.what) {
            100 -> handleLaunchActivity(msg)
        }

        base.handleMessage(msg)
        return true
    }

    fun handleLaunchActivity(msg: Message?) {
        val obj = msg!!.obj

        try {
            val field = obj.javaClass.getDeclaredField("intent")
            field.isAccessible = true
            val raw = field.get(obj) as Intent

            val target = raw.getParcelableExtra<Intent>(AMSHookHelper.EXTRA_TARGET_INTENT)
            raw.component = target.component
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}