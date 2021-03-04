package com.lilithqa.ezprofiler.scanner;

import java.util.List;

/**
 * @author 黑黑
 * @apiNote
 * @date 2021-03-04
 */
public class ControllerAccessInfo {
    /**
     * 调用Controller类方法
     */
    private Class<?> controllerClazz;
    /**
     * 已储存的调用方法统计情况
     */
    private List<MethodAccessInfo> methodInfos;

    public Class<?> getControllerClazz() {
        return this.controllerClazz;
    }

    public void setControllerClazz(Class<?> controllerClazz) {
        this.controllerClazz = controllerClazz;
    }

    public List<MethodAccessInfo> getMethodInfos() {
        return this.methodInfos;
    }

    public void setMethodInfos(List<MethodAccessInfo> methodInfos) {
        this.methodInfos = methodInfos;
    }
}
