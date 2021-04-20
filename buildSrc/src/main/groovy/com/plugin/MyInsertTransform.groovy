package com.plugin

import com.android.annotations.NonNull
import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.google.common.io.Files

class MyInsertTransform extends Transform {


    public MyInsertTransform(@NonNull String path, boolean addDependencies) {
        this.name = Files.getNameWithoutExtension(path);
        this.path = path;
        this.addDependencies = addDependencies;
    }

    //设置我们自定义的Transform对应的Task名称
    @Override
    String getName() {
        System.out.println("======自定义gradle插件========getName===");
        return "LLpp01_Transform"
    }

    //指定输入的类型，通过这里设定，可以指定我们要处理的文件类型
    //这样确保其他类型的文件不会传入
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    //指定Transfrom的作用范围
    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException,
            TransformException, InterruptedException {
        System.out.println("======自定义gradle插件=====transform======");
    }

}
