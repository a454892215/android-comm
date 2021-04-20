package com.plugin

import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.build.gradle.internal.scope.GlobalScope
import com.android.build.gradle.internal.scope.VariantScopeImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin

class MyTestPlugin implements Plugin<Object> {


    @Override
    void apply(Object target) {
        System.out.println("======自定义gradle插件 方式2====MyTestPlugin==============");
    }
}
