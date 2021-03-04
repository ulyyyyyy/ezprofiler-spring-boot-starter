package com.lilithqa.ezprofiler.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

/**
 * @author 黑黑
 * @apiNote
 * @date 2021-03-04
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface PropertySourcedMapping {
  String propertyKey();
  String value();
}
