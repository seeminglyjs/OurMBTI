package com.our.ourmbti.service.login.face;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.our.ourmbti.dao.login.face.LoginDao;
import com.our.ourmbti.dto.User;

@Service
public class LoginServiceImpl implements LoginService {

	
	//Dao 객체 의존성 주입
	@Autowired
	LoginDao loginDao;
	
	
	@Override // 로그인한 유저정보를 조회하는 메소드
	public int getLoginInfo(HashMap<String, String> map) {
		int check = 0;
		//로그인한 유저가 있는지 체크하는 메소드
		check = loginDao.selectLoginUser(map);	
		return check;
	}
	
	@Override // 세션 할당을 위한 유저 정보를 가져오는 메소드
	public User getUserInfo(HashMap<String, String> map) {
		
		//유저 정보를 가져오는 메소드
		User user = loginDao.selectUserInfo(map);
		
		return user;
	}
	
	@Override // 카카오톡 로그인 유저의 정보를 가져온다.
	public User selectUserByEmail(String kakaoEmail) {
		
		//유저 정보를 가져오는 메소드
		User user = loginDao.selectUserByEmail(kakaoEmail);
		
		return user;
	}
	
}
