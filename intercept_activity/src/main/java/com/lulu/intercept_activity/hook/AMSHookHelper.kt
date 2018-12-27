package com.lulu.intercept_activity.hook

import android.os.Build
import android.os.Handler
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy

class AMSHookHelper {

    companion object {
        val EXTRA_TARGET_INTENT = "extra_target_intent"

        @Throws(
            ClassNotFoundException::class,
            NoSuchMethodError::class,
            InvocationTargetException::class,
            IllegalAccessException::class,
            NoSuchFieldException::class
        )
        fun hookActivityManagerNative() {
            val gDefaultField: Field
            if (Build.VERSION.SDK_INT >= 26) {
                val activityManager = Class.forName("android.app.ActivityManager")
                gDefaultField = activityManager.getDeclaredField("IActivityManagerSingleton")
            } else {
                val activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")
                gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault")
            }
            gDefaultField.isAccessible = true

            val gDefault = gDefaultField.get(null)

            val singleton = Class.forName("android.util.Singleton")
            val mInstanceField = singleton.getDeclaredField("mInstance")
            mInstanceField.isAccessible = true

            val rawIActivityManager = mInstanceField.get(gDefault)

            val iActivityManagerInterface = Class.forName("android.app.IActivityManager")
            val proxy = Proxy.newProxyInstance(
                Thread.currentThread().contextClassLoader,
                arrayOf<Class<*>>(iActivityManagerInterface), IActivityManagerHander(rawIActivityManager)
            )
            mInstanceField.set(gDefault, proxy)
        }

        @Throws(Exception::class)
        fun hookActivityThreadHandler() {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread")
            currentActivityThreadField.isAccessible = true
            val currentActivityThread = currentActivityThreadField.get(null)

            val mHField = activityThreadClass.getDeclaredField("mH")
            mHField.isAccessible = true
            val mH = mHField.get(currentActivityThread) as Handler

            val mCallbackField = Handler::class.java.getDeclaredField("mCallback")
            mCallbackField.isAccessible = true
            mCallbackField.set(mH, ActivityThreadHandlerCallback(mH))
        }
    }
}