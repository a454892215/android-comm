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
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable); //匹配所有非规则字符
                String str = m.replaceAll("").trim(); //把所有非规则字符全部替换为""
                if (!editable.equals(str)) {
                    editText.setText(str);  //设置EditText的字符
                    editText.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }
        });
    }
}
