package com.our.ourmbti.service.join.face;

import javax.servlet.http.HttpServletRequest;

public interface JoinService {

	//아이디 중복 체크 메소드
	public int idOverlapCheck(String id);

	//이메일 중복 체크 메소드
	public int emailOverlapCheck(String email);

	//닉네임 중복 체크 메소드
	public int nickOverlapCheck(String nick);

	//전화번호 중복 체크 메소드
	public int phoneOverlapCheck(String phone);

	//회원가입을 진행하는 메소드
	public Integer joinUser(HttpServletRequest request);

}
