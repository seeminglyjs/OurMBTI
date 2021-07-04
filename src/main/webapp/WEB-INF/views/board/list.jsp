<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/layout/userHeader.jsp" %>

<script src="https://code.jquery.com/jquery-2.2.4.min.js" ></script>
<script type="text/javascript">
$(document).ready(function(){

})


	/* 카테고리 변경에 따른 페이지 이동 함수  */
	function chageSelect(){ 
		
		var selectList = document.getElementById("category")
		
		if(selectList.options[selectList.selectedIndex].value == "F"){
			location.href = "/board/list?category=F";
		}
		if(selectList.options[selectList.selectedIndex].value == "E"){
			location.href = "/board/list?category=E";
		}
		if(selectList.options[selectList.selectedIndex].value == "J"){
			location.href = "/board/list?category=J";
		}
	}

</script>





<style type="text/css">

/* 테이블 전체 div */
#listTableDiv{
	font-size: 12px;
}

</style>

<div>


<hr>


<!--검색 및 카테고리 항목 div -->
<div>

<!--카테고리 Div -->
<div class="pull-left">
	<select id = "category" onchange="chageSelect()" class="form-control">
		<option style="background-color:#e0fbfc">카테고리</option>
		<option value="F">자유게시판</option>
		<option value="E">경험게시판</option>
		<option value="J">진로게시판</option>	
	</select>
</div>
<div class="clear-fix"></div>


<!-- 검색 Form Div  -->
<div class="pull-right">
<form  class="form-inline" action="/board/list" method="get">
	<div class="form-group">
		<select id="searchCategory" name="searchCategory"class="form-control">
			<option value="title">제목</option>
			<option value="content">내용</option>	
		</select>
	</div>
    <div class="form-group">
    	<input type="text" class="form-control" id="searchText" name="searchText" placeholder="검색내용">
  		<button class="btn btn-sm btn-default">검색</button>
  	</div>
</form>
</div>
<div class="clear-fix"></div>
</div>
<br><!-- 리스트 와의 공간 부여 하기 위해서  -->
<div style="margin-top: 25px"></div>

<!--테이블 리스트 DIV  -->
<div id="listTableDiv">
<table class="table table-striped text-center">
	<tr>
		<td><strong>글번호</strong></td>
		<td><strong>제목</strong></td>
		<td><strong>작성자</strong></td>
		<td><strong>조회수</strong></td>
		<td><strong>작성일</strong></td>
	</tr>	
	<c:choose><%--게시글 존재여부 체크 --%>
	<c:when test="${empty list }">
		<tr>
			<td colspan="5"> 게시글이 존재하지 않습니다.</td>
		</tr>
	</c:when>
	
	<c:otherwise>
		<c:forEach begin="0" end="${listSize }" items="${list }" var="boardInfo">
		<tr>
			<td style="width: 10%">${boardInfo.B_NO }</td>			
			<td style="width: 50%">
			<a href="/board/detail?boardNo=${boardInfo.B_NO }">
			<c:choose>
				<c:when test='${boardInfo.B_TYPE eq "F" }'>
				[자유] 
				</c:when>

				<c:when test='${boardInfo.B_TYPE eq "E" }'>
				[경험] 
				</c:when>
			
				<c:otherwise>
				[직업]
				</c:otherwise>
			</c:choose>
			${boardInfo.B_TITLE }</a></td>		
			<td style="width: 15%">${boardInfo.U_NICK }</td>				
			<td style="width: 10%">${boardInfo.B_HIT }</td>
			<c:if test="${boardInfo.B_WRITE_DATE eq boardInfo.B_UPDATE_DATE}">
			<td style="width: 15%"><fmt:formatDate value="${boardInfo.B_WRITE_DATE }" pattern="yyyy/MM/dd"/></td>
			</c:if><%--게시글 수정 여부 체크 --%>
			<c:if test="${boardInfo.B_WRITE_DATE ne boardInfo.B_UPDATE_DATE}">
			<td style="width: 15%"><fmt:formatDate value="${boardInfo.B_UPDATE_DATE }" pattern="yyyy/MM/dd"/></td>
			</c:if>
		</tr>
		</c:forEach>
	</c:otherwise>
	
	</c:choose>
</table>
</div>

<!-- 로그인 시 글등록이 보이도록 하는 div -->
<div class="text-right">
	<c:if test="${not empty login }">
	<a href="/board/write"><button type="button" class="btn btn-sm btn-default">글작성</button></a>
	</c:if>
</div>
<hr>


<%@include file="/WEB-INF/views/util/boardPaging.jsp" %>
</div>
<%@include file="/WEB-INF/views/layout/userFooter.jsp" %>


