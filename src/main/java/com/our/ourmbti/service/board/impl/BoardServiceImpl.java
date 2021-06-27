package com.our.ourmbti.service.board.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.our.ourmbti.dao.board.face.BoardDao;
import com.our.ourmbti.dto.Board;
import com.our.ourmbti.service.board.face.BoardService;
import com.our.ourmbti.util.BoardPaging;

@Service
public class BoardServiceImpl implements BoardService {


	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	//dao di
	@Autowired
	BoardDao boardDao;
	
	@Override // 페이징 정보를 가져오는 메소드
	public HashMap<String, Object> getPaging(HttpServletRequest request) {
		BoardPaging paging = null;
		HashMap<String, Object> map = new HashMap<String, Object>(); // dao에 전달할 데이터를 답을 map
		
		//현재 페이지 1페이지로 default 설정
		int curPage = 1;
		
		// 페이징 전달값이 있을때
		String param = request.getParameter("curPage");
		if(param != null && !param.equals("")) {
			try { // 형변환 예외체크
				curPage = Integer.parseInt(param);
			}catch (Exception e) {
				curPage = 1;
			}
		}
		
		//현재 페이지 저장
		map.put("curPage", curPage);
		
		
		//default 카테고리는 자유 게시판
		String category = "F";
		
		//게시판 카테고리를 받을 변수체크 로직
		String param2 = request.getParameter("category");
		if(param2 != null) {
			if(param2.equals("E")) {
				category = "E";
			}else if(param2.equals("J")) {
				category = "J";
			}else {
				category = "F";
			}
		}
		
		
		//카테고리 저장
		map.put("category", category);
		
		//전체 게시글수를 카운트 한다.
		int totalCount = boardDao.selectBoardListCount(map);
		
		//페이징을 구한다.
		paging = new BoardPaging(totalCount, curPage);
		
		//구해진 페이징 정보를 맵에 넣는다.
		map.put("paging", paging);
		
		return map;
	}
	
	
	@Override // 게시판 리스트를 가져오는 메소드
	public List<HashMap<String, Object>> getList(HashMap<String, Object> map) {
		List<HashMap<String, Object>> list = null;
		
		//페이징에 따른 게시판 리스트를 가져온다.
		list = boardDao.selectBoardList(map);
		
		
		return list;
	}
}
