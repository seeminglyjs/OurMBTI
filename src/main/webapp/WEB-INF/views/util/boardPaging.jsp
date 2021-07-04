<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- JSTL 태그 라이브러리  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty param.category }">
	<c:set value="&category=${param.category }" var="category"></c:set>
</c:if>

<c:if test="${not empty param.searchText }">
	<c:set value="&searchText=${param.searchText }" var="searchText"></c:set>
</c:if>


<c:if test="${not empty param.searchCategory }">
	<c:set value="&searchCategory=${param.searchCategory }" var="searchCategory"></c:set>
</c:if>


<div id="pagingDiv" class="text-center">
  <ul class="pagination">
  
  <!-- 첫페이지로 이동하기 -->
  <c:choose>
  	<c:when test="${paging.curPage ne 1 }">
  	<li>
  		<a href="/board/list?curPage=1${category}${searchCategory}${searchText}">
  			<span>1 . .</span>
  		</a>
  	</li>
  	</c:when>
  </c:choose>
  
  
  <!-- 페이지 하나 뺴기 -->
  <c:choose>
  	<c:when test="${paging.curPage -1 eq 0 }">
    <li class="disabled">
        <span>&laquo;</span>
    </li>
  	</c:when>
  	
  	<c:otherwise>
  	<li>
      <a href="/board/list?curPage=${paging.curPage-1}${category}${searchCategory}${searchText}">
        <span>&laquo;</span>
      </a>
    </li>
  	</c:otherwise>
  </c:choose>
  
  
  
  <!--페이지 리스트   -->
  <c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="page">
  	
  	<c:if test="${page eq paging.curPage }">
  	<li class="active"><a href="/board/list?curPage=${page }${category}${searchCategory}${searchText}">${page }</a></li>
  	</c:if>
  	
  	<c:if test="${page ne paging.curPage }">
  	<li><a href="/board/list?curPage=${page }${category}${searchCategory}${searchText}">${page }</a></li>
  	</c:if>
  	
  </c:forEach>  
  
  
    
  <!-- 페이지 하나 더하기 -->
  <c:choose>
  	<c:when test="${paging.curPage eq paging.totalPage }">
    <li class="disabled">
        <span aria-hidden="true">&raquo;</span>
    </li>
  	</c:when>
  	
  	<c:when test="${paging.curPage ne paging.totalPage }">
  	<li>
      <a href="/board/list?curPage=${paging.curPage+1}${category}${searchCategory}${searchText}" aria-label="Previous">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:when>  	
  </c:choose>
  
  
  <!-- 마지막 페이지로  -->
   <c:choose>
  	<c:when test="${paging.curPage ne paging.totalPage }">
  	<li>
  		<a href="/board/list?curPage=${paging.totalPage}${category}${searchCategory}${searchText}">
  			<span>. . ${paging.totalPage}</span>
  		</a>
  	</li>
  	</c:when>
  </c:choose>
  
  </ul>
</div>