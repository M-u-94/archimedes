package la.deep.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.archimedes.common.service.impl.DemoDaoTarget;

import java.lang.reflect.Method;

/**
 * cglib 动态代理
 * 1、通过动态生成目标类的子类
 * 2、底层使用asm框架生成字节码
 * 3、反射实例的方式比class.newInstance效率更高
 * 参考：https://segmentfault.com/a/1190000041619989
 */
@Slf4j
public class CglibProxyDemo {

    public static void main(String[] args) {
        String curClassPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //输出cglib生成的class文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,curClassPath);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DemoDaoTarget.class);
        enhancer.setCallback(new DemoMethodInterceptor());
        DemoDaoTarget proxyObj = (DemoDaoTarget)enhancer.create();
        proxyObj.save("hello world!");
    }





    /**
     * 代理方法拦截器，也叫CallBack
     */
    static class DemoMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            log.info("intercept before method:{},proxyMethod:{}",method.toString(),methodProxy.getSignature().toString());
            //todo:methodProxy.invoke会死循环
            //执行目标对象的方法
            Object result = methodProxy.invokeSuper(o, objects);
            log.info("exit");
            return result;
        }
    }
}
