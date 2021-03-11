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

    @Value("${ezprofiler.enableBasic:false}")
    private boolean enableBasic = false;

    @Value("${ezprofiler.username:lilith_ltd}")
    private String username = "lilith_ltd";

    @Value("${ezprofiler.password:lilith_ltd}")
    private String password = "lilith_ltd";

    @Value("${ezprofiler.url:/profiler}")
    private String url = "/profiler";

    @Value("${ezprofiler.basepackage:com}")
    private String basePackage = "com";

    @Value("${ezprofiler.mongoDb.host}")
    private String host;

    @Value("${ezprofiler.mongoDb.port}")
    private int port;

    @Value("${ezprofiler.mongoDb.dataBaseName:profiler_statistics}")
    private String dataBaseName = "profiler_statistics";

    @Value("${ezprofiler.mongoDb.tableName}")
    private String tableName;

    @Value("${ezprofiler.mongoDb.username:}")
    private String dbUserName = null;

    @Value("${ezprofiler.mongoDb.password:}")
    private String dbPassword = null;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
