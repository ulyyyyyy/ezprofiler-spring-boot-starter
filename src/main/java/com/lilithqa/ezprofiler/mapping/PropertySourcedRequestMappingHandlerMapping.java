package com.lilithqa.ezprofiler.mapping;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriTemplate;

/**
 * @author 黑黑
 */
public class PropertySourcedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	private final static Logger logger = LoggerFactory.getLogger(PropertySourcedRequestMappingHandlerMapping.class);
	private final Map<String, HandlerMethod> handlerMethods = new LinkedHashMap<>();
	private final Environment environment;
	private final Object handler;

	public PropertySourcedRequestMappingHandlerMapping(Environment environment, Object handler) {
		this.environment = environment;
		this.handler = handler;
	}

	/**
	 *  初始化处理方法
	 */
	@Override
	protected void initHandlerMethods() {
		setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
		Class<?> clazz = handler.getClass();
		if (isHandler(clazz)) {
			// 遍历类中所有方法
			for (Method method : clazz.getMethods()) {
				// 判断方法是否是Object类上的方法
				if(ReflectionUtils.isObjectMethod(method)) {
					continue;
				}
				PropertySourcedMapping mapper = AnnotationUtils.getAnnotation(method, PropertySourcedMapping.class);
				if (mapper != null) {
					RequestMappingInfo mapping = getMappingForMethod(method, clazz);
					HandlerMethod handlerMethod = createHandlerMethod(handler, method);
					String mappingPath = mappingPath(mapper);
					if (mappingPath != null) {
						logger.info(String.format("Mapped URL path [%s] onto method [%s]", mappingPath, handlerMethod.toString()));
						handlerMethods.put(mappingPath, handlerMethod);
					} else {
						for (String path : mapping.getPatternsCondition().getPatterns()) {
							logger.info(String.format("Mapped URL path [%s] onto method [%s]", path, handlerMethod.toString()));
							handlerMethods.put(path, handlerMethod);
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @param mapper
	 * @return
	 */
	private String mappingPath(final PropertySourcedMapping mapper) {
		// ezprofiler.url
		String propertyKey = mapper.propertyKey();
		// my.profiler
		String propertyValue = environment.getProperty(propertyKey);
		if (propertyValue != null) {
			String realKey = String.format("${%s}", propertyKey);
			return propertyValue.replace(realKey, propertyValue);
		}
		return null;
	}

	/**
	 * 判断注解类型是否存在
	 * @param beanType 方法类型
	 * @return boolean
	 */
	@Override
	protected boolean isHandler(Class<?> beanType) {
		return ((AnnotationUtils.findAnnotation(beanType, RestController.class) != null)
				|| (AnnotationUtils.findAnnotation(beanType, RequestMapping.class) != null)
				|| (AnnotationUtils.findAnnotation(beanType, Controller.class) != null));
	}

	@Override
	protected HandlerMethod lookupHandlerMethod(String urlPath, HttpServletRequest request) throws Exception {
		logger.debug("looking up handler for path: " + urlPath);
		HandlerMethod handlerMethod = handlerMethods.get(urlPath);
		if (handlerMethod != null) {
			return handlerMethod;
		}
		for (String path : handlerMethods.keySet()) {
			UriTemplate template = new UriTemplate(path);
			if (template.matches(urlPath)) {
				request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, template.match(urlPath));
				return handlerMethods.get(path);
			}
		}
		return null;
	}
}
