package com.test.util.box_obj

import android.os.Bundle
import com.test.util.R
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog
import io.objectbox.Box
import io.objectbox.BoxStore
import kotlinx.android.synthetic.main.activity_box_object_test.*

class BoxObjectTestActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val boxStore: BoxStore = MyObjectBox.builder().androidContext(applicationContext).build()
        val boxUser: Box<BoxUser> = boxStore.boxFor(BoxUser::class.java)
        super.onCreate(savedInstanceState)
        btn_box_obj_add.setOnClickListener {
            boxUser.put(BoxUser(name = "tom-" +  (boxUser.count() + 1), age = 12, msg = "gaga"))
            AppLog.d("新增完毕")
        }
        btn_box_obj_delete.setOnClickListener {

        }
        btn_box_obj_find.setOnClickListener {
            for (item in boxUser.all) {
                AppLog.d("item: $item")
            }
        }
        btn_box_obj_update.setOnClickListener {

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_box_object_test
    }
}