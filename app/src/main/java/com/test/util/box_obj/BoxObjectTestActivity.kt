package com.test.util.box_obj

import android.os.Bundle
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.databinding.ActivityBoxObjectTestBinding
import com.test.util.databinding.AtyAccessibilityBinding
import com.test.util.utils.AppLog
import io.objectbox.Box

class BoxObjectTestActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val userBox: Box<BoxUser> = ObjectBox.store.boxFor(BoxUser::class.java)
        super.onCreate(savedInstanceState)
        binding.btnBoxObjAdd.setOnClickListener {
            userBox.put(BoxUser(name = "tom-" + (userBox.count() + 1), age = 12, msg = "gaga"))
            AppLog.d("新增完毕")
        }
        binding.btnBoxObjDelete.setOnClickListener {
            if (userBox.count() > 0) {
                val boxUser = userBox.all[0]
                userBox.remove(boxUser.id)
                AppLog.d("删除了数据：$boxUser")
            } else {
                AppLog.d("无数据可以删除...")
            }
        }
        binding.btnBoxObjFind.setOnClickListener {
            for (item in userBox.all) {
                AppLog.d("item: $item")
            }
        }
        binding.btnBoxObjUpdate.setOnClickListener {
            val user = userBox.get(1)
            user.name = "henry"
            userBox.put(user)
            AppLog.d("更新完毕")
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_box_object_test
    }

    private val binding: ActivityBoxObjectTestBinding
        get() {
            val inflate: ActivityBoxObjectTestBinding = ActivityBoxObjectTestBinding.inflate(layoutInflater)
            return inflate
        }
}