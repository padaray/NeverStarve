package com.NeverStarve.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;


public class StoreLoginInterceptor implements HandlerInterceptor{
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler) throws Exception{
		if(request.getSession().getAttribute("storeUser") == null) {
			response.sendRedirect("/NeverStarve/Member/login");
			return false;
		}
		return true;
	}
	
	
}
