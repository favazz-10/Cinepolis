package com.demo.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AppFilter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		int i = response.getStatus();
		if (i == 500) {
			response.sendRedirect("/loginForm");
			return false;
		}
		return true;
	}

}
