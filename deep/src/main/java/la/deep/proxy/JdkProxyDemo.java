package la.deep.proxy;

import lombok.extern.slf4j.Slf4j;
import org.archimedes.common.service.IDemoDaoTarget;
import org.archimedes.common.service.impl.DemoDaoTarget;

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
        DemoDaoTarget target = new DemoDaoTarget();
        IDemoDaoTarget proxy = (IDemoDaoTarget)Proxy.newProxyInstance(JdkProxyDemo.class.getClassLoader(), new Class[]{IDemoDaoTarget.class}, new DemoInvocationHandler(target));
        proxy.save("hello world!");

    }





     static class DemoInvocationHandler implements InvocationHandler{
        private  Object target;

         DemoInvocationHandler(Object target) {
             this.target = target;
         }


         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
             log.info("intercept before method:{}",method.toString());
             Object result = method.invoke(this.target, args);
             log.info("exit");
             return result;
         }
     }



}
