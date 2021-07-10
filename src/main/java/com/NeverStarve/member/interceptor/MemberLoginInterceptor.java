package com.NeverStarve.member.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;


public class MemberLoginInterceptor implements HandlerInterceptor{
	public boolean preHandle(HttpServletRequest request,
							HttpServletResponse response,
							Object handler) throws Exception{
		if(request.getSession().getAttribute("member") == null) {
			response.sendRedirect("/NeverStarve/Member/login");
			return false;
		}
		return true;
	}
	
	
}
