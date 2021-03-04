package com.lilithqa.ezprofiler.scanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 黑黑
 * @apiNote Profiler数据统计类
 * @date 2017年9月21日 下午3:02:30<br/>
 */
public class ProfileInfoHolder {

    private static final ConcurrentHashMap<Class<?>, ControllerAccessInfo> MAP = new ConcurrentHashMap<>();

    /**
     * 统计Profiler数据
     * @param profileInfo ProfileInfo数据类
     */
    @SuppressWarnings("deprecation")
    public static void addProfilerInfo(ProfileInfo profileInfo) {
        long startAt = profileInfo.getStart();
        long endAt = profileInfo.getEnd();
        long useTime = endAt - startAt;
        String uri = profileInfo.getUri();
        boolean occurError = profileInfo.isOccurError();
        //
        Class<?> controllerClazz = profileInfo.getClazz();
        Method method = profileInfo.getMethod();
        ControllerAccessInfo cai = MAP.get(controllerClazz);
        if (cai == null) {
            cai = new ControllerAccessInfo();
            cai.setControllerClazz(controllerClazz);
            MAP.put(controllerClazz, cai);
        }
        List<MethodAccessInfo> mais = cai.getMethodInfos();
        if (mais == null) {
            mais = new ArrayList<>();
            cai.setMethodInfos(mais);
        }
        MethodAccessInfo mai = getMethodAccessInfo(mais, method);
        //第一次调用
        if (mai == null) {
            mai = new MethodAccessInfo();
            mai.setMethod(method.getName());
            mai.setUri(uri);
            mai.setInvokeCount(1);
            mai.setLastDayCount(1);
            if (occurError) {
                mai.setErrorCount(1);
                mai.setLastDayErrorCount(1);
            } else {
                mai.setOkCount(1);
                mai.setLastDayOkCount(1);
            }
            // 更新数据
            mai.setFirstData(useTime);
            mai.setMaxInvokeAt(new Date());
            mai.setLastDayMaxInvokeAt(new Date());
            mai.setLastInvokeAt(new Date());
            mais.add(mai);
        } else {
            //之前已经有调用
            Date lastInvokeTime = mai.getLastInvokeAt();
            Date now = new Date();
            //第二天重新计算
            if (lastInvokeTime.getMonth() != now.getMonth() || lastInvokeTime.getDay() != now.getDay()) {
                mai.setNewDayData(useTime);
            }
            if (useTime < mai.getMinMills()) {
                mai.setMinMills(useTime);
            }
            if (useTime < mai.getLastDayMinMills()) {
                mai.setLastDayMinMills(useTime);
            }
            if (useTime > mai.getMaxMills()) {
                mai.setMaxMills(useTime);
                mai.setMaxInvokeAt(new Date());
            }
            if (useTime > mai.getLastDayMaxMills()) {
                mai.setLastDayMaxMills(useTime);
                mai.setLastDayMaxInvokeAt(new Date());
            }
            mai.setInvokeCount(mai.getInvokeCount() + 1);
            mai.setLastDayCount(mai.getLastDayCount() + 1);
            if (occurError) {
                mai.setErrorCount(mai.getErrorCount() + 1);
                mai.setLastDayErrorCount(mai.getLastDayErrorCount() + 1);
            } else {
                mai.setOkCount(mai.getOkCount() + 1);
                mai.setLastDayOkCount(mai.getLastDayOkCount() + 1);
            }
            mai.setAvgMills((mai.getAvgMills() + useTime) / 2);
            mai.setLastDayAvgMills((mai.getLastDayAvgMills() + useTime) / 2);
            mai.setLastInvokeAt(new Date());
            mai.setLastMills(useTime);
        }
    }

    private static MethodAccessInfo getMethodAccessInfo(List<MethodAccessInfo> mais, Method method) {
        for (MethodAccessInfo mai : mais) {
            if (method.getName().equals(mai.getMethod())) {
                return mai;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static Map<String, Object> getAllAccessInfo() {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<Class<?>, ControllerAccessInfo> entry : MAP.entrySet()) {
            ControllerAccessInfo cai = entry.getValue();
            result.put(cai.getControllerClazz().getSimpleName(), cai.getMethodInfos());
        }
        return result;
    }
}
