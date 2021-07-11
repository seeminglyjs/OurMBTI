package com.our.ourmbti.service.board.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.our.ourmbti.dao.board.face.BoardDao;
import com.our.ourmbti.dto.Board;
import com.our.ourmbti.dto.BoardImg;
import com.our.ourmbti.dto.Comment;
import com.our.ourmbti.dto.User;
import com.our.ourmbti.service.board.face.BoardService;
import com.our.ourmbti.util.BoardPaging;

@Service
public class BoardServiceImpl implements BoardService {


	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	//dao di
	@Autowired
	BoardDao boardDao;

	//서블릿 컨텍스트 객체
	// 의존성주입으로 바로 사용가능한 상태로 얻어온다
	@Autowired 	
	ServletContext context;

	@Override // 페이징 정보를 가져오는 메소드
	public HashMap<String, Object> getPaging(HttpServletRequest request) {
		BoardPaging paging = null;
		HashMap<String, Object> map = new HashMap<String, Object>(); // dao에 전달할 데이터를 답을 map

		//현재 페이지 1페이지로 default 설정
		int curPage = 1;

		//전체 게시글 수
		int totalCount = 0;

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

		String searchText = request.getParameter("searchText");

		// -- 사용자가 검색을 했다면
		if(searchText != null && !searchText.equals("")) {
			String searchCategory = request.getParameter("searchCategory");
			//검색 카테고리가 제목이나 내용이 아닐 경우

			if(searchCategory == null) {
				searchCategory = "title";
			}else {
				if(!searchCategory.equals("title") && !searchCategory.equals("content")) {
					searchCategory = "title";
				}
			}



			//검색 카테고리와 내용 map에 저장
			map.put("searchCategory", searchCategory);
			map.put("searchText", searchText);

			//검색 내용 기준으로 게시글 리스트를 구한다.
			totalCount = boardDao.selectBoardListCount(map);

		}else {// -- 사용자가 검색을 안했다면
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
			totalCount = boardDao.selectBoardListCount(map);
		}



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


	@Override // 게시글 조회수를 상승시키는 메소드
	public void plusHit(int bNo) {

		//게시글 조회수를 + 1 하는 메소드
		boardDao.updateHit(bNo);

	}


	@Override // 게시글 상세 정보를 가져오는 메소드
	public HashMap<String, Object> getBoardInfo(int bNo) {

		//게시판 상세 정보를 가져온다.
		HashMap<String, Object> map = boardDao.selectBoardDetail(bNo);

		return map;
	}

	@Override //게시글을 작성하는 메소드
	@Transactional //트랜잭션 체크 어노테이션
	public void writerBoard(MultipartHttpServletRequest request, User user) {

		//게시판 객체에 게시글 정보를 담는다.
		Board board = new Board();
		board.setbTitle(request.getParameter("title"));
		board.setbContent(request.getParameter("content"));
		board.setbType(request.getParameter("boardCategory"));
		board.setuNo(user.getuNo());

		//DB에 게시글 정보를 기록한다.
		boardDao.insertBoardInfo(board);

		//첨부 파일 정보 리스트로 받는다.
		List<MultipartFile> fileList = request.getFiles("uploadFiles");

		//파일이 없기 때문에 게시글 등록 완료
		if(fileList == null || fileList.isEmpty()) {
			return;
		}else {
			//첨부된 파일 숫자만큼 반복한다.
			for(int i = 0; i < fileList.size(); i++) {

				//첨부된 파일이름이 없거단 빈문자열이면 리턴
				if(fileList.get(i).getOriginalFilename() == null || fileList.get(i).getOriginalFilename().equals("")) {
					return;
				}


				//파일이 있는 경우 로직

				//파일이 저장될 경로 지정
				String storePath = context.getRealPath("upload");

				//경로에 파일 객체 생성
				File stored = new File(storePath);
				if(!stored.exists()) {
					stored.mkdir(); // 경로 미존재시 경로 생성
				}

				//원본 파일 이름
				String filename = fileList.get(i).getOriginalFilename();

				//저장파일 컨텐츠 타입 지정(이미지)
				String contentType = filename.substring(filename.lastIndexOf("."));

				//4번째 자리의 uuid 들 불러와서 저장
				String uid = UUID.randomUUID().toString().split("-")[4];

				//uuid와 컨텐츠 타입을 파일이름에 저장
				filename += uid;
				filename += contentType;

				//저장 경로와 파일이름 설정
				File dest = new File(stored, filename);

				// 파일 저장
				try {
					fileList.get(i).transferTo(dest);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				//파일 이미지 테이블 저장을 위한 현재 저장된 
				//게시판 정보를 가져오는 메소드
				int bNo = boardDao.lastBoardNo();

				//파일 이미지 정보 저장
				BoardImg boardImg = new BoardImg();

				boardImg.setbNo(bNo);
				boardImg.setBiContentType(contentType);
				boardImg.setBiOriginFilename(fileList.get(i).getOriginalFilename());
				boardImg.setBiSize(fileList.get(i).getSize());
				boardImg.setBiStoredFilename(filename);

				//파일 이미지 정보 저장
				boardDao.insertBoardImgInfo(boardImg);
			}
		}

	}

	@Override // 게시판 이미지 정보를 가져오는 메소드
	public List<BoardImg> getBoardImgInfo(int bNo) {

		List<BoardImg> list = boardDao.selectBoardImgInfo(bNo);

		return list;
	}


	@Override // 업데이트 할 게시글 정보를 가져온다.
	public Board getUpdateBoardInfo(int bNo) {

		Board board = boardDao.selectUpdateBoardDetail(bNo);

		return board;
	}

	@Override // 게시글을 업데이트 하는 메소드
	@Transactional
	public void updateBoardInfo(MultipartHttpServletRequest request) {

		String title = request.getParameter("title");
		String content = request.getParameter("content");

		String param = request.getParameter("bNo");
		int bNo = 0;	
		try {
			bNo = Integer.parseInt(param);
		} catch (Exception e) {
			logger.info("게시글 수정 게시글 번호 형변환 오류 발생");
		}

		//맵에 제목과 내용 저장
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("bNo", bNo);

		//게시글 정보 업데이트
		boardDao.updateBoardInfo(map);

		//첨부 파일 정보 리스트로 받는다.
		List<MultipartFile> fileList = request.getFiles("uploadFiles");

		//파일이 없기 때문에 게시글 등록 완료
		if(fileList == null || fileList.isEmpty() || fileList.get(0).getSize() == 0) {
			return;
		}else {

			//기존에 저장되어 있는 이미지 정보를 가져온다.
			List<BoardImg> listImg = boardDao.selectBoardImgInfo(bNo);

			//파일 경로 지정
			String path = context.getRealPath("upload");

			//db와 일치하는 파일을 서버에서 제거한다.
			for(int i = 0; i < listImg.size(); i++) {
				//현재 게시판에 존재하는 파일객체를 만듬
				File file = new File(path + "\\" + listImg.get(i).getBiStoredFilename());						
				if(file.exists()) { // 파일이 존재하면
					file.delete(); // 파일 삭제	
				}
			}

			//DB에서도 파일 정보를 지운다.
			boardDao.deleteBoardImgInfo(bNo);


			//*****새로운 파일 저장 코드

			//첨부된 파일 숫자만큼 반복한다.
			for(int i = 0; i < fileList.size(); i++) {

				//첨부된 파일이름이 없거단 빈문자열이면 리턴
				if(fileList.get(i).getOriginalFilename() == null || fileList.get(i).getOriginalFilename().equals("")) {
					return;
				}


				//파일이 있는 경우 로직

				//파일이 저장될 경로 지정
				String storePath = context.getRealPath("upload");

				//경로에 파일 객체 생성
				File stored = new File(storePath);
				if(!stored.exists()) {
					stored.mkdir(); // 경로 미존재시 경로 생성
				}

				//원본 파일 이름
				String filename = fileList.get(i).getOriginalFilename();

				//저장파일 컨텐츠 타입 지정(이미지)
				String contentType = filename.substring(filename.lastIndexOf("."));

				//4번째 자리의 uuid 들 불러와서 저장
				String uid = UUID.randomUUID().toString().split("-")[4];

				//uuid와 컨텐츠 타입을 파일이름에 저장
				filename += uid;
				filename += contentType;

				//저장 경로와 파일이름 설정
				File dest = new File(stored, filename);

				// 파일 저장
				try {
					fileList.get(i).transferTo(dest);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				//파일 이미지 정보 저장
				BoardImg boardImg = new BoardImg();

				boardImg.setbNo(bNo);
				boardImg.setBiContentType(contentType);
				boardImg.setBiOriginFilename(fileList.get(i).getOriginalFilename());
				boardImg.setBiSize(fileList.get(i).getSize());
				boardImg.setBiStoredFilename(filename);

				//파일 이미지 정보 저장
				boardDao.insertBoardImgInfo(boardImg);
			}
		}
	}

	//게시글을 삭제하는 메소드
	@Override
	@Transactional
	public void deleteBoard(HttpServletRequest request) {

		String param = request.getParameter("bNo");
		int bNo = 0;

		//전달받은 파라미터(게시글번호) 형변환 체크
		try {
			bNo = Integer.parseInt(param);
		} catch (Exception e) {
			logger.info("게시글 삭제 게시판 번호 형변환 오류 발생");
		}

		//게시글에 이미지 정보가 있는지 체크
		int count = 0;
		try {
			count= boardDao.selectBoardImgCount(bNo);
		} catch (Exception e) {
			count = 0;
		}

		//만약에 이미지 정보가 존재하면
		if(count > 0 ) {
			//기존에 저장되어 있는 이미지 정보를 가져온다.
			List<BoardImg> listImg = boardDao.selectBoardImgInfo(bNo);

			//파일 경로 지정
			String path = context.getRealPath("upload");

			//db와 일치하는 파일을 서버에서 제거한다.
			for(int i = 0; i < listImg.size(); i++) {
				//현재 게시판에 존재하는 파일객체를 만듬
				File file = new File(path + "\\" + listImg.get(i).getBiStoredFilename());						
				if(file.exists()) { // 파일이 존재하면
					file.delete(); // 파일 삭제	
				}
			}

			//DB에서도 파일 정보를 지운다.
			boardDao.deleteBoardImgInfo(bNo);
		}	

		//최종적으로 게시판 정보를 지운다.
		boardDao.deleteBoardInfo(bNo);
	}

	@Override // 게시글 좋아요 여부를 체크하는 메소드
	@Transactional
	public int boardLikeCheck(HttpServletRequest request) {

		Integer check = 0;

		String param1 = request.getParameter("uNo");
		String param2 = request.getParameter("bNo");
		String param3 = request.getParameter("unlike");

		int uNo = 0;
		int bNo = 0;

		//파라미터값 형변환 체크
		try {
			uNo = Integer.parseInt(param1);
			bNo = Integer.parseInt(param2);
		} catch (Exception e) {
			logger.info("** 게시판 좋아요 체크 중 형변환 오류 발생");
			return 0;
		}

		//게시판 좋아요 취소를 눌렀음
		if(param3 != null && param3.equals("on")) {
			//형변환 오류가 발생하지 않으면 해당 파라미터를 맵에 담아준다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("uNo", uNo);
			map.put("bNo", bNo);

			//해당 유저가 현재 게시판에 좋아요를 눌렀는지 체크
			check = boardDao.selectBoardLikesCountCheck(map);

			// 현재 로그인 유저는 해당 게시글에 좋아요를 눌렀다.
			if(check != null && check == 1) {
				//때문에 해당 유저가 좋아요 누른 정보를 제거한다.
				boardDao.deleteBoardLikeInfo(map);
			}

			//게시판 자체의 좋아요 수를 하나 줄인다.
			boardDao.updateBoardLikesMinus(map);

			//파라미터 체크를 위해 다시 확인한 후 넘긴다.
			check = boardDao.selectBoardLikesCountCheck(map);
		}else { // 게시판 좋아요를 눌렀음

			//형변환 오류가 발생하지 않으면 해당 파라미터를 맵에 담아준다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("uNo", uNo);
			map.put("bNo", bNo);

			//해당 유저가 현재 게시판에 좋아요를 눌렀는지 체크
			check = boardDao.selectBoardLikesCountCheck(map);

			// 현재 로그인 유저는 해당 게시글에 좋아요를 누르지 않음
			if(check != null && check == 0) {
				//때문에 해당 유저가 좋아요를 눌렀다고 체크함
				boardDao.insertBoardLikeInfo(map);
			}

			//게시판 자체의 좋아요 수도 늘린다.
			boardDao.updateBoardLikes(map);

			//파라미터 체크를 위해 다시 확인한 후 넘긴다.
			check = boardDao.selectBoardLikesCountCheck(map);
		}

		return check;
	}


	@Override // 게시글 상세보기 최초 접속시 해당 유저가 게시글 좋아요 눌렀는지 체크
	public Integer boardDetailLikeCheck(int bNo, HttpSession session) {

		User user = (User) session.getAttribute("user");

		int uNo = 0;

		//파라미터값 체크
		try {
			if(user == null) {
				return 0;
			}else {
				uNo = user.getuNo();
			}
		} catch (Exception e) {
			logger.info("** 게시판 상세보기 좋아요 체크 중 형변환 오류 발생");
			return 0;
		}

		//형변환 오류가 발생하지 않으면 해당 파라미터를 맵에 담아준다.
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("uNo", uNo);
		map.put("bNo", bNo);

		Integer check = 0;

		//해당 유저가 현재 게시판에 좋아요를 눌렀는지 체크
		check = boardDao.selectBoardLikesCountCheck(map);


		return check;
	}

	@Override //댓글을 작성하는 메소드
	public void writeComment(HttpServletRequest request) {

		String param1 = request.getParameter("uNo");
		String param2 = request.getParameter("bNo");
		String param3 = request.getParameter("comment");

		int uNo = 0;
		int bNo = 0;
		String comment = ""; 

		//파라미터를 전달할 변수에 담는다.
		try {
			uNo = Integer.parseInt(param1);
			bNo = Integer.parseInt(param2);
			comment = param3;
		} catch (Exception e) {
			logger.info("** 게시판 댓글 작성 중 파라미터 변환중 오류 발생");
			return;
		}

		//map에 각 변수를 담아준다.
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("uNo", uNo);
		map.put("bNo", bNo);
		map.put("comment", comment);

		//게시글에 댓글을 삽입한다.
		boardDao.insertComment(map);
	}


	@Override //댓글 리스트를 가져오는 메소드
	public List<HashMap<String,Object>> getCommentList(HttpServletRequest request) {
		int commentListSize = 10; // 최초 댓글 리스트 길이 10 까지
	
		String param1 = request.getParameter("bNo");
		String param2 = request.getParameter("delCheck"); // 댓글 삭제버튼이 눌렸는지
		String param3 = request.getParameter("moreCheck"); // 댓글 더보기버튼이 눌렸는지
		String param4 = request.getParameter("foldCheck"); // 댓글 접기버튼이 눌렸는지
			
		try {
			if(param2 != null && !param2.equals("")) {// 댓글 삭제가 눌렸다면
				//현재 리스트 길이 유지를 위해 리스트 길이 저장
				commentListSize = Integer.parseInt(request.getParameter("commentListSize"));
			}else if(param3 != null && !param3.equals("")) { // 댓글 더보기가 눌렸다면
				//현재 댓글 리스트에서 += 10을 해준다.
				commentListSize = Integer.parseInt(request.getParameter("commentListSize"));
				commentListSize += 10;
			}else if(param4 != null && !param4.equals("")) { // 접기 버튼이 눌렷다면
				commentListSize = 10; // 최초 댓글 리스트 길이 10 까지
			}
		} catch (Exception e) {
			logger.info("** 댓글 리스트 전체 길이 파라미터 문제 발생 10으로 고정");
			commentListSize = 10; // 최초 댓글 리스트 길이 10 까지
		}
		
		int bNo = 0;

		try { //파라미터 형변환 오류 체크
			bNo = Integer.parseInt(param1);
		} catch (Exception e) {
			logger.info("** 게시글 댓글리스트를 파라미터 변환중 오류 발생");
			return null;
		}

		//map에 각 변수를 담아준다.
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bNo", bNo);
		map.put("commentListSize", commentListSize);

		//댓글리스트를 가져오는 메소드
		List<HashMap<String,Object>> list = boardDao.selectAllList(map);

		return list;
	}

	@Override // 댓글의 총리스트를 가져오는 메소드
	public int getCommentCount(HttpServletRequest request) {


		String param1 = request.getParameter("bNo");

		int bNo = 0;

		try { //파라미터 형변환 오류 체크
			bNo = Integer.parseInt(param1);
		} catch (Exception e) {
			logger.info("** 게시글 댓글리스트를 파라미터 변환중 오류 발생");
			return 0;
		}
		int total = 0;

		//해당 게시글의 전체 댓글수를 가져온다.
		total = boardDao.selectCommentTotalCount(bNo);

		return total;
	}


	@Override// 게시판 상세보기시 댓글의 총리스트를 가져오는 메소드
	public List<HashMap<String, Object>> getFirstCommentList(int bNo) {
		int commentListSize = 10; // 최초 댓글 리스트 길이 10 까지
		
		//map에 각 변수를 담아준다.
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bNo", bNo);
		map.put("commentListSize", commentListSize);

		//댓글리스트를 가져오는 메소드
		List<HashMap<String,Object>> list = boardDao.selectAllList(map);

		return list;
	}

	
	@Override // 게시판 상세보기시 댓글의 총리스트 갯수를 가져오는 메소드
	public int getFirstCommentCount(int bNo) {
		int total = 0;

		//해당 게시글의 전체 댓글수를 가져온다.
		total = boardDao.selectCommentTotalCount(bNo);

		return total;
	}
	
	@Override // 게시판의 댓글을 삭제하는 메소드
	public void deleteComment(HttpServletRequest request) {
		
		int cNo = 0;
		
		String param = request.getParameter("cNo");
		
		try {
			cNo = Integer.parseInt(param);
		} catch (Exception e) {
			logger.info("** 게시글 댓글 삭제 중 파라미터 형변환 오류 발생");
			return;
		}
		
		//게시판에 선택된 댓글을 삭제한다.
		boardDao.deleteComment(cNo);
	}
}



