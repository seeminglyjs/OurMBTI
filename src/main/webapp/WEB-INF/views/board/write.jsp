<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/layout/userHeader.jsp" %>


<style type="text/css">

/* 게시글 상세보기 전체 DIV 설정 */
#writeDiv{
	margin-top: 20px;
	padding: 15px;
	min-height: 800px;
	border: 2px solid #98c1d9;
	border-radius: 30px;
}

#contentDiv{
	padding: 15px;
	min-height: 350px;
}

</style>


<hr>
<div id="writeDiv">
	
	<!--게시글 상세보기 제목  -->
	<div class="text-center" style="border-bottom: 1px solid #98c1d9; margin-bottom: 15px;">
	<input type="text" id="title" name="title" placeholder="제목입력">
	<br>
	</div>

		
	<!-- 게시글 내용영역  -->
	<div style="margin-top: 50px;" id="contentDiv">
	<textarea id="content" name="content" cols="20" rows="20"></textarea>	
	</div>
	
		
	<!-- 게시글 페이지 이동 영역 -->
	<div class="text-right">
	<a href="/board/list"><button type="button" class="btn btn-sm btn-primary" onclick="history.back()">글목록</button></a>
	<button type="button" class="btn btn-sm btn-default" onclick="history.back()">뒤로</button>	
	</div>
	

</div>




<%@include file="/WEB-INF/views/layout/userFooter.jsp" %>