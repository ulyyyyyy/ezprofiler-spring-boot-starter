package com.lilithqa.ezprofiler.scanner;

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
    private ConcurrentHashMap<String, ControllerAccessInfo> map;

    /**
     * 入库时间
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

    @Override
    public String toString() {
        return "AggregateInformation{" +
                "map=" + map +
                ", date='" + date + '\'' +
                '}';
    }
}
