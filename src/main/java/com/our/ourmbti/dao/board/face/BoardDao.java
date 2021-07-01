package com.our.ourmbti.dao.board.face;

import java.util.HashMap;
import java.util.List;

import com.our.ourmbti.dto.Board;

public interface BoardDao {

	// 전체 게시글 수를 카운트 하는 메소드
	public int selectBoardListCount(HashMap<String, Object> map);

	// 페이징에 따른 게시글 정보를 가져오는 메소드
	public List<HashMap<String, Object>> selectBoardList(HashMap<String, Object> map);

	// 게시판 상세보기 정보를 가져오는 메소드
	public HashMap<String, Object> selectBoardDetail(int bNo);

	//게시글 조회수를 + 1시키는 메소드
	public void updateHit(int bNo);

	//게시글 글쓰기를 구현한 메소드
	public void insertBoardInfo(Board board);

}
