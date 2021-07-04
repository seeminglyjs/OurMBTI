package com.our.ourmbti.dao.board.face;

import java.util.HashMap;
import java.util.List;

import com.our.ourmbti.dto.Board;
import com.our.ourmbti.dto.BoardImg;

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

	//작성된 게시글 번호를 가져오는 메소드
	public int lastBoardNo();

	//파일 이미지 정보 DB에 저장
	public void insertBoardImgInfo(BoardImg boardImg);

	//게시판 이미지 정보를 가져오는 메소드
	public List<BoardImg> selectBoardImgInfo(int bNo);

	//업데이트할 게시글 정보를 가져오는 메소드
	public Board selectUpdateBoardDetail(int bNo);

	//게시글 정보를 수정하는 메소드
	public void updateBoardInfo(HashMap<String, Object> map);

	//게시판 이미지 정보를 제거하는 메소드
	public void deleteBoardImgInfo(int bNo);

	//게시판 업데이트에 따른 이미지 정보 업데이트 메소드
	public void updateBoardImgInfo(BoardImg boardImg);

}
