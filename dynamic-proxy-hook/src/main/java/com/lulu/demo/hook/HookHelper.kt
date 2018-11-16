package com.lulu.demo.hook

import android.app.Instrumentation
import android.util.Log

class HookHelper {

    companion object {

        @Throws(Exception::class)
        fun attachContext() {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
            currentActivityThreadMethod.isAccessible = true
            val currentActivityThread = currentActivityThreadMethod.invoke(null)

            val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
            mInstrumentationField.isAccessible = true
            val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation

            val evilInstrumentation = EvilInstrumentation(mInstrumentation)
            mInstrumentationField.set(currentActivityThread, evilInstrumentation)

            Log.i("HookHelper", "has go in MyApplication attachContext method")
        }
    }
}