package com.our.ourmbti.dao.join.face;

import java.util.HashMap;

public interface JoinDao {

	//아이디 중복 체크 메소드
	public int selectIdOverlap(String id);

	//이메일 중복 체크 메소드
	public int selectEmailOverlap(String email);

	//닉네임 중복 체크 메소드
	public int selectNickOverlap(String nick);

	//전화번호 중복 체크 메소드
	public int selectPhoneOverlap(String phone);

	//회원가입을 진행하는 메소드
	public void insertJoinUser(HashMap<String, String> map);

	//가입된 유저가 있는지 체크하는 메소드
	public int selectJoinUserCount(String parameter);

}
