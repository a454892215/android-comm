package com.test.util.main

import android.os.Bundle
import com.common.utils.LogUtil
import com.test.util.R
import com.test.util.base.MyBaseActivity

class AccessibilityActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.aty_accessibility
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d("====onCreate=====")
    }

}