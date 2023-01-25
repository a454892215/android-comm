package com.test.util.accessibility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import com.common.utils.ToastUtil
import com.kotl.Log
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog
import kotlinx.android.synthetic.main.aty_accessibility.*
import kotlinx.android.synthetic.main.floating_view.*


class AccessibilityActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.aty_accessibility
    }

    /**
    1.4 启动服务
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
        btn_start_app.setOnClickListener {
            lunchApp(aty = activity, packageName = "com.auto.bank.app1.go")
        }
//        this.bindService(Intent(this@AccessibilityActivity, MyAccessibilityService::class.java), object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                AppLog.d("====onServiceConnected=====")
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                AppLog.d("====onServiceDisconnected=====")
//            }
//        }, BIND_AUTO_CREATE)
        btn_open_float_win.setOnClickListener {
            openFloatWin(activity)
        }
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

    private fun lunchApp(aty: Activity, packageName: String) {
        try {
            val intent = aty.packageManager.getLaunchIntentForPackage(packageName)
            intent!!.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.action = Intent.ACTION_MAIN
            aty.startActivity(intent)
        } catch (ex: Exception) {
            ToastUtil.showLong("启动apk发生异常... ")
            AppLog.e(ex)
        }
    }

    private fun isAccessibilityEnabled(context: Context): Boolean {
        val am = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.isEnabled
    }

    @SuppressLint("InflateParams")
    private fun openFloatWin(aty: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val isAccess = Settings.canDrawOverlays(aty)
            if (isAccess) {
                //   showFlowWinByOri(aty)
                EasyFloat.with(this)
                    .setLayout(R.layout.floating_view)
                    .setDragEnable(true)
                    .setShowPattern(ShowPattern.ALL_TIME)
                    .show()
                tv_start.setOnClickListener {
                    ToastUtil.showLong("tv_start")

                }
                tv_stop.setOnClickListener {
                    ToastUtil.showLong("tv_stop")
                }
            } else {
                ToastUtil.showLong("请去设置页面，同意悬浮窗权限 ")
                PermissionUtils.requestPermission(activity, object : OnPermissionResult {
                    override fun permissionResult(isOpen: Boolean) {
                        ToastUtil.showLong("isOpen:$isOpen")
                        if (isOpen) {
                            openFloatWin(aty)
                        }
                    }
                })
            }
        } else {
            ToastUtil.showLong("API 必须大于等于23")
        }
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("unused")
    private fun showFlowWinByOri(aty: Activity) {
        val layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.gravity = Gravity.CENTER or Gravity.START
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.x = 0
        layoutParams.y = 0
        val layoutInflater = LayoutInflater.from(aty)
        val floatView: View = layoutInflater.inflate(R.layout.floating_view, null)
        floatView.findViewById<TextView>(R.id.tv_start).setOnClickListener {
            ToastUtil.showLong("开始")
        }
        floatView.findViewById<TextView>(R.id.tv_stop).setOnClickListener {
            ToastUtil.showLong("停止")
        }
        aty.windowManager.addView(floatView, layoutParams)
    }
}