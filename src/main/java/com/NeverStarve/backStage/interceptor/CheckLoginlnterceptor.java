package com.NeverStarve.backStage.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CheckLoginlnterceptor implements WebMvcConfigurer {
	
	@Autowired
	AdminInterceptor adm;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(adm).addPathPatterns("/Backstage/**");
	}
}
