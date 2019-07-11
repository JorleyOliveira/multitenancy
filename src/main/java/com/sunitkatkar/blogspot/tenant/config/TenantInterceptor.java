package com.sunitkatkar.blogspot.tenant.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunitkatkar.blogspot.util.TenantContextHolder;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

//    private static final List<String> URLS_IGNORE = new ArrayList<>();
//    static {
//        URLS_IGNORE.add("/login");
//        URLS_IGNORE.add("/register");
//        URLS_IGNORE.add("/entidades");
//    }
        
    private static final String REGEX_PATTERN_URL = "([^.]+).citsmartcloud.com";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    	Matcher m = Pattern.compile(REGEX_PATTERN_URL).matcher(request.getServerName());
		
    	if (m.find()) {
			String tenantid = m.group(1);
			TenantContextHolder.setTenantId(tenantid);
		} else {
			throw new Exception(
                    "Authentication method not supported: "
                            + request.getMethod());
		}
//        if(URLS_IGNORE.contains(request.getRequestURI())) {
//            return true;
//        }
//
//        String tenantId = request.getHeader("user-access");
//        TenantContext.setCurrentTenant(tenantId);

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
//        TenantContext.clear();
    	TenantContextHolder.clear();
    }
}