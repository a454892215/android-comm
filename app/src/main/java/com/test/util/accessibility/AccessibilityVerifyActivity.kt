package com.test.util.accessibility

import android.os.Bundle
import com.common.utils.ToastUtil
import com.test.util.BuildConfig

import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog
import kotlinx.android.synthetic.main.aty_verify_accessibility.*

class AccessibilityVerifyActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.aty_verify_accessibility
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLog.d("====onCreate=====")
        tv_app_info.text = BuildConfig.APPLICATION_ID
        tv_1.setOnClickListener {
            ToastUtil.showLong("tv_1")
        }

        tv_2.setOnClickListener {
            ToastUtil.showLong("tv_2")
        }

        tv_3.setOnClickListener {
            ToastUtil.showLong("tv_3")
        }
        tv_4.setOnClickListener {
            ToastUtil.showLong("tv_4")
        }
    }
}