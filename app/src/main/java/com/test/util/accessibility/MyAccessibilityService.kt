package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.test.util.utils.AppLog

class MyAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        AppLog.d("======onServiceConnected=========")
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        AppLog.d("======onAccessibilityEvent======${event.toString()}===")
    }

    override fun onInterrupt() {
        AppLog.d("======onInterrupt=========")
    }
}