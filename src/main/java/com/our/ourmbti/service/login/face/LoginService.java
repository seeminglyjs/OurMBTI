package com.our.ourmbti.service.login.face;

import java.util.HashMap;

import com.our.ourmbti.dto.User;

public interface LoginService {

	//로그인 유저 정보 확인 메소드
	public int getLoginInfo(HashMap<String, String> map);

	//로그인 정보 체크 확인후 유저정보 세션 할당을 위한 메소드
	public User getUserInfo(HashMap<String, String> map);

	//카카오톡 로그인 유저의 정보를 가져온다.
	public User selectUserByEmail(String kakaoEmail);

}
