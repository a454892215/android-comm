package com.test.util.box_obj

import android.os.Bundle
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_box_object_test.*

class BoxObjectTestActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val userBox: Box<BoxUser> = ObjectBox.store.boxFor(BoxUser::class.java)
        super.onCreate(savedInstanceState)
        btn_box_obj_add.setOnClickListener {
            userBox.put(BoxUser(name = "tom-" + (userBox.count() + 1), age = 12, msg = "gaga"))
            AppLog.d("新增完毕")
        }
        btn_box_obj_delete.setOnClickListener {
            if (userBox.count() > 0) {
                val boxUser = userBox.all[0]
                userBox.remove(boxUser.id)
                AppLog.d("删除了数据：$boxUser")
            } else {
                AppLog.d("无数据可以删除...")
            }
        }
        btn_box_obj_find.setOnClickListener {
            for (item in userBox.all) {
                AppLog.d("item: $item")
            }
        }
        btn_box_obj_update.setOnClickListener {
            val user = userBox.get(1)
            user.name = "henry"
            userBox.put(user)
            AppLog.d("更新完毕")
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_box_object_test
    }
}