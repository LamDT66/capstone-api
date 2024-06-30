package com.fpt.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fpt.service.impl.TokenServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private TokenServiceImpl tokenService;
	
	@Override
	public void doFilter(
			ServletRequest servletRequest, 
			ServletResponse servletResponse, 
			FilterChain filterChain) throws IOException, ServletException {
	
		Authentication authentication = tokenService.parseTokenToUserInformation(
				(HttpServletRequest) servletRequest);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(servletRequest, servletResponse);
	}
}

