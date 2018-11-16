package com.lulu.demo

import android.app.Application
import com.lulu.demo.hook.HookHelper

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            HookHelper.attachContext()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}