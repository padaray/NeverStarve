package com.NeverStarve.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.NeverStarve.member.interceptor.MemberLoginInterceptor;
import com.NeverStarve.store.interceptor.StoreLoginInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new StoreLoginInterceptor())
				.addPathPatterns("/store/storeIndex")
				.addPathPatterns("/store/menu")
				.addPathPatterns("/store/modifyInfo")
				.excludePathPatterns("/store/login")
				.excludePathPatterns("/store/register");
		
		registry.addInterceptor(new MemberLoginInterceptor())
				.addPathPatterns("/Member/**")
				.excludePathPatterns("/Member/login")
				.excludePathPatterns("/Member/forgotPassword")
				.excludePathPatterns("/Member/register");
	}
	
//	@Override
//	public void addMemberInterceptors(InterceptorRegistry registry) {
//
//				
//		
//	}
	
	
}
