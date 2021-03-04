package com.lilithqa.ezprofiler.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 黑黑
 * @apiNote web响应类
 * @date 2018年4月12日 下午3:03:48
 */
public class MyResponseUtil {

    public static void retError(HttpServletRequest request, HttpServletResponse response) {
        String serverName = request.getServerName();
        response.setStatus(410);
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("WWW-authenticate", "Basic Realm=\"" + serverName + "\"");
    }
}
