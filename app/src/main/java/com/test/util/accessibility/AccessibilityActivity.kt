package com.test.util.accessibility

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import com.kotl.Log
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog

class AccessibilityActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.aty_accessibility
    }

    /**
     * 1.4 启动服务
    这里我们需要在无障碍功能里面手动打开该项功能，否则无法继续进行，通过
    下面代码可以打开系统的无障碍功能列表
    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    startActivity(intent);

     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLog.d("====onCreate=====")
        val btnStart = findViewById<TextView>(R.id.btn_start)
        btnStart.setOnClickListener {
            Log.d("=====btnStart click======")
            val intent =  Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }

    }

}