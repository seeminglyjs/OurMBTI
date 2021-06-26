package com.our.ourmbti.dao.login.face;

import java.util.HashMap;

import com.our.ourmbti.dto.User;

public interface LoginDao {

	//로그인한 유저를 찾는 메소드
	public int selectLoginUser(HashMap<String, String> map);

	//세션할당을 위해 유저 정보를 가져오는 메소드
	public User selectUserInfo(HashMap<String, String> map);

	//이메일을 기준으로 관련 유저의 정보를 가져온다.
	public User selectUserByEmail(String kakaoEmail);

}
