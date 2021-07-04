<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/layout/userHeader.jsp" %>

<style type="text/css">

/* 게시글 상세보기 전체 DIV 설정 */
#detailDiv{
	margin-top: 20px;
	padding: 15px;
	min-height: 800px;
	border: 2px solid #98c1d9;
	border-radius: 30px;
}

/* 좌측상단 카테고리 영역 */
#detailCategoryDiv{
	color: #293241;
}

#contentDiv{
	padding: 15px;
	min-height: 350px;
}

</style>


<hr>
<div id="detailDiv">
	<div id="detailCategoryDiv">
	<h5>
	<c:choose>
	<c:when test='${boardInfo.B_TYPE eq "F" }'>
	 > 자유게시판
	</c:when>

	<c:when test='${boardInfo.B_TYPE eq "E" }'>
	 > 경험게시판 
	</c:when>

	<c:otherwise>
	 > 직업게시판
	</c:otherwise>
	</c:choose>
	</h5>
	</div>
	
	<!--게시글 상세보기 제목  -->
	<div class="text-center" style="border-bottom: 1px solid #98c1d9; margin-bottom: 15px;">
	<h2><strong>${boardInfo.B_TITLE }</strong></h2>
	<br>
	
	<!-- 양끝으로 찢어지도록 설정  -->
	<div style="margin-bottom: 15px; font-size: 12px;">
	<div class="pull-left"><strong>작성자: ${boardInfo.U_NICK }</strong>&nbsp; &nbsp; &nbsp;</div> 
	
	<!-- 게시글 수정여부에 따라 나타나게 설정  -->
	<c:choose>
	<c:when test="${boardInfo.B_WRITE_DATE eq boardInfo.B_UPDATE_DATE}">
	<div class="pull-right"><strong>작성일: <fmt:formatDate value="${boardInfo.B_WRITE_DATE }" pattern="yyyy/MM/dd - hh:mm"/></strong></div>
	</c:when>
	
	<c:otherwise>
	<div class="pull-right"><strong>수정일: <fmt:formatDate value="${boardInfo.B_UPDATE_DATE }" pattern="yyyy/MM/dd - hh:mm"/></strong></div>
	</c:otherwise>
	</c:choose>
	<!--float 깨짐 방지  -->
	<div class="clearfix"></div>
	<div class="text-right"><strong>[ 조회수: ${boardInfo.B_HIT } ]</strong></div>
	</div>
	
	</div>
	
	<!-- 게시글 페이지 이동 영역 -->
	<div class="text-right">
	<a href="/board/list"><button type="button" class="btn btn-sm btn-primary" onclick="history.back()">글목록</button></a>
	<button type="button" class="btn btn-sm btn-default" onclick="history.back()">뒤로</button>	
	</div>
	
	
	<!-- 게시글 내용영역  -->
	<div style="margin-top: 50px;" id="contentDiv">
	
	<!-- 게시글에 등록된 이미지가 있으면  -->
	<c:if test="${not empty fileNameList }">
		<c:forEach items="${fileNameList}" var="img">
			<div class="imgDiv">
			<img src="/upload/${img }" alt="이미지 찾을 수 없음" style="width: 45%; height: 45%; border: 1px solid #ccc; margin-bottom: 10px"/>
			</div>
		</c:forEach>
	</c:if>
	${boardInfo.B_CONTENT }	
	</div>
	
	<!-- 게시글 수정 삭제 버튼 및 쪽지보내기 버튼  -->
	<div class="text-right" style="border-bottom: 1px solid #98c1d9; padding-bottom: 15px;">	
	<c:if test="${sessionScope.user.uNo eq boardInfo.U_NO }">
	<a href="/board/update?bNo=${boardInfo.B_NO }"><button class="btn btn-sm btn-default">게시글수정</button></a>
	<a href="/board/delete?bNo=${boardInfo.B_NO }"><button class="btn btn-sm btn-warning">게시글삭제</button></a>
	</c:if>
	<!-- 쪽지보내기 -->
	<c:if test="${sessionScope.user.uNo ne boardInfo.U_NO }">
	<a href="#"><button class="btn btn-sm btn-info"><span class="glyphicon glyphicon-envelope" aria-hidden="true">&nbsp;쪽지</span></button></a>
	</c:if>	
	</div>
	
	
	<!--댓글 쓰기 영역  -->
	<div style="margin-top: 20px;" class="text-center">
		<form>
		  <div class="form-group">
		    <label for="comment"></label>
		    <input type="email" class="form-control" id="comment" name="comment" placeholder="댓글을 달아보세요." style="width: 90%; display: inline-block;">
		  	<button class="btn btn-default btn-sm">등록</button>
		  </div>
		  
		</form>
	</div>
	
	<!--댓글 리스트 영역  -->
	<div>

	
	</div>


</div>

<%@include file="/WEB-INF/views/layout/userFooter.jsp" %>