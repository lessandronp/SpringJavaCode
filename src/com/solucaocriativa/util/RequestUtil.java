package com.solucaocriativa.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.ELRequestMatcher;

public class RequestUtil {

    private static Log log = LogFactory.getLog(RequestUtil.class);
    private static final ELRequestMatcher REQUEST_MATCHER = 
	    new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");
    public static final String JSON_VALUE = "{\"%s\": %s}";

    public static Boolean isAjaxRequest(HttpServletRequest request) {
	return REQUEST_MATCHER.matches(request);
    }

    public static void sendJsonResponse(HttpServletResponse response,
	    String key, String message) {
	response.setContentType("application/json;charset=UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	try {
	    response.getWriter().write(String.format(JSON_VALUE, key, message));
	} catch (IOException e) {
	    log.error("error writing json to response", e);
	}
    }

}