package cglib;

import com.test.java.util.LogUtil;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyIntercept implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        LogUtil.d("函数执行前");
        Object rev = methodProxy.invoke(target, args);
        LogUtil.d("函数执行后");
        return rev;
    }
}
