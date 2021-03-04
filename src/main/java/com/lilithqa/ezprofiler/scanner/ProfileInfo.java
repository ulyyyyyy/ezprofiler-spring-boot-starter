package com.lilithqa.ezprofiler.scanner;

import java.lang.reflect.Method;

/**
 * @author 黑黑
 * @apiNote 单次调用统计数据类
 * @date 2018年4月12日 下午4:11:29<br/>
 */
public class ProfileInfo {
    /**
     * 调用的url
     */
    private String uri;
    /**
     * 开始时间戳
     */
    private long start;
    /**
     * 结束时间戳
     */
    private long end;
    /**
     * 请求是否发生错误
     */
    private boolean occurError;
    /**
     * 调用请求的具体类
     */
    private Class<?> clazz;
    /**
     * 调用请求的具体方法
     */
    private Method method;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isOccurError() {
        return occurError;
    }

    public void setOccurError(boolean occurError) {
        this.occurError = occurError;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}	
