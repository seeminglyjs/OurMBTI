package com.our.ourmbti.controller.login;

import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.our.ourmbti.dto.User;
import com.our.ourmbti.service.login.face.LoginService;


@Controller
public class LoginController {

	
	//로거 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	//서비스 객체
	@Autowired
	LoginService loginService;
	
	
	//로그인 get 컨트롤러
	@GetMapping(value = "/login/login")
	public void login() {
	}
	
	//로그인 post 컨트롤러
	@PostMapping(value = "/login/login")
	public String loginRes(String id, String pw, String memoryId, Model model, HttpSession session){
		
		logger.info(memoryId); // 아이디기억하기 체크시 on
		
		//아이디 파라미터 체크
		if(id == null || id.equals("")) {
			return "/login/login";
		}
		//비밀번호 파라미터 체크
		if(pw == null || pw.equals("")) {
			return "/login/login";
		}

		
		//아이디랑 비밀번호를 담을 맵객체
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", pw);
		
		//서비스 객체에 정보 전달 
		Integer check = loginService.getLoginInfo(map);
		
		try {//예외 점검 및 로그인 전달 파라미터 확인
			if(check != null && check.intValue() != 0) {
				//정보가 맞기때문에 유저정보를 가져온다.
				User user = loginService.getUserInfo(map);
				//세션에 유저 정보 저장
				session.setAttribute("user", user);
				session.setAttribute("login", true);
				return "/main/main";
			}else {
				return "/login/login";
			}
		} catch (Exception e) {
			session.invalidate();
			return "/login/login";
		}	
	}
	
	//로그아웃 컨트롤러
	@GetMapping(value="/login/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
}
