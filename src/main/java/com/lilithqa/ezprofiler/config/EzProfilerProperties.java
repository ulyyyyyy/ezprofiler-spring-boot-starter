package com.lilithqa.ezprofiler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 黑黑
 */
@Component
public class EzProfilerProperties {

    @Value("${ezprofiler.enableBasic:false}")
    private boolean enableBasic = false;

    @Value("${ezprofiler.username:lilith_ltd}")
    private String username = "test";

    @Value("${ezprofiler.password:lilith_ltd}")
    private String password = "test";

    @Value("${ezprofiler.path:/profiler}")
    private String path = "/profiler";

    @Value("${ezprofiler.basepackage:com}")
    private String basePackage = "com";

    @Value("${ezprofiler.mongoDb.uri}")
    private String uri;

    @Value("${ezprofiler.mongoDb.dataBaseName:profiler_statistics}")
    private String dataBaseName = "profiler_statistics";

    @Value("${ezprofiler.mongoDb.dbTableName}")
    private String dbTableName;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }
}
