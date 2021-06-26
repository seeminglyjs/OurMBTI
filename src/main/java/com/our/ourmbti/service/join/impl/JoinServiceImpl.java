package com.our.ourmbti.service.join.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.our.ourmbti.dao.join.face.JoinDao;
import com.our.ourmbti.service.join.face.JoinService;

@Service
public class JoinServiceImpl implements JoinService {

	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(JoinServiceImpl.class);
	
	//JoinDao di
	@Autowired
	JoinDao joinDao;
	
	@Override // 아이디 중복 체크 메소드
	public int idOverlapCheck(String id) {
		
		int check = joinDao.selectIdOverlap(id);
		
		return check;
	}
	
	@Override// 이메일 중복 체크 메소드
	public int emailOverlapCheck(String email) {
		int check = joinDao.selectEmailOverlap(email);
		
		return check;
	}
	
	@Override// 닉네임 중복 체크 메소드
	public int nickOverlapCheck(String nick) {
		int check = joinDao.selectNickOverlap(nick);
		
		return check;
	}
	
	@Override// 전화번호 중복 체크 메소드
	public int phoneOverlapCheck(String phone) {
		int check = joinDao.selectPhoneOverlap(phone);
		
		return check;
	}
	
	@Override// 회원가입을 진행하는 메소드
	public Integer joinUser(HttpServletRequest request) {
		
		//파라미터로 전달 받은 회원가입 정보를 맵에 저장
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		map.put("pw", request.getParameter("pw"));
		map.put("name", request.getParameter("name"));
		map.put("phone", request.getParameter("phone"));
		map.put("email", request.getParameter("email"));
		map.put("nick", request.getParameter("nick"));
		map.put("postcode", request.getParameter("postcode"));
		map.put("address", request.getParameter("address"));
		map.put("detailAddress", request.getParameter("detailAddress"));
		map.put("mbti", request.getParameter("mbti"));
		
		
		//db에 저장하는 메소드
		joinDao.insertJoinUser(map);
		
		
		int check = joinDao.selectJoinUserCount(request.getParameter("id"));
		
		return check;
	}
}
