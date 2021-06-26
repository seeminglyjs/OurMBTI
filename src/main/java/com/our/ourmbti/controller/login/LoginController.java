package com.our.ourmbti.controller.login;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
	public String loginRes(String id, String pw, String memoryId, Model model, HttpSession session
			,@CookieValue(required = false) Cookie idCookie
			,@CookieValue(required = false) Cookie idCheck
			,HttpServletResponse response
			,HttpServletRequest request
			){

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

				if(memoryId != null) {//아이디 기억하기 체크시
					idCookie = new Cookie("idCookie", user.getuId()); // 아이디 저장
					idCookie.setMaxAge(30*24*60*60); // 30일 동안 저장
					idCheck = new Cookie("idCheck", "true"); // 아이디 체크 저장
					idCheck.setMaxAge(30*24*60*60); // 30일 동안 저장

					//쿠키 저장을 한다.
					response.addCookie(idCookie);
					response.addCookie(idCheck);
				}else {
					Cookie[] cookies = request.getCookies();
					if(cookies != null) {
						for(Cookie c : cookies) {
							c.setMaxAge(0); // 쿠키를 만료시킨다.
							response.addCookie(c); // 만료 쿠키 전달
						}
					}
				}
				return "/main/main";
			}else {
				model.addAttribute("noneUser", "ok");// 유저 정보 없음
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


	//카카오톡 GET 로그인 컨트롤러
	@GetMapping(value="/login/kakaoLogin")
	public void kakaoLogin() {}

	//카카오톡 Post 로그인 컨트롤러
	@PostMapping(value="/login/kakaoLogin")
	public String kakaoLoginRes(String kakaoEmail, Model model, HttpSession session) {	

		//로그인 정보 체크
		if(kakaoEmail == null || kakaoEmail.equals("")) {
			return "/login/login";
		}else {
			//카카오톡 로그인 유저의 정보를 가져온다.
			User user = loginService.selectUserByEmail(kakaoEmail);
			if(user == null || user.getuId() == null) { //비가입 유저
				
				model.addAttribute("kakaoEmail", kakaoEmail);// 이메일 정보 포워딩
				return "/join/join"; // 회원 가입 화면으로 보낸다.
			}else {
				//세션에 유저 정보 저장[기존 가입 이메일]
				session.setAttribute("user", user);
				session.setAttribute("login", true);
				return "/main/main";
			}
		
		}
	}

}
