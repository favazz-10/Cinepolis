package com.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.filter.AppFilter;

public class AppConfig implements WebMvcConfigurer {

	@Bean
	public AppFilter getFilter() {
		return new AppFilter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(getFilter()).addPathPatterns("/*");
	}
}
