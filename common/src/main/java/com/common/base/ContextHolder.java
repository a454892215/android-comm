package com.common.base;

import android.app.Application;
import android.content.Context;

public class ContextHolder {
    private static Application app;

    public static void init(Application application) {
        app = application;
    }

    public static Context getContext() {
        return app;
    }
}
