package com.lilithqa.ezprofiler.scanner;

import java.util.Date;

/**
 * @author 黑黑
 * @apiNote 方法请求调用统计数据类
 * @date 2021-03-04
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
     * 调用请求最小时间
     */
    private long minMills;
    /**
     * 调用请求最大时间
     */
    private long maxMills;
    /**
     * 调用请求平均时间
     */
    private long avgMills;
    /**
     * 请求最大时间日期
     */
    private Date maxInvokeAt;
    /**
     * 最近一日调用次数
     */
    private long lastDayCount;
    /**
     * 最近一天调用成功次数
     */
    private long lastDayOkCount;
    /**
     * 最近一天调用失败次数
     */
    private long lastDayErrorCount;
    /**
     * 最近一天调用成功率
     */
    private double lastDaySuccessRate;
    /**
     * 最近一天调用请求最小时间
     */
    private long lastDayMinMills;
    /**
     * 最近一天调用请求最大时间
     */
    private long lastDayMaxMills;
    /**
     * 最近一天调用请求平均时间
     */
    private long lastDayAvgMills;
    /**
     * 最近一天最长延时请求日期
     */
    private Date lastDayMaxInvokeAt;
    /**
     * 上次请求时间
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

    public long getLastDayCount() {
        return lastDayCount;
    }

    public void setLastDayCount(long lastDayCount) {
        this.lastDayCount = lastDayCount;
    }

    public long getLastDayOkCount() {
        return lastDayOkCount;
    }

    public void setLastDayOkCount(long lastDayOkCount) {
        this.lastDayOkCount = lastDayOkCount;
    }

    public long getLastDayErrorCount() {
        return lastDayErrorCount;
    }

    public void setLastDayErrorCount(long lastDayErrorCount) {
        this.lastDayErrorCount = lastDayErrorCount;
    }

    public double getLastDaySuccessRate() {
        return lastDaySuccessRate;
    }

    public void setLastDaySuccessRate(double lastDaySuccessRate) {
        this.lastDaySuccessRate = lastDaySuccessRate;
    }

    public long getLastDayMinMills() {
        return lastDayMinMills;
    }

    public void setLastDayMinMills(long lastDayMinMills) {
        this.lastDayMinMills = lastDayMinMills;
    }

    public long getLastDayMaxMills() {
        return lastDayMaxMills;
    }

    public void setLastDayMaxMills(long lastDayMaxMills) {
        this.lastDayMaxMills = lastDayMaxMills;
    }

    public long getLastDayAvgMills() {
        return lastDayAvgMills;
    }

    public void setLastDayAvgMills(long lastDayAvgMills) {
        this.lastDayAvgMills = lastDayAvgMills;
    }

    public Date getLastDayMaxInvokeAt() {
        return lastDayMaxInvokeAt;
    }

    public void setLastDayMaxInvokeAt(Date lastDayMaxInvokeAt) {
        this.lastDayMaxInvokeAt = lastDayMaxInvokeAt;
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
        this.lastDayMinMills = useTime;
        this.lastDayMaxMills = useTime;
        this.lastDayAvgMills = useTime;
        this.lastMills = useTime;
    }

    public void setNewDayData(long useTime) {
        this.lastDayCount = 0;
        this.lastDayErrorCount = 0;
        this.lastDayOkCount = 0;
        this.lastDayAvgMills = useTime;
        this.lastDayMinMills = useTime;
        this.lastDayMaxMills = useTime;
        this.lastDayMaxInvokeAt = new Date();
    }
}
