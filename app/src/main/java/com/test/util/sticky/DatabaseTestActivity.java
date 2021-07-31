package com.test.util.sticky;

import android.os.Bundle;

import com.database.LitePalTest;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;


public class DatabaseTestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_add_record).setOnClickListener(v -> {
            LitePalTest.save();
        });

        findViewById(R.id.brn_delete_last).setOnClickListener(v -> {
            LitePalTest.deleteLast();
        });

        findViewById(R.id.btn_find_last).setOnClickListener(v -> {
            LitePalTest.findLast();
        });

        findViewById(R.id.btn_find_all).setOnClickListener(v -> {
            LitePalTest.findAll();
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_database_test;
    }
}
