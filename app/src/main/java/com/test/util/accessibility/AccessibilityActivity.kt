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
import com.common.comm.L
import com.common.utils.ToastUtil
import com.kotl.Log
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import com.test.util.R
import com.test.util.accessibility.AccessibilityUtil.Companion.isAccessibilityEnabled
import com.test.util.accessibility.AccessibilityUtil.Companion.lunchApp
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
            lunchApp(aty = activity, packageName = "com.test.product_2")
        }
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





    @SuppressLint("InflateParams")
    private fun openFloatWin(aty: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val isAccess = Settings.canDrawOverlays(aty)
            val tag = "float_tag11"
            if (isAccess) {
                EasyFloat.with(this)
                    .setTag(tag)
                    .setLayout(R.layout.floating_view)
                    .setDragEnable(true)
                    .setShowPattern(ShowPattern.ALL_TIME)
                    .registerCallback {
                        createResult { _, _, view ->
                            run {
                                view?.findViewById<View>(R.id.tv_test_btn)?.setOnClickListener {
                                    AccessibilityUtil.performClickById(id = "com.test.product_2:id/tv_1")
                                }
                                view?.findViewById<View>(R.id.tv_test_edit)?.setOnClickListener {
                                    AccessibilityUtil.performSetTextToInputById(id = "com.test.product_2:id/et_1", text = "111222333")
                                }
                                view?.findViewById<View>(R.id.tv_test_btn_tag)?.setOnClickListener {
                                    AccessibilityUtil.performClickByTag(tag = "按钮3")
                                }
                                view?.findViewById<View>(R.id.tv_test_edit_tag)?.setOnClickListener {
                                    AccessibilityUtil.performSetTextToInputByTag(tag = "请输入账号", text = "111222333")
                                }
                                view?.findViewById<View>(R.id.tv_from_root)?.setOnClickListener {
                                    AccessibilityUtil.performSetTextToInputByHintText("请输入账号", "666888")
                                }
                                 view?.findViewById<View>(R.id.tv_set_text_for_focused_node)?.setOnClickListener {
                                     AccessibilityUtil.performSetTextToInputForFocusedNode("88886666")
                                }
                                 view?.findViewById<View>(R.id.tv_test_gesture)?.setOnClickListener {
                                     AccessibilityUtil.exeGesture(y = L.dp_1 * 440)
                                }
                                view?.findViewById<View>(R.id.tv_test_lunch_app)?.setOnClickListener {
                                    lunchApp(aty = activity, packageName = "com.test.product_2")
                                }

                            }
                        }
                    }
                    .show()


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
        floatView.findViewById<TextView>(R.id.tv_test_btn).setOnClickListener {
        }
        floatView.findViewById<TextView>(R.id.tv_test_edit).setOnClickListener {
        }
        aty.windowManager.addView(floatView, layoutParams)
    }
}