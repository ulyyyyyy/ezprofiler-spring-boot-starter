package com.lilithqa.ezprofiler.annotation;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;

/**
 * @author 黑黑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Profiler {

	/**
	 * 监控开关
	 * @return 默认返回 true
	 */
	@AliasFor("enable")   
	boolean value() default true;

	/**
	 * 与enable互为显性别名
	 * @return 默认返回 true
	 */
	@AliasFor("value")    
	boolean enable() default true;
}
