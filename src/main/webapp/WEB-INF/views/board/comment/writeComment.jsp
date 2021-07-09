<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--댓글 리스트 영역  -->
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
			<div class="text-center">등록된 댓글이 없습니다.</div>
		</c:otherwise>
	
	</c:choose>
<!--------------------------------  -->