package com.lulu.binder_hook

import android.annotation.TargetApi
import android.content.ClipData
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class BinderHookHandler(base: IBinder, stubClass: Class<*>): InvocationHandler {

    private val TAG = "BinderHookHandler"

    private var base: Any

    init {
        try {
            val asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder::class.java)
            // IClipboard.Stub.asInterface(base);
            this.base = asInterfaceMethod.invoke(null, base)
        } catch (e: Exception) {
            throw RuntimeException("hooked failed!")
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if("getPrimaryClip" == method?.name) {
            Log.d(TAG, "hook getPrimaryClip")
            return ClipData.newPlainText(null, "you are hooked")
        }

        if ("hasPrimaryClip" == method?.name) {
            return true
        }

        return method!!.invoke(base, args)
    }
}