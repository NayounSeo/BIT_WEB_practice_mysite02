package com.estsoft.mysite.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// @Auth Annotation 체크 (@AuthUser)
		AuthUser authUser = parameter.getParameterAnnotation( AuthUser.class);
		if( authUser == null ) {
			return false;
		}
		
		// @ParameterType 체크
		if (parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if( supportsParameter( parameter ) == false ) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// 세션에서 authUser 가져오기
		//--------------내가 쓰는 request로 바꿔주세요~
		HttpServletRequest request = webRequest.getNativeRequest( HttpServletRequest.class );
		HttpSession session = request.getSession( );
		if(session == null ) {
			return WebArgumentResolver.UNRESOLVED;
		}
		//AuthUser로 세팅을 해주게 됩니다.
		return session.getAttribute("authUser");	
	}

}
