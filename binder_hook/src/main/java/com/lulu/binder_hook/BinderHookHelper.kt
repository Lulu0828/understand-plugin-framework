package com.lulu.binder_hook

import java.lang.reflect.Proxy
import android.os.IBinder



class BinderHookHelper {

    companion object {
        @Throws(Exception::class)
        fun hookClipboardService() {
            val CLIPBOARD_SERVICE = "clipboard"

            val serviceManager = Class.forName("android.os.ServiceManager")
            val getService = serviceManager.getDeclaredMethod("getService", String::class.java)

            val rawBinder = getService.invoke(null, CLIPBOARD_SERVICE) as IBinder
            val hookedBinder = Proxy.newProxyInstance(serviceManager.classLoader,
                arrayOf<Class<*>>(IBinder::class.java),
                BinderProxyHookHandler(rawBinder)) as IBinder

            val cacheField = serviceManager.getDeclaredField("sCache")
            cacheField.isAccessible = true
            val cache = cacheField.get(null) as MutableMap<String, IBinder>
            cache[CLIPBOARD_SERVICE] = hookedBinder
        }
    }
}