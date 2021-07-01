package com.our.ourmbti.controller.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.our.ourmbti.dto.BoardImg;
import com.our.ourmbti.dto.User;
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
	
	//게시판 상세보기 컨트롤러
	@GetMapping(value = "/board/detail")
	public String detail(@RequestParam(defaultValue = "") String boardNo, Model model) {
		
		int bNo = 0;
		
		try {//잘못된 파라미터 접근 시
			bNo = Integer.parseInt(boardNo);
		}catch (Exception e) {
			return "/board/list";
		}
		
		if(bNo < 1) { // 1보다 작은 게시글은 없음
			return "/board/list";
		}
		
		// 게시글 조회수를 상승시키는 메소드
		boardService.plusHit(bNo);
		
		//유저와 게시글정보의 조인 결과를 가져오는 메소드
		HashMap <String, Object> map = boardService.getBoardInfo(bNo);
		
		//게시판 이미지 정보를 가져오는 메소드
		List<BoardImg> boardImgList = boardService.getBoardImgInfo(bNo);
		
		List<String> fileNameList = new ArrayList<String>();
		
		for(int i = 0; i < boardImgList.size(); i++) {
			fileNameList.add(boardImgList.get(i).getBiStoredFilename());
		}
		
		
		//게시글 정보 전달
		model.addAttribute("boardInfo", map);
		
		//이미지가 저장되어 있는지 체크
		if(!fileNameList.isEmpty() && fileNameList.size() > 0) {
			model.addAttribute("fileNameList", fileNameList);
		}
		
		return "/board/detail";
		
	}
	
	//게시판 글쓰기 Get 컨트롤러
	@GetMapping(value="/board/write")
	public void write() {}
	
	//게시판 글쓰기 Post 컨트롤러
	@PostMapping(value="/board/write")
	public String writeRes(MultipartHttpServletRequest request, HttpSession session) {
		
		String boardCategory = request.getParameter("boardCategory");
		String title = request.getParameter("title");
		
		//현재 세션 유저정보를 가져온다.
		User user =(User) session.getAttribute("user");

	
		//비로그인 유저 글작성시
		if(user == null) {
			return "redirect:/board/list";
		}else {
			logger.info("****************************************");
			logger.info("{}",user.getuNo());
		}
		
		
		//파라미터 null 여부 체크
		if(boardCategory == null || title == null) {
			return "redirect:/board/list";
		}
		
		//파라미터 빈문자 여부 체크
		if(boardCategory.equals("") || title.equals("")) {
			return "redirect:/board/list";
		}
		
		//게시글을 작성하는 메소드
		boardService.writerBoard(request, user);
		
		
		return "redirect:/board/list";
	}
	
	
}
