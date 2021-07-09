<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/layout/userHeader.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	
	//좋아요 버튼 클릭시
	$(document).on("click", "#likeSpan", function(){
		$.ajax({
			type: "post"
			,url: "/board/boardLike"
			,data:{ uNo : $("#likeuNo").val()
				,bNo : $("#likebNo").val()}
			,dataType: "html"
			,success:function(res){
				$("#likeDiv").html(res)
			}
			,error:function(){
				console.log("게시판 좋아요 실패")
			}
		})
	})
	
	
	//안좋아요 버튼 클릭시
	$(document).on("click", "#unlikeSpan", function(){
		$.ajax({
			type: "post"
			,url: "/board/boardUnlike"
			,data:{ uNo : $("#unlikeuNo").val()
				,bNo : $("#unlikebNo").val()
				,unlike : "on"}
			,dataType: "html"
			,success:function(res){
				$("#likeDiv").html(res)
			}
			,error:function(){
				console.log("게시판 안좋아요 실패")
			}
		})
	})
	
	//댓글 달기 ajax
	$(document).on("click", "#commentBtn", function(){
		$.ajax({
			type: "post"
			,url: "/board/comment/writeComment"
			,data:{ uNo : ${sessionScope.user.uNo}
				,bNo : ${boardInfo.B_NO }
				,comment : $("#comment").val()}
			,dataType: "html"
			,success:function(res){
				$("#commentDiv").html(res)
			}
			,error:function(){
				console.log("게시판 댓글달기 실패")
			}
		})
	})

})
</script>


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


/* 게시글 좋아요 버튼  */
#likeSpan{
	cursor: pointer;
	color: #ee6c4d;
	font-size: 25px;
	line-height: 40px;
}

/* 좋아요 버튼 호버시 */
#likeSpan:hover{
	font-size: 30px;
}


/* 게시글 안좋아요 버튼  */
#unlikeSpan{
	cursor: pointer;
	color: #293241;
	font-size: 25px;
	line-height: 40px;
}

/* 안좋아요 버튼 호버시 */
#unlikeSpan:hover{
	font-size: 30px;
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
	
<!--게시글 좋아요 영역 -->
	<div id="likeDiv">
	
	<c:choose>

		<c:when test="${likeCheck }">
			<!--안좋아요 버튼 -->
			<div class="text-center">
			<span id="unlikeSpan" class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
			<input type="hidden" name="uNo" id="unlikeuNo" value="${sessionScope.user.uNo }">
			<input type="hidden" name="bNo" id="unlikebNo" value="${boardInfo.B_NO }">
			</div>
			
			<!-- 안좋아요 숫자  -->
			<div class="text-center">
			<p>좋아요 ${boardInfo.B_LIKES } 개</p>
			</div>	
		</c:when>
	
		<c:otherwise>
			<!--좋아요 버튼 -->
			<div class="text-center">
			<span id="likeSpan" class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
			<input type="hidden" name="uNo" id="likeuNo" value="${sessionScope.user.uNo }">
			<input type="hidden" name="bNo" id="likebNo" value="${boardInfo.B_NO }">
			</div>
			
			<!-- 좋아요 숫자  -->
			<div class="text-center">
			<p>좋아요 ${boardInfo.B_LIKES } 개</p>
			</div>
		</c:otherwise>
		
	</c:choose>

	</div>
<!------------------------ -->
	
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
		  <div class="form-group">
		    <label for="comment"></label>
		    <input type="text" class="form-control" id="comment" name="comment" placeholder="댓글을 달아보세요." style="width: 90%; display: inline-block;">
		  	<button id="commentBtn" class="btn btn-default btn-sm">등록</button>
		  </div>
	</div>
	
<!--댓글 리스트 영역  -->
	<div id="commentDiv" style="padding: 15px;">
	
	<c:choose>
		
		<c:when test="${not empty commentListSize }">
			<!-- 댓글 리스트 만큼 반복된다.  -->
			<c:forEach begin="0" end="${commentListSize-1 }" items="${commentList }" var ="commentInfo">
			<div style="border-bottom: 1px solid #ccc; margin-bottom: 5px;">
				<div><strong style="font-size: 16px;">
				${commentInfo.U_NICK }</strong></div>			
				<div>${commentInfo.C_CONTENT }</div>
				<div style="font-size: 10px; color: #ccc;">
				<fmt:formatDate value="${commentInfo.C_WRITE_DATE }" pattern="yyyy/MM/dd - hh:mm"/>
				<span id ="nestBtn" style="font-size: 12px; color: #3d5a80; cursor: pointer;">답글</span>
				<!-- 댓글 작성자일 경우 활성화된다.  -->
				<c:if test="${sessionScope.user.uNo eq commentInfo.U_NO}">
				<span id ="commentDeleteBtn" style="font-size: 12px; color: red; cursor: pointer;">삭제</span>
				</c:if>
				
				</div>			
			</div>
			</c:forEach>
			
		</c:when>
	
	
		<c:otherwise>
			<div>등록된 댓글이 없습니다.</div>
		</c:otherwise>
	
	</c:choose>
	
	</div>
<!--------------------------------  -->

</div>

<%@include file="/WEB-INF/views/layout/userFooter.jsp" %>