package com.lilithqa.ezprofiler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 黑黑
 * @apiNote ezProfiler参数类
 * @date 2018年4月12日 下午2:59:56<br/>
 */
@Component
public class EzProfilerProperties {

    @Value("${ezprofiler.enableBasic:true}")
    private boolean enableBasic = true;

    @Value("${ezprofiler.username:lilith_ltd}")
    private String username = "lilith_ltd";

    @Value("${ezprofiler.password:lilith_ltd}")
    private String password = "lilith_ltd";

    @Value("${ezprofiler.url:/profiler}")
    private String url = "/profiler";

    @Value("${ezprofiler.basepackage:com}")
    private String basePackage = "com";

    @Value("${ezprofiler.mongoDb.uri}")
    private String uri;

    public boolean isEnableBasic() {
        return enableBasic;
    }

    public void setEnableBasic(boolean enableBasic) {
        this.enableBasic = enableBasic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
