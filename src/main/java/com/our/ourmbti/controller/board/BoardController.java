package com.our.ourmbti.controller.board;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.our.ourmbti.dto.Board;
import com.our.ourmbti.service.board.face.BoardService;


@Controller
public class BoardController {

	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	//서비스 객체 di
	@Autowired
	BoardService boardService;
	
	//게시판 리스트 컨트롤러
	@GetMapping(value="board/list")
	public void list(HttpServletRequest request, Model model) {
		
		//게시판 리스트의 페이징 정보를 가져오는 메소드
		HashMap <String, Object> map = boardService.getPaging(request);
		
		//페이징 정보와 요청 정보를 맵에 담아 리스트를 구한다.
		List<HashMap <String, Object>> list = boardService.getList(map);
		
		// 리스트와 리스트 크기를 넘긴다.	
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size() - 1);
	
		//페이징 처리를 위한 페이징 정보를 넘긴다.
		model.addAttribute("paging", map.get("paging"));
	}
	
}
