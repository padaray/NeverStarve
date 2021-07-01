package com.NeverStarve.backStage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import com.NeverStarve.member.model.MemberBean;

@Configuration
public class AdminInterceptor implements HandlerInterceptor {
	
	@Autowired
	HttpSession session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String contextPath = request.getContextPath();
		MemberBean member =(MemberBean) session.getAttribute("member");
		if (member!=null && member.getUserType().equals("0")) {
		
			return true;
		}else {
			response.sendRedirect(contextPath+"/Member/login");
			return false;
		}
		
	}
}
