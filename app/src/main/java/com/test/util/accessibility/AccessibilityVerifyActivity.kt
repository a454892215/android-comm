package com.test.util.accessibility

import android.os.Bundle
import com.common.utils.ToastUtil


import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.databinding.AtyVerifyAccessibilityBinding
import com.test.util.utils.AppLog

class AccessibilityVerifyActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.aty_verify_accessibility
    }

    private val binding: AtyVerifyAccessibilityBinding
        get() {
            val inflate: AtyVerifyAccessibilityBinding = AtyVerifyAccessibilityBinding.inflate(layoutInflater)
            return inflate
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLog.d("====onCreate=====")
        binding.tvAppInfo.text = "?"
        binding.tv1.setOnClickListener {
            ToastUtil.showLong("tv_1")
        }

        binding.tv2.setOnClickListener {
            ToastUtil.showLong("tv_2")
        }

        binding.tv3.setOnClickListener {
            ToastUtil.showLong("tv_3")
        }
        binding.tv4.setOnClickListener {
            ToastUtil.showLong("tv_4")
        }
    }
}