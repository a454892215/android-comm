package com.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl2 implements Plugin<Project>{

    void apply(Project project){
        System.out.println("==========PluginImpl2==============");

    }
}
