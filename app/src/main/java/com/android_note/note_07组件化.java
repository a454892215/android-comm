package com.android_note;

/**
 * 组件化：
 *   1. 解决问题：项目大了后，编译速度会变慢，每次拉代码运行，都会花费很多时间，不利于各个团队间的合作开发
     2. 组件化实施流程，
        01. 目前android一般都使用gradle编译，android模块分为application和library
        02. 当模块处于组件模式时，模块是application模式可以直接安装运行，当处于集成打包的时候模块是library模式被依赖进项目中
        03. 因为组件化的开发，各个模块是隔离的，很多API的调用不能直接依赖，所以需要有一个公告模块抽取共用资源和进行接口隔离各个模块的相互调用
        04. manifest配置文件的合并，不同的模式下指定不同的manifest文件
        05.解决第三方库重复引用的冲突问题： 根据组件名排除或者根据包名排除
        06.通过路由实现各个activity页面的跳转
        07. 组件之间资源名冲突：因为拆分出了很多单独模块，在集成模式的时候可能会资源名称冲突，约定资源文件命名规则，
            gradle中resourcePrefix可以强制资源文件命名前缀，resourcePrefix只能限定xml文件


 */
public class note_07组件化 {
}
