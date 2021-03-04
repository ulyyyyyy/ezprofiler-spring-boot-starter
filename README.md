# ezProfiler

## 统计Controller层的方法执行
插件作用:统计方法次数、时间、成功率，帮助基础服务的数据可视化
## 使用方式

1. 安装依赖
```sh
下载源码，命令行执行：maven clean install
```

2. 添加依赖
```xml
<dependency>
  <groupId>com.lilith.ezProfiler</groupId>
  <artifactId>ezprofiler-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
3. 添加配置
```java
@EnableProfiler
@Configuration
public class EzProfilerConfigure {
}
```
4.项目启动以后，访问浏览器 http://localhost:8080/profiler , 输出结果类似：
```json
{
	"DemoController": [{
		"method": "hello",//方法名
		"uri": "/hello",     //url路径
		"invokeCount": 2,  //总的调用次数
		"okCount": 2,       //总的成功的次数
		"errorCount": 0,   //总的失败的次数
        "successRate": 1, // 总的成功率
		"minMills": 0,       //最小用时
		"maxMills": 0,      //最大用时
		"avgMills": 0,       //平均用时
		"maxInvokeAt": "2018-08-09 10:28:08", //最大用时发生时间点
		"lastDayCount": 2,  //最近一天总调用次数
		"lastDayOkCount": 2,//最近一天成功次数
		"lastDayErrorCount": 0,//最近一天失败次数
        "lastSuccessRate": 1,// 最近一天成功率
		"lastDayMinMills": 0,//最近一天最小用时
		"lastDayMaxMills": 0,//最近一天最大用时
		"lastDayAvgMills": 0,//最近一天平均用时
		"lastDayMaxInvokeAt": "2018-08-09 10:28:12",//最近一天最大用时发生时间点
		"lastMills": 0,       //上次用时
		"lastInvokeAt": "2018-08-09 10:30:11"//上次调用时间点
	}]
}
```

## 其他配置

1. 默认会统计所有Controller的所有方法，可以在不想被统计的Controller类或者方法上添加@Profiler(false)注解，方法的优先级高于类的优先级
```java
@GetMapping("/world")
@Profiler(false)
public String world() {
  return "world";
}
```
2. 默认会给统计接口加上权限验证，默认的用户名：lilith_ltd，密码：lilith_ltd，可以自定义：
```html
ezprofiler.enableBasic=true
ezprofiler.username={username}
ezprofiler.password={password}
```
3. 默认的profiler的访问路径是/profiler，可以自定义:
```html
ezprofiler.url=/my/profiler
```
4.可以自定义要扫描的controller的base package，默认是com
```html
ezprofiler.basepackage=com
```
5.可以自定义mongoDb数据库 Uri，无默认Uri
```html
ezprofiler.mongoDb.uri={数据库地址}
```