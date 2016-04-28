package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = new UserVo( );
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		// Login Service 호출 ( 로그인 작업 )
		UserVo authUser = userService.login( userVo );
		if ( authUser == null ) {
			response.sendRedirect( request.getContextPath( ) + "/user/loginform");
			return false;
		}
		// Login 처리
		HttpSession session = request.getSession( true );
		session.setAttribute("authUser", authUser);
		response.sendRedirect( request.getContextPath( )+"/main" );
		
		return false;
	}

}
