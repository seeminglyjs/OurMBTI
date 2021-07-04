package com.our.ourmbti.service.board.face;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.our.ourmbti.dto.Board;
import com.our.ourmbti.dto.BoardImg;
import com.our.ourmbti.dto.User;

public interface BoardService {

	public HashMap<String, Object> getPaging(HttpServletRequest request);

	// 게시글 리스트 정보를 가져오는 메소드
	public List<HashMap<String, Object>> getList(HashMap<String, Object> map);

	//게시글 상세 정보를 가져오는 메소드
	public HashMap<String, Object> getBoardInfo(int bNo);

	//게시글 조회수를 상승시키는 메소드
	public void plusHit(int bNo);

	//게시글을 작성하는 메소드
	public void writerBoard(MultipartHttpServletRequest request, User user);

	//게시글 이미지 정보를 가져오는메소드
	public List<BoardImg> getBoardImgInfo(int bNo);

	//업데이트 게시글 정보를 가져온다.
	public Board getUpdateBoardInfo(int bNo);

	//게시글을 업데이트 하는 메소드
	public void updateBoardInfo(MultipartHttpServletRequest request);

}
