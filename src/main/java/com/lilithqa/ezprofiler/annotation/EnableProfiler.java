package com.lilithqa.ezprofiler.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.lilithqa.ezprofiler.config.EzProfilerConfiguration;

/**
 * @author 黑黑
 * @apiNote
 * @date 2018年7月2日 上午8:10:14<br/>
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Import({EzProfilerConfiguration.class})
public @interface EnableProfiler {
}
