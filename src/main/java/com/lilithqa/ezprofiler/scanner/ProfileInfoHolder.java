package com.lilithqa.ezprofiler.scanner;

import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.repository.MyMongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 黑黑
 * @apiNote Profiler数据统计类
 * @date 2017年9月21日 下午3:02:30<br/>
 */
@Component
public class ProfileInfoHolder {

    private static EzProfilerProperties properties;

    @Autowired
    public ProfileInfoHolder(EzProfilerProperties properties) {
        ProfileInfoHolder.properties = properties;
    }

    private static final AggregateInformation AGGREGATE_INFORMATION = new AggregateInformation();

    /**
     * 统计Profiler数据
     *
     * @param profileInfo ProfileInfo数据类
     */
    public static void addProfilerInfo(ProfileInfo profileInfo) {
        long startAt = profileInfo.getStart();
        long endAt = profileInfo.getEnd();
        long useTime = endAt - startAt;
        String uri = profileInfo.getUri();
        boolean occurError = profileInfo.isOccurError();
        Class<?> controllerClazz = profileInfo.getClazz();
        Method method = profileInfo.getMethod();
        ConcurrentHashMap<String, ControllerAccessInfo> map = AGGREGATE_INFORMATION.getMap();
        ControllerAccessInfo cai = map.get(controllerClazz.getSimpleName());

        if (cai == null) {
            cai = new ControllerAccessInfo();
            cai.setControllerClazz(controllerClazz);
            map.put(controllerClazz.getSimpleName(), cai);
        }
        List<MethodAccessInfo> mais = cai.getMethodInfos();
        if (mais == null) {
            mais = new ArrayList<>();
            cai.setMethodInfos(mais);
        }
        MethodAccessInfo mai = getMethodAccessInfo(mais, method);

        if (mai == null || mai.getOkCount() + mai.getErrorCount() == 0) {
            mai = new MethodAccessInfo();
            mai.setMethod(method.getName());
            mai.setUri(uri);
            mai.setInvokeCount(1);
            if (occurError) {
                mai.setErrorCount(1);
                mai.setSuccessRate(0.0);
            } else {
                mai.setOkCount(1);
                mai.setSuccessRate(1.0);
            }
            // 更新数据
            mai.setFirstData(useTime);
            mai.setMaxInvokeAt(new Date());
            mai.setLastInvokeAt(new Date());
            mais.add(mai);
        } else if (AGGREGATE_INFORMATION.checkNewDay()) {
            try {
                AGGREGATE_INFORMATION.setMap(map);

                // 添加昨日时间
                Date lastDay;
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                lastDay = calendar.getTime();
                SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
                // 把时间经过处理得到期望格式的时间
                String time = standardFormat.format(lastDay.getTime());
                AGGREGATE_INFORMATION.setDate(time);

                // 插入数据
                MyMongoTemplate myMongoTemplate = new MyMongoTemplate(properties);
                myMongoTemplate.setMethodAccessInfo(AGGREGATE_INFORMATION);
                myMongoTemplate.getMongoClient().close();
            } catch (Exception e ) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
            mais.removeIf( methodAccessInfo -> method.getName().equals(methodAccessInfo.getMethod()));
            mai = new MethodAccessInfo();
            mai.setMethod(method.getName());
            mai.setUri(uri);
            mai.setInvokeCount(1);
            if (occurError) {
                mai.setErrorCount(1);
                mai.setSuccessRate(0.0);
            } else {
                mai.setOkCount(1);
                mai.setSuccessRate(1.0);
            }
            // 更新数据
            mai.setFirstData(useTime);
            mai.setMaxInvokeAt(new Date());
            mai.setLastInvokeAt(new Date());
            mais.add(mai);
        } else {
            if (useTime < mai.getMinMills()) {
                mai.setMinMills(useTime);
            }
            if (useTime > mai.getMaxMills()) {
                mai.setMaxMills(useTime);
                mai.setMaxInvokeAt(new Date());
            }
            mai.setInvokeCount(mai.getInvokeCount() + 1);
            if (occurError) {
                mai.setErrorCount(mai.getErrorCount() + 1);
            } else {
                mai.setOkCount(mai.getOkCount() + 1);
            }
            mai.setSuccessRate((double) mai.getOkCount() / (mai.getOkCount() + mai.getErrorCount()));
            mai.setAvgMills((mai.getAvgMills() * mai.getOkCount() + useTime) / mai.getOkCount() + 1);
            mai.setLastInvokeAt(new Date());
            mai.setLastMills(useTime);
        }
    }

    /**
     * 获取单个方法的访问数据信息
     *
     * @param mais   保存的所有方法访问数据信息
     * @param method 需要调用的方法
     * @return 单个方法的数据
     */
    private static MethodAccessInfo getMethodAccessInfo(List<MethodAccessInfo> mais, Method method) {
        for (MethodAccessInfo mai : mais) {
            if (method.getName().equals(mai.getMethod())) {
                return mai;
            }
        }
        return null;
    }

    /**
     * 获取所有类的访问数据信息
     *
     * @return Map
     */
    public static Map<String, Object> getAllAccessInfo() {
        final ConcurrentHashMap<String, ControllerAccessInfo> map = AGGREGATE_INFORMATION.getMap();
        Map<String, Object> result = new HashMap<>(map.keySet().size());
        for (Map.Entry<String, ControllerAccessInfo> entry : map.entrySet()) {
            ControllerAccessInfo cai = entry.getValue();
            result.put(cai.getControllerClazz().getSimpleName(), cai.getMethodInfos());
        }
        return result;
    }
}
