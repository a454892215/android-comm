package com.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyTestPlugin implements Plugin<Project>{

    void apply(Project project){
        System.out.println("======自定义gradle插件 方式2====MyTestPlugin==============");

    }
}
