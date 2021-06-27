package com.our.ourmbti.dao.board.face;

import java.util.HashMap;
import java.util.List;

public interface BoardDao {

	// 전체 게시글 수를 카운트 하는 메소드
	public int selectBoardListCount(HashMap<String, Object> map);

	// 페이징에 따른 게시글 정보를 가져오는 메소드
	public List<HashMap<String, Object>> selectBoardList(HashMap<String, Object> map);

}
