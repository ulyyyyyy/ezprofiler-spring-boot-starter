package com.lilithqa.ezprofiler.controller;

import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lilithqa.ezprofiler.annotation.Profiler;
import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.mapping.PropertySourcedMapping;
import com.lilithqa.ezprofiler.scanner.ProfileInfoHolder;
import com.lilithqa.ezprofiler.util.MyResponseUtil;

/**
 * @author 黑黑
 */
@RestController
@Profiler(false)
public class EzProfilerController {

    /**
     * 默认路由
     */
    public static final String DEFAULT_URL = "/profiler";

    /**
     * 配置类
     */
    @Autowired
    private final EzProfilerProperties properties;

    public EzProfilerController(EzProfilerProperties properties) {
        this.properties = properties;
    }

    @RequestMapping(DEFAULT_URL)
    @PropertySourcedMapping(propertyKey = "ezprofiler.url", value = "${ezprofiler.url}")
    public Map<String, Object> ezprofiler(HttpServletRequest request, HttpServletResponse response) {
        boolean enableBasic = properties.isEnableBasic();
        // 是否开启认证
        if (!enableBasic) {
            return ProfileInfoHolder.getAllAccessInfo();
        }
        String auth = request.getHeader("Authorization");
        int basicLength = 6;
        if ((auth != null) && (auth.length() > basicLength)) {
            auth = auth.substring(basicLength);
            auth = new String(Base64.getDecoder().decode(auth));
            String authServer = properties.getUsername() + ":" + properties.getPassword();
            if (auth.equals(authServer)) {
                // 认证成功，返回所有数据
                return ProfileInfoHolder.getAllAccessInfo();
            } else {
                MyResponseUtil.retError(request, response);
                return null;
            }
        } else {
            MyResponseUtil.retError(request, response);
            return null;
        }
    }
}
