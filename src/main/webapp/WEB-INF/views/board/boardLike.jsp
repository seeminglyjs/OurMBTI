<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--1  -->
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