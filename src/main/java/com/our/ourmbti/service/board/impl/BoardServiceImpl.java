package com.our.ourmbti.service.board.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
		if(fileList == null || fileList.isEmpty()) {
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

}

