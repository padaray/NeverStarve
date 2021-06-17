package com.NeverStarve.WebConfig;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.NeverStarve.store.interceptor.StoreLoginInterceptor;



public class WebConfig  implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new StoreLoginInterceptor())
				.addPathPatterns("/store/**")
				.excludePathPatterns("/store/login");
	}
	
	
}
