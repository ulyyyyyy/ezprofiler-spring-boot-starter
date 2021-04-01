package com.lilithqa.ezprofiler.scanner;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @apiNote 总信息
 * @author 黑黑
 * @date 2021-03-09
 */
public class AggregateInformation {

    /**
     * key为各控制层类，value为各方法调用信息
     */
    private ConcurrentHashMap<String, ControllerAccessInfo> map = new ConcurrentHashMap<>();

    /**
     * 上次调用时间
     */
    private Date lastInvokeTime = new Date();

    /**
     * 入库日期
     */
    private String date;

    public ConcurrentHashMap<String, ControllerAccessInfo> getMap() {
        return map;
    }

    public void setMap(ConcurrentHashMap<String, ControllerAccessInfo> map) {
        this.map = map;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getLastInvokeTime() {
        return lastInvokeTime;
    }

    public void setLastInvokeTime(Date lastInvokeTime) {
        this.lastInvokeTime = lastInvokeTime;
    }

    public AggregateInformation() {
    }

    @Override
    public String toString() {
        return "AggregateInformation{" +
                "map=" + map +
                ", date='" + date + '\'' +
                '}';
    }

    public boolean checkNewDay() {
        SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date now = new Date();
        String nowStr = standardFormat.format(now);
        String lastInvoke = standardFormat.format(this.lastInvokeTime);

        System.out.println("上次调用日期：" + lastInvoke);
        System.out.println("当前调用日期：" + nowStr);

        return !nowStr.equals(lastInvoke);
    }
}
