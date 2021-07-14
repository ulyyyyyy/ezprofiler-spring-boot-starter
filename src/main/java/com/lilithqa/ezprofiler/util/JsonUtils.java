package com.lilithqa.ezprofiler.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author 黑黑
 */
public class JsonUtils {

	/**
	 * 对象转成JSON
	 * @param obj 对象
	 * @return 返回JSON
	 */
	public static String object2Json(Object obj) {
		String result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对象转成Map
	 * @param obj 对象
	 * @return Map
	 */
	public static Map object2Map(Object obj) {
		String object2Json = object2Json(obj);
		Map<?, ?> result = jsonToMap(object2Json);
		return result;
	}

	/**
	 * JSON转Map
	 * @param json json,String
	 * @return Map
	 */
	public static Map<?, ?> jsonToMap(String json) {
		return json2Object(json, Map.class);
	}

	/**
	 * JSON转Object
	 * @param json json
	 * @param cls 对象类型
	 * @param <T> 泛型
	 * @return Object
	 */
	public static <T> T json2Object(String json, Class<T> cls) {
		T result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return result;
	}


	/**
	 * 转换对象
	 * @param srcObject 源对象
	 * @param destObjectType 目标对象
	 * @param <T> 类型
	 * @return 目标对象
	 */
	public static <T> T conveterObject(Object srcObject, Class<T> destObjectType) {
		String jsonContent = object2Json(srcObject);
		return json2Object(jsonContent, destObjectType);
	}
 
}