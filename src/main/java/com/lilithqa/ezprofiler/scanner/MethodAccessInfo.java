package com.lilithqa.ezprofiler.scanner;

import java.util.Date;

/**
 * @author 黑黑
 */
public class MethodAccessInfo {
    /**
     * 调用方法
     */
    private String method;
    /**
     * 调用地址
     */
    private String uri;
    /**
     * 调用总次数
     */
    private long invokeCount;
    /**
     * 调用成功次数
     */
    private long okCount;
    /**
     * 调用失败次数
     */
    private long errorCount;
    /**
     * 调用成功率
     */
    private double successRate;
    /**
     * 调用请求最小耗时
     */
    private long minMills;
    /**
     * 调用请求最大耗时
     */
    private long maxMills;
    /**
     * 调用请求平均耗时
     */
    private long avgMills;
    /**
     * 请求最大耗时日期
     */
    private Date maxInvokeAt;
    /**
     * 上次请求耗时
     */
    private long lastMills;
    /**
     * 上次请求日期
     */
    private Date lastInvokeAt;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getInvokeCount() {
        return invokeCount;
    }

    public void setInvokeCount(long invokeCount) {
        this.invokeCount = invokeCount;
    }

    public long getOkCount() {
        return okCount;
    }

    public void setOkCount(long okCount) {
        this.okCount = okCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public long getMinMills() {
        return minMills;
    }

    public void setMinMills(long minMills) {
        this.minMills = minMills;
    }

    public long getMaxMills() {
        return maxMills;
    }

    public void setMaxMills(long maxMills) {
        this.maxMills = maxMills;
    }

    public long getAvgMills() {
        return avgMills;
    }

    public void setAvgMills(long avgMills) {
        this.avgMills = avgMills;
    }

    public Date getMaxInvokeAt() {
        return maxInvokeAt;
    }

    public void setMaxInvokeAt(Date maxInvokeAt) {
        this.maxInvokeAt = maxInvokeAt;
    }

    public long getLastMills() {
        return lastMills;
    }

    public void setLastMills(long lastMills) {
        this.lastMills = lastMills;
    }

    public Date getLastInvokeAt() {
        return lastInvokeAt;
    }

    public void setLastInvokeAt(Date lastInvokeAt) {
        this.lastInvokeAt = lastInvokeAt;
    }

    public void setFirstData(long useTime) {
        this.minMills = useTime;
        this.maxMills = useTime;
        this.avgMills = useTime;
        this.lastMills = useTime;
    }

    public MethodAccessInfo(String methodName, String uri, boolean occurError, long useTime) {
        this.setMethod(methodName);
        this.setUri(uri);
        this.setInvokeCount(1);
        if (occurError) {
            this.setErrorCount(1);
            this.setSuccessRate(0.0);
        } else {
            this.setOkCount(1);
            this.setSuccessRate(1.0);
        }
        // 更新数据
        this.setFirstData(useTime);
        this.setMaxInvokeAt(new Date());
        this.setLastInvokeAt(new Date());
    }
}
