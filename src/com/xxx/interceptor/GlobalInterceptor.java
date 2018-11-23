package com.xxx.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xxx.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)  {
        Object user = request.getSession().getAttribute("user");
        boolean flag = false;
        String ajaxFlag = request.getHeader("x-requested-with");
        if(StringUtil.isNotNullString(ajaxFlag)){
            if(ajaxFlag.equalsIgnoreCase("XMLHttpRequest")){
                flag = true;
            }
        }
        try {
        	if(null == user){
        		if(flag){
        			response.getWriter().print("timeout");
        			response.getWriter().close();
        		} else {
        			response.sendRedirect(request.getContextPath() + "/login.jsp");
        			return false;
        		}
        	}
        } catch (Exception e){
        }
        return true;
    }
}
