package com.test.util.accessibility

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import com.kotl.Log
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog
import kotlinx.android.synthetic.main.aty_accessibility.*

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
        btn_start.setOnClickListener {
            Log.d("=====btnStart click======")
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
        tv_server_state.text = ""
        this.bindService(Intent(this@AccessibilityActivity, MyAccessibilityService::class.java), object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                AppLog.d("====onServiceConnected=====")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                AppLog.d("====onServiceDisconnected=====")
            }
        }, BIND_AUTO_CREATE)
        updateState()
    }

    private var isLoop = true

    @SuppressLint("SetTextI18n")
    fun updateState() {
        tv_server_state.postDelayed({
            val enable = isAccessibilityEnabled(this@AccessibilityActivity)
            tv_server_state.text = "辅助服务是否开启：$enable"
            //AppLog.d(tv_server_state.text.toString())
            updateState()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoop = false
    }

    private fun isAccessibilityEnabled(context: Context): Boolean {
        val am = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.isEnabled
    }
}