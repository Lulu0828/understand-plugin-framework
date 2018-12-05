package com.lulu.ams_pms_hook

import java.lang.Exception
import java.lang.reflect.Proxy

class HookHelper {

    companion object {
        fun hookAcitivtyManager() {
            try {
                val activityManagerNative = Class.forName("android.app.ActivityManagerNative")
                val gDefaultField = activityManagerNative.getDeclaredField("gDefault")
                gDefaultField.isAccessible = true

                val gDefault = gDefaultField.get(null)

                val singleton = Class.forName("android.util.Singleton")
                val mInstanceField = singleton.getDeclaredField("mInstance")
                mInstanceField.isAccessible = true

                val rawIActivityManager = mInstanceField.get(gDefault)

                val iActivityManagerInterface = Class.forName("android.app.IActivityManager")
                val proxy = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
                    arrayOf<Class<*>>(iActivityManagerInterface), HookHandler(rawIActivityManager))
                mInstanceField.set(gDefault, proxy)
            } catch (e: Exception) {
                RuntimeException("Hook Failed", e)
            }
        }
    }
}