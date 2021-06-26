package com.our.ourmbti.controller.join;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.our.ourmbti.service.join.face.JoinService;


@Controller
public class JoinController {

	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

	//서비스 객체 di 주입
	@Autowired
	JoinService joinService;
	
	//회원가입 Get 컨트롤러
	@GetMapping(value= "/join/join")
	public void join(String kakaoEmail, HttpServletRequest request) {
		request.setAttribute("kakaoEmail", kakaoEmail); // 카카오 이메일 체크
	}
	
	//회원가입 Post 컨트롤러
	@PostMapping(value= "/join/join")
	public String joinRes(HttpServletRequest request, Model model) {
		Integer check = joinService.joinUser(request);
		
		if(check == 1) {
			return "/main/main";
		}else {
			logger.info("회원가입 실패 로직 확인 필요");
			model.addAttribute("joinFail", "ok");
			return "/join/join";
		}
	}
	
	//아이디 중복 체크 컨트롤러
	@PostMapping(value= "/join/overlap/id")
	public String idOverlap(String id, Model model) {
		
		//아이디 중복 체크
		int check = joinService.idOverlapCheck(id);
		
		//아이디 중복 결과를 전달한다.
		model.addAttribute("idCheck", check);
		
		return "/join/overlap/idOverlap";
	}
	
	
	//이메일 중복 체크 컨트롤러
	@PostMapping(value= "/join/overlap/email")
	public String emailOverlap(String email, Model model) {
		
		//이메일 중복 체크
		int check = joinService.emailOverlapCheck(email);
		
		//이메일 중복 결과를 전달한다.
		model.addAttribute("emailCheck", check);
		
		return "/join/overlap/emailOverlap";
	}
	
	
	//닉네임 중복 체크 컨트롤러
	@PostMapping(value= "/join/overlap/nick")
	public String nickOverlap(String nick, Model model) {
		
		//닉네임 중복 체크
		int check = joinService.nickOverlapCheck(nick);
		
		//닉네임 중복 결과를 전달한다.
		model.addAttribute("nickCheck", check);
		
		return "/join/overlap/nickOverlap";
	}
	
	//전화번호 중복 체크 컨트롤러
	@PostMapping(value= "/join/overlap/phone")
	public String phoneOverlap(String phone, Model model) {
		
		//전화번호 중복 체크
		int check = joinService.phoneOverlapCheck(phone);
		
		//전화번호 중복 결과를 전달한다.
		model.addAttribute("phoneCheck", check);
		
		return "/join/overlap/phoneOverlap";
	}
	
	
}
