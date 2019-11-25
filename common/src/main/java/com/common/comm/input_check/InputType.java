package com.common.comm.input_check;

import android.text.TextUtils;

import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;

public enum InputType {
    /**
     * 帐号
     */
    account {
        @Override
        public boolean check(String text) {
            return false;
        }
    },
    phone {
        @Override
        public boolean check(String text) {
            boolean isOK = StringUtil.isMobileNum(text);
            if (!isOK) {
                ToastUtil.showShort("请输入合法手机号");
            }
            return isOK;
        }
    },
    password {
        @Override
        public boolean check(String text) {
            return false;
        }
    },
    passwordConfirm {
        @Override
        public boolean check(String text) {
            return false;
        }
    },
    /**
     * 短信
     */
    smsCode {
        @Override
        public boolean check(String text) {
            return false;
        }
    },  //
    /**
     * 邀请码
     */
    invitedCode {
        @Override
        public boolean check(String text) {
            return false;
        }
    },
    /**
     * 微信
     */
    weiChat {
        @Override
        public boolean check(String text) {
            if (TextUtils.isEmpty(text)) {
                ToastUtil.showShort("请输入微信号");
                return false;
            }
            return true;
        }
    },
    email {
        @Override
        public boolean check(String text) {
            boolean isOK = StringUtil.isEmail(text);
            if (!isOK) {
                ToastUtil.showShort("请输入合法邮箱帐号");
            }
            return isOK;
        }
    };

    public abstract boolean check(String text);


}
