package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.kotl.Log
import com.test.util.utils.AppLog


class MyAccessibilityService : AccessibilityService() {


    override fun onCreate() {
        super.onCreate()
        Log.d("======onCreate==============")
        AccessibilityUtil.init(this)
        val myIntent = Intent(this, AccessibilityActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(myIntent)
    }

    override fun onDestroy() {
        Log.d("======onCreate==============")
        AccessibilityUtil.destroy()
        super.onDestroy()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("======onServiceConnected==============")
    }

    // activity start或者bind方式触发，或者设置页面启动辅助服务触发
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
        AppLog.d(" ====== onInterrupt ========= ")
    }

}
