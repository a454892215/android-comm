package com.common.widget.comm;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTextUtil {

    public static void addWordNumFilter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String editable = editText.getText().toString();
                String regEx = "[^a-zA-Z0-9]"; //对所有字母数字取反
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();//把所有匹配到的值替换掉
                if (!editable.equals(str)) {
                    editText.setText(str);
                    editText.setSelection(str.length());
                }
            }
        });
    }
}
