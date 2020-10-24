package com.test.util.ori_code

import android.os.Bundle
import com.common.helper.FragmentHelper
import com.common.widget.CommonTabLayout
import com.test.util.R
import com.test.util.base.MyBaseActivity

class OriCodeActivity : MyBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view_test2
    }

    private val fragmentArr = arrayOf<Class<*>>(Fragment_01::class.java, Fragment_02::class.java)

    private val tabNames = arrayOf("Handler1", "Handler2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabLayout: CommonTabLayout = findViewById(R.id.tab_layout_1)
        tabLayout.setIndicatorViewId(R.id.flt_tab_indicator)
        tabLayout.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv)
        val fragmentHelper = FragmentHelper(fm, fragmentArr, R.id.flt_content)
        tabLayout.setOnSelectChangedListener { position: Int -> fragmentHelper.onSwitchFragment(position) }
        tabLayout.currentPosition = 0
    }
}