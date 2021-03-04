package com.lilithqa.ezprofiler.controller;

import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lilithqa.ezprofiler.annotation.Profiler;
import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.mapping.PropertySourcedMapping;
import com.lilithqa.ezprofiler.scanner.ProfileInfoHolder;
import com.lilithqa.ezprofiler.util.MyResponseUtil;

/**
 * @author 605162215@qq.com
 *
 * @date 2018年4月12日 下午3:02:53<br/>
 */
@RestController
@Profiler(false)
public class EzProfilerController {
	
	public static final String DEFAULT_URL = "/profiler";
	
	private final EzProfilerProperties properties;
	
	public EzProfilerController(EzProfilerProperties properties) {
		this.properties = properties;
	}	
	
	@RequestMapping(DEFAULT_URL)
	@PropertySourcedMapping(propertyKey="ezprofiler.url",value="${ezprofiler.url}")
	public Map<String, Object> ezprofiler(HttpServletRequest request, HttpServletResponse response) {
		boolean enableBasic = properties.isEnableBasic();
		if(!enableBasic) {
			return ProfileInfoHolder.getAllAccessInfo();
		}
		String auth = request.getHeader("Authorization");
		if ((auth != null) && (auth.length() > 6)) {
			auth = auth.substring(6, auth.length());
			auth = new String(Base64.getDecoder().decode(auth));
			String authServer = properties.getUsername()+":"+properties.getPassword();
			if(auth.equals(authServer)) {
				return ProfileInfoHolder.getAllAccessInfo();
			}else {
				MyResponseUtil.retError(request, response);
				return null;
			}
		} else {
			MyResponseUtil.retError(request, response);
			return null;
		}
	}
}
