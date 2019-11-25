package com.common.template;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;

import com.common.R;
import com.common.utils.ToastUtil;
import com.common.widget.InputLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class InputCheckUtil {
    private int[] inputLayoutIdArr;
    private InputType[] inputTypeArr;
    private ViewGroup parentView;

    public InputCheckUtil(int[] inputLayoutIdArr, InputType[] inputTypeArr, ViewGroup parentView) {
        this.inputLayoutIdArr = inputLayoutIdArr;
        this.inputTypeArr = inputTypeArr;
        this.parentView = parentView;
    }

    private List<String> inputTextList = new ArrayList<>();

    public boolean checkAllInput(boolean isIgnoreSmsCode) {
        String text_password = "";
        inputTextList.clear();
        for (int i = 0; i < inputLayoutIdArr.length; i++) {
            InputLayout inputLayout = parentView.findViewById(inputLayoutIdArr[i]);
            EditText et_input = inputLayout.findViewById(R.id.et_input);
            String text = et_input.getText().toString();
            inputTextList.add(text);
            InputType inputType = inputTypeArr[i];
            switch (inputType) {
                case phone:
                    if (!text.matches("^1[3-9][0-9]{9}$")) {
                        ToastUtil.showShort("请输入合法的手机号码");
                        return false;
                    }
                case password:
                    if (text.length() < 8) {
                        ToastUtil.showShort("密码长度至少8位");
                        return false;
                    }
                    text_password = text;
                case passwordConfirm:
                    if (!text_password.equals(text)) {
                        ToastUtil.showShort("两次密码输入不一致");
                        return false;
                    }
                case smsCode:
                    if (!isIgnoreSmsCode && TextUtils.isEmpty(text)) {
                        ToastUtil.showShort("请输入验证码");
                        return false;
                    }
            }
        }
        return true;
    }

    public String getInputText(int position) {
        return inputTextList.get(position);
    }

    public enum InputType {
        phone, password, passwordConfirm, smsCode
    }

}
