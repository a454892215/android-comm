package com.test.util.accessibility.lib;

public enum VerifyResult {
    OK("成功", 1), // 1
    Fail("失败", 2), // 2
    Fail2("银行名字校验失败", 2), // 2
    Except("异常", 0); // 0
    final String name;
    final int code;

    VerifyResult(String name, int code) {
        this.name = name;
        this.code = code;
    }
}
