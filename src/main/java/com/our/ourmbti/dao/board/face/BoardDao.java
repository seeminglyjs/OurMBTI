package com.our.ourmbti.dao.board.face;

import java.util.HashMap;
import java.util.List;

import com.our.ourmbti.dto.Board;
import com.our.ourmbti.dto.BoardImg;
import com.our.ourmbti.dto.Comment;

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

	//게시판에 이미지 존재 여부 체크
	public int selectBoardImgCount(int bNo);

	//게시판을 지운다.
	public void deleteBoardInfo(int bNo);

	//게시판에 해당 유저가 좋아요를 눌렀는지 체크한다.
	public Integer selectBoardLikesCountCheck(HashMap<String, Object> map);

	//유저가 좋아요를 눌렀다고 체크한다.
	public void insertBoardLikeInfo(HashMap<String, Object> map);

	//게시판 좋아요수를 하나 더해준다.
	public void updateBoardLikes(HashMap<String, Object> map);

	//해당 게시판 좋아요 눌렀다는 유저정보를 제거한다. 
	public void deleteBoardLikeInfo(HashMap<String, Object> map);

	//게시판 좋아요수를 하나 차감해준다.
	public void updateBoardLikesMinus(HashMap<String, Object> map);

	//게시판에 댓글을 작성해주는 메소드
	public void insertComment(HashMap<String, Object> map);

	//댓글리스트를 가져오는 메소드
	public List<HashMap<String,Object>> selectAllList(HashMap<String, Object> map);

	//해당 게시글의 전체 댓글 수를 가져오는 메소드
	public int selectCommentTotalCount(int bNo);

	//댓글을 삭제하는 메소드
	public void deleteComment(int cNo);

}
