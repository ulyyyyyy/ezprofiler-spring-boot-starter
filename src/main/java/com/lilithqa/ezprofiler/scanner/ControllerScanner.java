package com.lilithqa.ezprofiler.scanner;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lilithqa.ezprofiler.annotation.Profiler;
import com.lilithqa.ezprofiler.config.EzProfilerProperties;

/**
 * @author 黑黑
 */
@Service
public class ControllerScanner implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    @Autowired
    private EzProfilerProperties properties;

    private final ProfilerQueue queue = new ProfilerQueue();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 拦截Controller和RestController类，生成他们的子类
     * 也就是对我们服务的 Controller 层进行拦截，就可以截取所有调用接口的请求
     */
    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        final Class<?> beanClass = bean.getClass();
        final String beanClassName = beanClass.getName();
        // 获取basePackage的地址，默认为"com"
        String basePackage = properties.getBasePackage();
        // 限定了 controller 的目录位置，当拦截到指定目录外的Controller时直接返回
        if (!beanClassName.startsWith(basePackage) || beanClassName.startsWith("org.springframework") || beanClassName.contains("EzProfilerController")) {
            return bean;
        }
        // 如果拦截类中不包括Controller注解，则直接返回
        if (!AnnotatedElementUtils.hasAnnotation(beanClass, Controller.class)) {
            return bean;
        }
        // 取当前拦截类 Profiler 注解的参数
        Profiler profiler = AnnotationUtils.findAnnotation(beanClass, Profiler.class);
        // 如果类上没有启用Profiler，直接返回
        if (profiler != null && profiler.enable()) {
            return bean;
        }

        // 确定拦截的 controller 类
        log.info("find controller:{}", beanName);
        // 调用ProxyFactory类，在拦截前后进行额外操作
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(bean);
        // MethodInterceptor 实现环绕增强，综合实现了前置、后置增强两者的功能
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            // 通过invoke方法实现真正代理过程
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Method method = invocation.getMethod();
                Profiler methodProfiler = AnnotationUtils.findAnnotation(method, Profiler.class);
                // 判断方法上是否启用Profiler
                // invoke()，如果方法是实例方法，那么正常调用它，bean为类方法，invocation.getArguments()获取当前方法参数
                // 如果该方法正常完成，则将其返回的值返回给invoke的调用者，否则返回false
                if (methodProfiler != null && methodProfiler.enable()) {
                    return method.invoke(bean, invocation.getArguments());
                }
                // 判断是否是一个requestMapping方法
                RequestMapping requestMappingAnnotation = AnnotatedElementUtils.getMergedAnnotation(method, RequestMapping.class);
                if (requestMappingAnnotation == null) {
                    return method.invoke(bean, invocation.getArguments());
                }
                //开始统计
                String uri = "";
                long startAt = 0;
                long endAt = 0;
                boolean occurError = true;
                try {
                    // requestMapping注解的 value 值，即对应的 uri
                    uri = requestMappingAnnotation.value()[0];
                    startAt = System.currentTimeMillis();
                    // 正常执行当前方法
                    Object result = method.invoke(bean, invocation.getArguments());
                    endAt = System.currentTimeMillis();
                    occurError = false;
                    return result;
                } catch (Exception e) {
                    // 如果执行方法出错，将 occurError 置1
                    endAt = System.currentTimeMillis();
                    occurError = true;
                    throw e;
                } finally {
                    // 记录数据
                    ProfileInfo info = new ProfileInfo();
                    info.setStart(startAt);
                    info.setEnd(endAt);
                    info.setUri(uri);
                    info.setClazz(beanClass);
                    info.setMethod(method);
                    info.setOccurError(occurError);
                    // 将数据保存到队列
                    queue.addProfileInfo(info);
                }
            }
    });
        return proxyFactory.getProxy();
    }

}
