<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--댓글 리스트 영역  -->
	<c:choose>
		<c:when test="${not empty commentListSize }">
		<input type="hidden" name="commentListSize" id= "commentListSize" value="${commentListSize }">	
			
			<c:set value="0" var="num"></c:set><!--아이디에추가되는값  -->
			<!-- 댓글 리스트 만큼 반복된다.  -->
			<c:forEach begin="0" end="${commentListSize-1 }" items="${commentList }" var ="commentInfo">
			<input type="hidden" value="${num=num+1 }"><!-- loop마다 하나씩 더해준다.  -->
			<div style="border-bottom: 1px solid #ccc; margin-bottom: 5px;">
				<div><strong style="font-size: 16px;">
				${commentInfo.U_NICK }</strong></div>			
				<div>${commentInfo.C_CONTENT }</div>
				<div style="font-size: 10px; color: #ccc;">
				<fmt:formatDate value="${commentInfo.C_WRITE_DATE }" pattern="yyyy/MM/dd - hh:mm"/>
				
				<span id ="nestBtn${num }" style="font-size: 12px; color: #3d5a80; cursor: pointer;">답글</span>
				
				<!-- 댓글 작성자일 경우 활성화된다.  -->
				<c:if test="${sessionScope.user.uNo eq commentInfo.U_NO}">
				<span id ="commentDeleteBtn${num }" style="font-size: 12px; color: red; cursor: pointer;" onclick="deleteCno(this)">삭제
				<input type="hidden" value="${commentInfo.C_NO }">
				</span>				
				</c:if>
				
				</div>			
			</div>
			</c:forEach>
			
		</c:when>
	
	
		<c:otherwise>
			<div class="text-center">등록된 댓글이 없습니다.</div>
		</c:otherwise>	
	</c:choose>
	
	<!-- 댓글 더보기 접기 체크 구문 -->
	<c:choose>
		
		<c:when test="${commentListSize < totalCommentCount}">
		<div class="text-center" style="margin-top: 15px">
			<button class="btn btn-sm btn-default" id="moreComment" name="moreComment" type="button">더보기</button>
		</div>
		</c:when>
		
		<c:when test="${commentListSize >= totalCommentCount and commentListSize > 10}">
		<div class="text-center" style="margin-top: 15px">
			<button class="btn btn-sm btn-default" id="foldComment" name="foldComment" type="button">접기</button>
		</div>
		</c:when>
	
		<c:otherwise/>
	</c:choose>
<!--------------------------------  -->