package com.our.ourmbti.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginCheckInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(UserLoginCheckInterceptor.class);
	
	@Override // 프리 핸들러 인터셉터
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		//세션 체크를 위한 세션 객체
		HttpSession session = request.getSession();
		
		//login이 세션에 없으면 비로그인 사용자입니다.
		if(session.getAttribute("login") == null){
			session.invalidate();
	
			logger.info("비로그인 유저 접근 확인 . . . . .");
			//응답 객체 인코딩 설정
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			//스크립트로 알림창을 띄운뒤 로그인 화면으로 이동한다.
			PrintWriter out = response.getWriter();
			out.print("<script>alert('로그인이 필요한 페이지 입니다. \\n로그인 화면으로 이동합니다!'); location.href='/login/login' </script>");
			return false;
		}else {
			return true; // 로그인 가능 유저 true 리턴
		}	
	}
	
}
