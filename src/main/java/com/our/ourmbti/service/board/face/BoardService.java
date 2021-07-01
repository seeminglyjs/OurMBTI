package com.our.ourmbti.service.board.face;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

	public HashMap<String, Object> getPaging(HttpServletRequest request);

	// 게시글 리스트 정보를 가져오는 메소드
	public List<HashMap<String, Object>> getList(HashMap<String, Object> map);

	//게시글 상세 정보를 가져오는 메소드
	public HashMap<String, Object> getBoardInfo(int bNo);

	//게시글 조회수를 상승시키는 메소드
	public void plusHit(int bNo);

}
