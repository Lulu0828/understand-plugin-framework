package com.lulu.ams_pms_hook

import android.content.Context
import java.lang.reflect.Proxy

class HookHelper {

    companion object {
        fun hookActivityManager() {
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

        fun hookPackageManager(context: Context?) {
            try {
                val activityThreadClass = Class.forName("android.app.ActivityThread")
                val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
                val currentActivityThread = currentActivityThreadMethod.invoke(null);

                val sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager")
                sPackageManagerField.isAccessible = true
                val sPackageManager = sPackageManagerField.get(currentActivityThread)

                val iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager")
                val proxy = Proxy.newProxyInstance(iPackageManagerInterface.classLoader,
                    arrayOf<Class<*>>(iPackageManagerInterface), HookHandler(sPackageManager))

                sPackageManagerField.set(currentActivityThread, proxy)

                val pm = context!!.packageManager
                val mPmField = pm.javaClass.getDeclaredField("mPM")
                mPmField.isAccessible = true
                mPmField.set(pm, proxy)
            } catch (e: Exception) {
                RuntimeException("hook failed", e)
            }
        }
    }
}