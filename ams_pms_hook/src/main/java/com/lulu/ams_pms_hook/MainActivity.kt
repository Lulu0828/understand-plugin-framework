package com.lulu.ams_pms_hook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            val uri = Uri.parse("http://wwww.baidu.com")
            val t = Intent(Intent.ACTION_VIEW)
            t.data = uri
            startActivity(t)
        }

        btn2.setOnClickListener {
            packageManager.getInstalledApplications(0)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        HookHelper.hookAcitivtyManager()
        super.attachBaseContext(newBase)
    }
}
