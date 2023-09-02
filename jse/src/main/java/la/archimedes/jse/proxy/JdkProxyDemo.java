package la.archimedes.jse.proxy;

import lombok.extern.slf4j.Slf4j;
import la.archimedes.common.service.IDemoDaoTarget;
import la.archimedes.common.service.impl.DemoDaoTarget;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理demo
 * 1、目标类必须存在接口
 */
@Slf4j
public class JdkProxyDemo {

    public static void main(String[] args) {
        //参数在  ProxyGenerator.saveGeneratedFiles 属性中,生成的字节码文件在classpath内，可能需要finder打开才能看到或者看commit新增的文件
        // System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true"); 无效，因为必须在ProxyGenerator.generateProxyClass静态方法执行前设置，所以需要再jvm参数设置
        //jvm参数：-Dsun.misc.ProxyGenerator.saveGeneratedFile=true
        DemoDaoTarget target = new DemoDaoTarget();
        IDemoDaoTarget proxy = (IDemoDaoTarget) Proxy.newProxyInstance(JdkProxyDemo.class.getClassLoader(), new Class[]{IDemoDaoTarget.class}, new DemoInvocationHandler(target));
        proxy.save("hello world!");

    }


    static class DemoInvocationHandler implements InvocationHandler {
        private Object target;

        DemoInvocationHandler(Object target) {
            this.target = target;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("intercept before method:{}", method.toString());
            Object result = method.invoke(this.target, args);
            log.info("exit");
            return result;
        }
    }


}
